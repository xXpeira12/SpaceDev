package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
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

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Space Menu Game");

        createMainMenuScene();
        createGameplayScene();

        gameState = GameState.MAIN_MENU;
        primaryStage.setScene(mainMenuScene);
        primaryStage.show();
    }

    public void setGameState(GameState state) {
//        System.out.println("Current game state: " + gameState + "..." + "Setting game state to: " + state);
        gameState = state;
        switch (gameState) {
            case MAIN_MENU:
                primaryStage.setScene(mainMenuScene);
                break;
            case PLAYING:
                primaryStage.setScene(gameplayScene);
                startGame();
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

        Slider volumeSlider = new Slider(0, 1, 0.5);
        volumeSlider.setMajorTickUnit(0.1);
        volumeSlider.setMinorTickCount(1);
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setShowTickMarks(true);
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            mediaPlayer.setVolume(newValue.doubleValue());
        });

        // Create volume label
        Label volumeLabel = new Label("Music Volume");
        volumeLabel.setTextFill(Color.WHITE);

        // Create volume container
        HBox volumeContainer = new HBox(10);
        volumeContainer.setAlignment(Pos.CENTER);
        volumeContainer.getChildren().addAll(volumeLabel, volumeSlider);

        // Add buttons to menu layout
        menuLayout.getChildren().addAll(
                startButton, howToPlayButton, enemiesInfoButton,
                itemsInfoButton, highScoresButton, exitButton,
                volumeContainer
        );
        // Add menu layout to root
        root.setCenter(menuLayout);

        // Create scene
        mainMenuScene = new Scene(root, 800, 600);
    }

    private void createGameplayScene() {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        StackPane gameplayLayout = new StackPane(canvas);
        gameplayScene = new Scene(gameplayLayout, WIDTH, HEIGHT);
    }

    public void showMainMenu() {
        primaryStage.setScene(mainMenuScene);
    }

}
