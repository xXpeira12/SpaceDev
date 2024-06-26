package application;

import entity.Item.BoostShotItem;
import entity.Item.Item;
import entity.bomb.*;
import entity.shot.BaseShot;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import entity.rocket.Rocket;
import entity.shot.Shot;
import entity.shot.SpreadShot;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static config.Config.*;

public class GamePlay extends Application {
    public static final Random RAND = new Random();
    boolean gameOver = false;
    public static GraphicsContext gc;
    public static Rocket player;
    List<Shot> shots;
    List<Universe> univ;
    List<Bomb> bombs;
    List<Item> items;
    public static int score;
    private boolean left, right, shoot, restart;
    private boolean shotFired;
    private int counter = 0;
    private boolean isPaused = false;
    private MediaPlayer shootingMediaPlayer;
    private MediaPlayer explosionMediaPlayer;
    private MediaPlayer dropItemMediaPlayer;

    @Override
    public void start(Stage stage) throws Exception {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000.0 / 60), e -> run(gc)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        canvas.setCursor(Cursor.MOVE);
        canvas.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case LEFT:
                    left = true;
                    break;
                case RIGHT:
                    right = true;
                    break;
                case SPACE:
                    shoot = true;
                    break;
                case ENTER:
                    restart = true;
                    break;
                case ESCAPE:
                    isPaused = !isPaused;
                    break;
            }
        });

        canvas.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case LEFT:
                    left = false;
                    break;
                case RIGHT:
                    right = false;
                    break;
                case SPACE:
                    shoot = false;
                    shotFired = false;
                    break;
                case ENTER:
                    restart = false;
                    break;
            }
        });

        canvas.setFocusTraversable(true);
        canvas.requestFocus();
        setUp();
        stage.setScene(new Scene(new StackPane(canvas)));
        stage.setTitle("Space");
        stage.show();
    }

    private void setUp() {
        score = INITIAL_SCORE;
        univ = new ArrayList<>();
        shots = new ArrayList<>();
        bombs = new ArrayList<>();
        items = new ArrayList<>();
        player = new Rocket(WIDTH / 2, HEIGHT - PLAYER_SIZE, PLAYER_SIZE, PLAYER_IMG);
        IntStream.range(0, MAX_BOMBS).forEach(i -> {
            int bombType = RAND.nextInt(3);
            Bomb bomb;
            switch (bombType) {
                case 0:
                    bomb = new BaseBomb(50 + RAND.nextInt(WIDTH - 100), 0, PLAYER_SIZE, BOMBS_IMG[0], 5);
                    break;
                case 1:
                    bomb = new FastBomb(50 + RAND.nextInt(WIDTH - 100), 0, PLAYER_SIZE, BOMBS_IMG[1], 2);
                    break;
                case 2:
                    bomb = new BigBomb(50 + RAND.nextInt(WIDTH - 100), 0, PLAYER_SIZE, BOMBS_IMG[2], 10);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + bombType);
            }
            bombs.add(bomb);
        });

        Media shootingSound = new Media(new File("assets/shoot_sound.mp3").toURI().toString());
        shootingMediaPlayer = new MediaPlayer(shootingSound);
        shootingMediaPlayer.setVolume(Math.min(DEFAULT_SFX_VOLUME, 0.5));

        Media explosionSound = new Media(new File("assets/explosion_sound.mp3").toURI().toString());
        explosionMediaPlayer = new MediaPlayer(explosionSound);
        explosionMediaPlayer.setVolume(DEFAULT_SFX_VOLUME);

        Media dropItemSound = new Media(new File("assets/dropItem_sound.mp3").toURI().toString());
        dropItemMediaPlayer = new MediaPlayer(dropItemSound);
        dropItemMediaPlayer.setVolume(DEFAULT_SFX_VOLUME);
    }

    private void run(GraphicsContext gc) {
        clearScreen(gc);
        displayScore(gc);
        displayCurrentGun(gc);

        if (gameOver) {
            displayGameOver(gc);
            return;
        }
        if (isPaused) {
            displayPauseMenu(gc);
            return;
        }

        drawUniverse();
        updatePlayer();
        handlePlayerShooting();
        checkCollisions();
        updateBombs();
        updateItems();
        spawnBossBomb();
        updateBossBombShots();

        gameOver = player.isDestroyed();
        spawnUniverse();
        removeOffscreenUniverse();
    }

    private void clearScreen(GraphicsContext gc) {
        gc.setFill(Color.grayRgb(20));
        gc.fillRect(0, 0, WIDTH, HEIGHT);
    }

    private void displayScore(GraphicsContext gc) {
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font(20));
        gc.setFill(Color.WHITE);
        gc.fillText("Score: " + score, 60, 20);
    }

    private void displayCurrentGun(GraphicsContext gc) {
        gc.fillText("Current Gun: " + player.getStatus(), WIDTH / 2.0, 20);
    }

    private void drawUniverse() {
        univ.forEach(Universe::draw);
    }

    private void updatePlayer() {
        player.update();
        player.draw();
        if (left) {
            if (player.getPosX() > PADDING_ROCKET) {
                player.setPosX(player.getPosX() - 5);
            }
        }
        if (right) {
            if (player.getPosX() < (WIDTH - PLAYER_SIZE)) {
                player.setPosX(player.getPosX() + 5);
            }
        }
    }

    private void handlePlayerShooting() {
        if (shoot && !shotFired && shots.size() < MAX_SHOTS) {
            if (player.shoot() instanceof SpreadShot) {
                Collections.addAll(shots, new SpreadShot(player.shoot().getPosX(), player.shoot().getPosY(), ((SpreadShot) player.shoot()).getNumShots(), ((SpreadShot) player.shoot()).getSpaceBetweenShot()).getShots());
            } else {
                shots.add(player.shoot());
            }
            shotFired = true;

            shootingMediaPlayer.stop();
            shootingMediaPlayer.play();
        }
    }

    private void checkCollisions() {
        bombs.stream().peek(Bomb::update).peek(Bomb::draw).forEach(e -> {
            if (player.collide(e) && !player.isExploding()) {
                player.explode();
                explosionMediaPlayer.stop();
                explosionMediaPlayer.play();
            }
        });

        for (int i = shots.size() - 1; i >= 0; i--) {
            Shot shot = shots.get(i);
            if (shot.getPosY() < 0 || shot.isRemove()) {
                shots.remove(i);
                continue;
            }
            shot.update();
            shot.draw();
            for (Bomb bomb : bombs) {
                if (shot.collide(bomb) && !bomb.isExploding()) {
                    shot.dealDamage(bomb);
                    if (bomb.getHealth() <= 0) {
                        bomb.setDestroyed(true);
                        score++;
                        bomb.explode();
                        bomb.update();
                        bomb.draw();
                        int randomDropItem = RAND.nextInt(10);
                        if (randomDropItem < 4) {
                            items.add(new BoostShotItem(bomb.getPosX(), bomb.getPosY(), PLAYER_SIZE / 2, randomDropItem));
                        }
                        explosionMediaPlayer.stop();
                        explosionMediaPlayer.play();
                    }
                    shot.setIsRemove(true);
                }
            }
        }
    }

    private void updateBombs() {
        Class[] bombTypes = new Class[]{Bomb.class, FastBomb.class, BigBomb.class};
        for (int i = bombs.size() - 1; i >= 0; i--) {
            if (bombs.get(i).isDestroyed()) {
                Class bombType = bombTypes[RAND.nextInt(bombTypes.length)];
                Bomb newBomb;
                if (bombType == Bomb.class) {
                    newBomb = new BaseBomb(50 + RAND.nextInt(WIDTH - 100), 0, PLAYER_SIZE, BOMBS_IMG[0], 5);
                } else if (bombType == FastBomb.class) {
                    newBomb = new FastBomb(50 + RAND.nextInt(WIDTH - 100), 0, PLAYER_SIZE, BOMBS_IMG[1], 2);
                } else {
                    newBomb = new BigBomb(50 + RAND.nextInt(WIDTH - 100), 0, PLAYER_SIZE, BOMBS_IMG[2], 10);
                }
                bombs.set(i, newBomb);
            }
        }
    }

    private void updateItems() {
        items.forEach(item -> {
            item.update();
            item.draw();
        });
        for (int i = items.size() - 1; i >= 0; i--) {
            Item item = items.get(i);
            if (item.collide(player)) {
                items.remove(i);
                gc.setFill(Color.WHITE);
                gc.fillText("Item Collected", item.getPosX(), item.getPosY());
                dropItemMediaPlayer.stop();
                dropItemMediaPlayer.play();
            }
        }
    }

    private void spawnBossBomb() {
        if (score >= 50 && score % 10 == 0 && bombs.stream().noneMatch((b -> b instanceof BossBomb))) {
            bombs.add(new BossBomb(50 + RAND.nextInt(WIDTH - 100), 0, PLAYER_SIZE, BOMBS_IMG[3], 20));
        }
    }

    private void updateBossBombShots() {
        counter++;
        if (counter >= 60) {
            for (Bomb bomb : bombs) {
                if (bomb instanceof BossBomb bossBomb) {
                    bossBomb.shot();
                    counter = 0;
                }
            }
        }

        for (Bomb bomb : bombs) {
            if (bomb instanceof BossBomb bossBomb) {
                for (BaseShot shot : bossBomb.getShots()) {
                    shot.updateBombShot();
                    shot.drawBombShot();

                    if (shot.collide(player)) {
                        player.explode();
                    }
                    gameOver = player.isDestroyed();
                    if (gameOver) {
                        displayGameOver(gc);
                        return;
                    }
                }
            }
        }
    }

    private void spawnUniverse() {
        if (RAND.nextInt(10) > 2) {
            univ.add(new Universe());
        }
    }

    private void removeOffscreenUniverse() {
        for (int i = 0; i < univ.size(); i++) {
            if (univ.get(i).getPosY() > HEIGHT) {
                univ.remove(i);
            }
        }
    }

    private void displayGameOver(GraphicsContext gc) {
        gc.setFont(Font.font(35));
        gc.setFill(Color.YELLOW);
        gc.fillText("Game Over \n Your Score is: " + score + " \n Press Enter to play again", WIDTH / 2.0, HEIGHT / 2.5);
        if (restart) {
            gameOver = false;
            setUp();
        } else {
            return;
        }
    }

    private void displayPauseMenu(GraphicsContext gc) {
        gc.setFont(Font.font(35));
        gc.setFill(Color.YELLOW);
        gc.fillText("Paused \n Press ESC to resume", WIDTH / 2.0, HEIGHT / 2.5);
    }
}
