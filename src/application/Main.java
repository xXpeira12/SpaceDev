package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
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

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Space Menu Game");

        // Create root layout
        BorderPane root = new BorderPane();
        root.setBackground(createSpaceBackground());

        // Load background music
        String backgroundMusicFile = "/ProgMethAllLecture/SpaceDev/assets/space_song.mp3"; // Adjust the path to your background music file
        Media backgroundMusicMedia = new Media(new File(backgroundMusicFile).toURI().toString());
        mediaPlayer = new MediaPlayer(backgroundMusicMedia);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop the background music
        mediaPlayer.play(); // Start playing the background music

        // Create main menu layout
        VBox menuLayout = new VBox(20);
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.setMinWidth(400);

        // Create buttons
        Button startButton = createMenuButton("Start Game");
        startButton.setOnAction(e -> {
            try {
                playButtonClickSound(); // Play button click sound
                startGame();
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
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);

        primaryStage.show();
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



    private void startGame() throws Exception {
        System.out.println("Starting the game!");
        GamePlay gamePlay = new GamePlay();

        // Create a new scene for the game play
        Scene gameScene = new Scene(new StackPane(), WIDTH, HEIGHT);

        // Set up the game canvas
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        StackPane gamePane = new StackPane(canvas);
        gameScene.setRoot(gamePane);

        // Switch to the game scene
        primaryStage.setScene(gameScene);

        // Start the game
        gamePlay.start(primaryStage);
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

    private void playButtonClickSound() {
        String buttonClickSoundFile = "space_song.mp3"; // Adjust the path to your button click sound file
        Media buttonClickMedia = new Media(new File(buttonClickSoundFile).toURI().toString());
        MediaPlayer buttonClickPlayer = new MediaPlayer(buttonClickMedia);
        buttonClickPlayer.play(); // Play the button click sound
    }

    @Override
    public void stop() {
        mediaPlayer.stop(); // Stop the background music when the application exits
    }

}
