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
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import entity.rocket.Rocket;
import entity.shot.Shot;
import entity.shot.SpreadShot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static config.Config.*;

public class Main extends Application {
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
    int counter = 0;

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
        score = INITIAL_SCORE;
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
    }

    private void run(GraphicsContext gc) {
        gc.setFill(Color.grayRgb(20));
        gc.fillRect(0, 0, WIDTH, HEIGHT);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font(20));
        gc.setFill(Color.WHITE);
        gc.fillText("Score: " + score, 60, 20);
        gc.fillText("Current Gun: " + player.getStatus(), WIDTH / 2.0, 20);

        if (gameOver) {
            gc.setFont(Font.font(35));
            gc.setFill(Color.YELLOW);
            gc.fillText("Game Over \n Your Score is: " + score + " \n Press Enter to play again", WIDTH / 2.0, HEIGHT / 2.5);
            if (restart) {
                gameOver = false;
                setUp();
            }
            return;
        }

        univ.forEach(Universe::draw);

        player.update();
        player.draw();
        if (left) {
            player.setPosX(player.getPosX() - 5);
        }
        if (right) {
            player.setPosX(player.getPosX() + 5);
        }
        if (shoot && !shotFired && shots.size() < MAX_SHOTS) {
            if (player.shoot() instanceof SpreadShot) {
                Collections.addAll(shots, new SpreadShot(player.shoot().getPosX(), player.shoot().getPosY(), ((SpreadShot) player.shoot()).getNumShots(), ((SpreadShot) player.shoot()).getSpaceBetweenShot()).getShots());
            } else {
                shots.add(player.shoot());
            }
            shotFired = true;
        }

        bombs.stream().peek(Bomb::update).peek(Bomb::draw).forEach(e -> {
            if (player.collide(e) && !player.isExploding()) {
                player.explode();
            }
        });

        for (int i = shots.size() - 1; i >= 0; i--) {
            Shot shot = shots.get(i);
            if (shot.getPosY() < 0 || shot.isToRemove()) {
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
                    }
                    shot.setToRemove(true);
                }
            }
        }

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
            }

            if (score >= 50 && score % 10 == 0 && bombs.stream().noneMatch((b -> b instanceof BossBomb))) {
                bombs.add(new BossBomb(50 + RAND.nextInt(WIDTH - 100), 0, PLAYER_SIZE, BOMBS_IMG[3], 20));
            }

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
                    // Update and draw the shot
                    for (BaseShot shot : bossBomb.getShots()) {
                        shot.updateBombShot();
                        shot.drawBombShot();

                        // Check for collision with Player
                        if (shot.collide(player)) {
                            player.explode();
                        }
                        gameOver = player.isDestroyed();
                        if (gameOver) {
                            gc.setFont(Font.font(35));
                            gc.setFill(Color.YELLOW);
                            gc.fillText("Game Over \n Your Score is: " + score + " \n Press Enter to play again", WIDTH / 2, HEIGHT / 2.5);
                            if (restart) {
                                gameOver = false;
                                setUp();
                            }
                            return;
                        }
                    }
                }
            }
        }

        gameOver = player.isDestroyed();

        if (RAND.nextInt(10) > 2) {
            univ.add(new Universe());
        }
        for (int i = 0; i < univ.size(); i++) {
            if (univ.get(i).getPosY() > HEIGHT) {
                univ.remove(i);
            }
        }
    }
}
