package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import static config.Config.*;

import java.io.File;

public class Main extends Application {

    private Stage primaryStage;
    private MediaPlayer mediaPlayer;
    private GameState gameState;
    private Scene mainMenuScene;
    private Scene gameplayScene;
    private Scene pauseMenuScene;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Space Menu Game");

        createMainMenuScene();
        createGameplayScene();
        createPauseMenuScene();

        gameState = GameState.MAIN_MENU;
        primaryStage.setScene(mainMenuScene);
        primaryStage.show();
    }

    private void setGameState(GameState state) {
        gameState = state;
        switch (gameState) {
            case MAIN_MENU:
                primaryStage.setScene(mainMenuScene);
                break;
            case PLAYING:
                primaryStage.setScene(gameplayScene);
                startGame();
                break;
            case PAUSED:
                primaryStage.setScene(pauseMenuScene);
                break;
        }
    }

    private Background createSpaceBackground() {
        // Load background image using ClassLoader
        String imageUrl = getClass().getResource("/bgmenu.png").toExternalForm();
        BackgroundImage backgroundImage = new BackgroundImage(
                new javafx.scene.image.Image(imageUrl, 800, 600, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                javafx.scene.layout.BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        );
        return new Background(backgroundImage);
    }

    private Button createMenuButton(String text) {
        Button button = new Button(text);
        button.setMinWidth(200);
        button.setMinHeight(40);
        button.setFont(Font.font("Pixel Font", 16));
        button.setTextFill(Color.WHITE);

        // Default button style (blue and purple gradient)
        button.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #000099, #8a2be2);" +
                        "-fx-background-radius: 0;" +
                        "-fx-border-color: #000000;" +
                        "-fx-border-width: 2px;" +
                        "-fx-border-radius: 5px;"
        );

        // Set button hover style
        button.setOnMouseEntered(e -> {
            button.setStyle(
                    "-fx-background-color: linear-gradient(to bottom, #000099, #9b30ff);" +
                            "-fx-background-radius: 0;" +
                            "-fx-border-color: #000000;" +
                            "-fx-border-width: 2px;" +
                            "-fx-border-radius: 5px;"
            );
        });

        // Set button exit (hover off) style
        button.setOnMouseExited(e -> {
            button.setStyle(
                    "-fx-background-color: linear-gradient(to bottom, #000099, #8a2be2);" +
                            "-fx-background-radius: 0;" +
                            "-fx-border-color: #000000;" +
                            "-fx-border-width: 2px;" +
                            "-fx-border-radius: 5px;"
            );
        });

        return button;
    }


    private void startGame() {
        System.out.println("Starting the game!");
        GamePlay gamePlay = new GamePlay();
        try {
            gamePlay.setMainApp(this);
            gamePlay.start(primaryStage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void showHowToPlay() {
        System.out.println("How to Play");
        // Implement how to play information
    }

    private void showEnemiesInfo() {
        System.out.println("Enemies Info");
        // Implement enemies information
    }

    private void showItemsInfo() {
        System.out.println("Item & Enhancement Info");
        // Implement item & enhancement information
    }

    private void showHighScores() {
        System.out.println("High Scores");
        // Implement high scores display
    }

    @Override
    public void stop() {
        mediaPlayer.stop();
    }

    public void reduceVolume() {
        mediaPlayer.setVolume(0.5);
    }

    public void createMainMenuScene() {
        // Create root layout
        BorderPane root = new BorderPane();
        root.setBackground(createSpaceBackground());

        // Load background music
        String backgroundMusicFile = "assets/space_song.mp3";
        Media backgroundMusicMedia = new Media(new File(backgroundMusicFile).toURI().toString());
        mediaPlayer = new MediaPlayer(backgroundMusicMedia);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(0.5);
        mediaPlayer.play();

        // Create main menu layout
        VBox menuLayout = new VBox(20);
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.setMinWidth(400);

        // Create buttons
        Button startButton = createMenuButton("Start Game");
        startButton.setOnAction(e -> {
            try {
                setGameState(GameState.PLAYING);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        Button howToPlayButton = createMenuButton("How to Play");
        howToPlayButton.setOnAction(e -> showHowToPlay());

        Button enemiesInfoButton = createMenuButton("Enemies Info");
        enemiesInfoButton.setOnAction(e -> showEnemiesInfo());

        Button itemsInfoButton = createMenuButton("Item & Enhancement Info");
        itemsInfoButton.setOnAction(e -> showItemsInfo());

        Button highScoresButton = createMenuButton("High Scores");
        highScoresButton.setOnAction(e -> showHighScores());

        Button exitButton = createMenuButton("Exit");
        exitButton.setOnAction(e -> primaryStage.close());

        // Add buttons to menu layout
        menuLayout.getChildren().addAll(
                startButton, howToPlayButton, enemiesInfoButton,
                itemsInfoButton, highScoresButton, exitButton
        );

        // Add menu layout to root
        root.setCenter(menuLayout);

        // Create scene
        mainMenuScene = new Scene(root, 800, 600);
        setUpKeyEventHandler(mainMenuScene);
    }

    private void createGameplayScene() {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        StackPane gameplayLayout = new StackPane(canvas);
        gameplayScene = new Scene(gameplayLayout, WIDTH, HEIGHT);
        setUpKeyEventHandler(gameplayScene);
    }

    private void createPauseMenuScene() {
        VBox pauseLayout = new VBox(20);
        pauseLayout.setAlignment(Pos.CENTER);

        Button resumeButton = createMenuButton("Resume");
        resumeButton.setOnAction(e -> setGameState(GameState.PLAYING));

        Button backButton = createMenuButton("Back to Main Menu");
        backButton.setOnAction(e -> setGameState(GameState.MAIN_MENU));

        pauseLayout.getChildren().addAll(resumeButton, backButton);

        pauseMenuScene = new Scene(pauseLayout, 400, 300);
        setUpKeyEventHandler(pauseMenuScene);
    }

    private void setUpKeyEventHandler(Scene scene) {
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                if (gameState == GameState.PLAYING) {
                    setGameState(GameState.PAUSED);
                } else if (gameState == GameState.PAUSED) {
                    setGameState(GameState.MAIN_MENU);
                }
            }
        });
    }

    public void showMainMenu() {
        primaryStage.setScene(mainMenuScene);
    }
}
