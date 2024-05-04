package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage primaryStage;

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

        // Create main menu layout
        VBox menuLayout = new VBox(20);
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.setMinWidth(400);

        // Create buttons
        Button startButton = createMenuButton("Start Game");
        startButton.setOnAction(e -> startGame());

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
                "-fx-background-color: linear-gradient(to bottom, #4d55ff, #8a2be2);" +
                        "-fx-background-radius: 0;" +
                        "-fx-border-color: #ffffff;" +
                        "-fx-border-width: 2px;" +
                        "-fx-border-radius: 5px;"
        );

        // Button hover effect (slightly lighter gradient)
        button.setOnMouseEntered(e -> {
            button.setStyle(
                    "-fx-background-color: linear-gradient(to bottom, #6670ff, #9b30ff);" +
                            "-fx-background-radius: 0;" +
                            "-fx-border-color: #ffffff;" +
                            "-fx-border-width: 2px;" +
                            "-fx-border-radius: 5px;"
            );
        });

        button.setOnMouseExited(e -> {
            button.setStyle(
                    "-fx-background-color: linear-gradient(to bottom, #4d55ff, #8a2be2);" +
                            "-fx-background-radius: 0;" +
                            "-fx-border-color: #ffffff;" +
                            "-fx-border-width: 2px;" +
                            "-fx-border-radius: 5px;"
            );
        });

        return button;
    }



    private void startGame() {
        System.out.println("Starting the game!");
        // Implement game start logic
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
}
