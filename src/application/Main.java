package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import static config.Config.*;

import java.io.File;
import java.util.Objects;

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
//                create new gameplayScene and remove the old one
                createGameplayScene();
                break;
            case PLAYING:
                primaryStage.setScene(gameplayScene);
                startGame();
                break;
            default:
                showInfoScene(gameState);
                break;
        }
    }

    private void showInfoScene(GameState state) {
        String imageFile = getImageFile(state);
        Scene infoScene = createInfoScene(imageFile);
        addSceneListeners(infoScene);
        primaryStage.setScene(infoScene);
    }

    private String getImageFile(GameState state) {
        switch (state) {
            case HOW_TO_PLAY:
                return "file:assets/how_to_play.png";
            case ENEMIES_INFO:
                return "file:assets/enemies_info.png";
            case ITEMS_INFO:
                return "file:assets/items_info.png";
            default:
                return "";
        }
    }

    private Scene createInfoScene(String imageFile) {
        StackPane layout = new StackPane();
        Scene infoScene = new Scene(layout, 800, 600);

        Image image = new Image(imageFile);
        ImageView imageView = new ImageView(image);

        VBox contentLayout = new VBox(20);
        contentLayout.setAlignment(Pos.CENTER);
        contentLayout.getChildren().add(imageView);

        layout.getChildren().add(contentLayout);
        return infoScene;
    }

    private Background createSpaceBackground() {
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

        String defaultStyle = "-fx-background-color: linear-gradient(to bottom, #000099, #8a2be2);" +
                "-fx-background-radius: 0;" +
                "-fx-border-color: #000000;" +
                "-fx-border-width: 2px;" +
                "-fx-border-radius: 5px;";
        String hoverStyle = "-fx-background-color: linear-gradient(to bottom, #000099, #9b30ff);" +
                "-fx-background-radius: 0;" +
                "-fx-border-color: #000000;" +
                "-fx-border-width: 2px;" +
                "-fx-border-radius: 5px;";

        button.setStyle(defaultStyle);
        button.setOnMouseEntered(e -> button.setStyle(hoverStyle));
        button.setOnMouseExited(e -> button.setStyle(defaultStyle));

        return button;
    }


    private void startGame() {
//        System.out.println("Starting the game!");
        GamePlay gamePlay = new GamePlay();
        try {
            gamePlay.start(primaryStage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
        howToPlayButton.setOnAction(e -> setGameState(GameState.HOW_TO_PLAY));

        Button enemiesInfoButton = createMenuButton("Enemies Info");
        enemiesInfoButton.setOnAction(e -> setGameState(GameState.ENEMIES_INFO));

        Button itemsInfoButton = createMenuButton("Item & Enhancement Info");
        itemsInfoButton.setOnAction(e -> setGameState(GameState.ITEMS_INFO));

        Button exitButton = createMenuButton("Exit");
        exitButton.setOnAction(e -> primaryStage.close());

        Slider volumeSlider = createVolumeSlider();
        Label volumeLabel = createVolumeLabel();
        HBox volumeContainer = createVolumeContainer(volumeLabel, volumeSlider);

        // Add buttons to menu layout
        menuLayout.getChildren().addAll(
                startButton, howToPlayButton, enemiesInfoButton,
                itemsInfoButton, exitButton,
                volumeContainer
        );
        root.setCenter(menuLayout);

        mainMenuScene = new Scene(root, 800, 600);
    }

    private Slider createVolumeSlider() {
        Slider volumeSlider = new Slider(0, 1, 0.5);
        volumeSlider.setMajorTickUnit(0.1);
        volumeSlider.setMinorTickCount(1);
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setShowTickMarks(true);
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            mediaPlayer.setVolume(newValue.doubleValue());
        });
        return volumeSlider;
    }

    private Label createVolumeLabel() {
        Label volumeLabel = new Label("Music Volume");
        volumeLabel.setTextFill(Color.WHITE);
        return volumeLabel;
    }

    private HBox createVolumeContainer(Label volumeLabel, Slider volumeSlider) {
        HBox volumeContainer = new HBox(10);
        volumeContainer.setAlignment(Pos.CENTER);
        volumeContainer.getChildren().addAll(volumeLabel, volumeSlider);
        return volumeContainer;
    }

    private void createGameplayScene() {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        StackPane gameplayLayout = new StackPane(canvas);
        gameplayScene = new Scene(gameplayLayout, WIDTH, HEIGHT);
    }

    private void addSceneListeners(Scene scene) {
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                setGameState(GameState.MAIN_MENU);
            }
        });
    }
}
