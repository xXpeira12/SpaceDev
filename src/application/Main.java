package application;

import entity.Item.BoostShotItem;
import entity.Item.DropItem;
import entity.bomb.BigBomb;
import entity.bomb.Bomb;
import entity.bomb.BossBomb;
import entity.bomb.FastBomb;
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
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Main extends Application {
    public static final Random RAND = new Random();
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final int PLAYER_SIZE = 60;
    public static final Image PLAYER_IMG = new Image("file:assets/player.png");
    public static final Image EXPLOSION_IMG = new Image("file:assets/explosion.png");
    public static final int EXPLOSION_W = 128;
    public static final int EXPLOSION_ROWS = 3;
    public static final int EXPLOSION_COLS = 3;
    public static final int EXPLOSION_H = 128;
    public static final int EXPLOSION_STEPS = 15;
    public static final int INITIAL_SCORE = 25;
    public static final int BASE_SHOT_SIZE = 6;
    public static final int BIG_SHOT_SIZE = 9;
    public static final int SPEED_SHOT_SIZE = 4;
    public static final int POWER_UP_DURATION = 3 * 60;
    public static final Image BOMBS_IMG[] = {new Image("file:assets/1.png"), new Image("file:assets/2.png"), new Image("file:assets/3.png"), new Image("file:assets/4.png")};
    public static final Image BoostShot_IMG[] = {new Image("file:assets/drop_item.png"), new Image("file:assets/drop_item.png"), new Image("file:assets/drop_item.png")};
    final int MAX_BOMBS = 6;
    final int MAX_SHOTS = MAX_BOMBS * 2;
    boolean gameOver = false;
    public static GraphicsContext gc;
    public static Rocket player;
    List<Shot> shots;
    List<Universe> univ;
    List<Bomb> Bombs;
    List<DropItem> dropItems;
    public static int score;
    private boolean left, right, shoot, restart;
    private boolean shotFired;

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
        Bombs = new ArrayList<>();
        dropItems = new ArrayList<>();
        player = new Rocket(WIDTH / 2, HEIGHT - PLAYER_SIZE, PLAYER_SIZE, PLAYER_IMG);
        score = INITIAL_SCORE;
        IntStream.range(0, MAX_BOMBS).forEach(i -> {
            int bombType = RAND.nextInt(3); // Randomly choose between 0, 1, and 2
            Bomb bomb;
            switch (bombType) {
                case 0:
                    bomb = new Bomb(50 + RAND.nextInt(WIDTH - 100), 0, PLAYER_SIZE, BOMBS_IMG[0], 5);
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
            Bombs.add(bomb);
        });
    }

    private void run(GraphicsContext gc) {
        gc.setFill(Color.grayRgb(20));
        gc.fillRect(0, 0, WIDTH, HEIGHT);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font(20));
        gc.setFill(Color.WHITE);
        gc.fillText("Score: " + score, 60, 20);
        gc.fillText("Current Gun: " + player.getStatus(), WIDTH / 2, 20);

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
                for (Shot eachShot : new SpreadShot(player.shoot().getPosX(), player.shoot().getPosY(), ((SpreadShot) player.shoot()).getNumShots(), ((SpreadShot) player.shoot()).getSpaceBetweenShot()).getShots()) {
                    shots.add(eachShot);
                }
            } else {
                shots.add(player.shoot());
            }
            shotFired = true;
        }

        Bombs.stream().peek(Bomb::update).peek(Bomb::draw).forEach(e -> {
            if (player.colide(e) && !player.isExploding()) {
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
            for (Bomb bomb : Bombs) {
                if (shot.colide(bomb) && !bomb.isExploding()) {
                    shot.dealDamage(bomb);
                    if (bomb.getHealth() <= 0) {
                        bomb.setDestroyed(true);
                        score++;
                        bomb.explode();
                        bomb.update();
                        bomb.draw();
                        int randomDropItem = RAND.nextInt(10);
                        if (randomDropItem < 3) {
                            dropItems.add(new BoostShotItem(bomb.getPosX(), bomb.getPosY(), PLAYER_SIZE / 2, randomDropItem));
                        }
                    }
                    shot.setToRemove(true);
                }
            }
        }

        Class[] bombTypes = new Class[]{Bomb.class, FastBomb.class, BigBomb.class};
        for (int i = Bombs.size() - 1; i >= 0; i--) {
            if (Bombs.get(i).isDestroyed()) {
                Class bombType = bombTypes[RAND.nextInt(bombTypes.length)];
                Bomb newBomb;
                if (bombType == Bomb.class) {
                    newBomb = new Bomb(50 + RAND.nextInt(WIDTH - 100), 0, PLAYER_SIZE, BOMBS_IMG[0], 5);
                } else if (bombType == FastBomb.class) {
                    newBomb = new FastBomb(50 + RAND.nextInt(WIDTH - 100), 0, PLAYER_SIZE, BOMBS_IMG[1], 2);
                } else { // bombType == BigBomb.class
                    newBomb = new BigBomb(50 + RAND.nextInt(WIDTH - 100), 0, PLAYER_SIZE, BOMBS_IMG[2], 10);
                }
                Bombs.set(i, newBomb);
            }
        }
        dropItems.forEach(item -> {
            item.update();
            item.draw();
        });
        for (int i = dropItems.size() - 1; i >= 0; i--) {
            DropItem item = dropItems.get(i);
            if (item.collide(player)) {
                dropItems.remove(i);
                gc.setFill(Color.WHITE);
                gc.fillText("Item Collected", item.getPosX(), item.getPosY());
            }
        }

        if (score >= 50 && score % 10 == 0 && Bombs.stream().noneMatch((b -> b instanceof BossBomb))) {
            Bombs.add(new BossBomb(50 + RAND.nextInt(WIDTH - 100), 0, PLAYER_SIZE, BOMBS_IMG[3], 20));
        }

        gameOver = player.isDestroyed();

//        for (Bomb entity.bomb : Bombs) {
//    if (entity.bomb instanceof BossBomb) {
//        // Cast entity.bomb to BossBomb so we can call BossBomb methods
//        BossBomb bossBomb = (BossBomb) entity.bomb;
//
//        // Call the shoot method
//       bossBomb.shoot(bossBomb.getShots());
//
//        // Update and draw the shot
//        for (Shot shot : bossBomb.getShots()) {
//            shot.update();
//            shot.draw();
//
//            // Check for collision with Rocket
//            if (shot.collide(player)) {
//                gameOver = true; // Assuming you have a gameOver variable to end the game
//                return; // End the game immediately
//            }
//        }
//    }
//}

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
