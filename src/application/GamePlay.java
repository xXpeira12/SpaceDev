package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import rocket.Bomb;
import rocket.Rocket;
import shot.Shot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class GamePlay extends Application {
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
    public static final Image BOMBS_IMG[] = {
      new Image("file:assets/1.png"),
      new Image("file:assets/2.png")
    };
    final int MAX_BOMBS = 6;
    final int MAX_SHOTS = MAX_BOMBS * 2;
    boolean gameOver = false;
    public static GraphicsContext gc;
    Rocket player;
    List<Shot> shots;
    List<Universe> univ;
    List<Bomb> Bombs;
    private double mouseX;
    public static int score;

    private Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000.0 / 60), e -> run(gc)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        canvas.setCursor(Cursor.MOVE);
        canvas.setOnMouseMoved(e -> mouseX = e.getX());
        canvas.setOnMouseClicked(e -> {
            if(shots.size() < MAX_SHOTS) shots.add(player.shoot());
            if(gameOver) {
                gameOver = false;
                setUp();
            }
        });
        canvas.setOnKeyPressed(e -> handleKeyPress(e.getCode()));
        setUp();
        stage.setScene(new Scene(new StackPane(canvas)));
        stage.setTitle("Space");
        stage.show();
    }

    private void setUp() {
        univ = new ArrayList<>();
        shots = new ArrayList<>();
        Bombs = new ArrayList<>();
        player = new Rocket(WIDTH / 2, HEIGHT - PLAYER_SIZE, PLAYER_SIZE, PLAYER_IMG);
        score = 0;
        IntStream.range(0, MAX_BOMBS).mapToObj(i -> new Bomb(50 + RAND.nextInt(WIDTH - 100), 0, PLAYER_SIZE, BOMBS_IMG[RAND.nextInt(BOMBS_IMG.length)])).forEach(Bombs::add);
    }
    private void run(GraphicsContext gc) {
        gc.setFill(Color.grayRgb(20));
        gc.fillRect(0, 0, WIDTH, HEIGHT);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font(20));
        gc.setFill(Color.WHITE);
        gc.fillText("Score: " + score, 60, 20);

        if (gameOver) {
            gc.setFont(Font.font(35));
            gc.setFill(Color.YELLOW);
            gc.fillText("Game Over \n Your Score is: " + score + " \n Click to play again", WIDTH / 2, HEIGHT / 2.5);
            return;
        }


        univ.forEach(Universe::draw);

        player.update();
        player.draw();
        player.setPosX((int) mouseX);

        Bombs.stream().peek(Rocket::update).peek(Rocket::draw).forEach(e -> {
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
                    score++;
                    bomb.explode();
                    shot.setToRemove(true);
                }
            }
        }

        for (int i = Bombs.size() - 1; i >= 0; i--) {
            if (Bombs.get(i).isDestroyed()) {
                Bombs.set(i, new Bomb(50 + RAND.nextInt(WIDTH - 100), 0, PLAYER_SIZE, BOMBS_IMG[RAND.nextInt(BOMBS_IMG.length)]));
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
    private void handleKeyPress(KeyCode code) {
        if (gameOver) {
            // Return to menu
            Main main = new Main();
            try {
                main.start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
