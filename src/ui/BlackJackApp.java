package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BlackJackApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        BlackJackController controller = new BlackJackController();
        // Finestra un po' più piccola e semplice
        Scene scene = new Scene(controller.getRoot(), 800, 600);
        
        primaryStage.setTitle("Il mio gioco del Blackjack");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}