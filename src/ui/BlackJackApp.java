package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BlackJackApp extends Application
{

    @Override
    public void start(Stage primaryStage)
    {
        BlackJackController controller = new BlackJackController();
        Scene scene = new Scene(controller.getRoot(), 1000, 700);
        scene.getStylesheets().add(getClass().getResource("/ui/style.css").toExternalForm());

        primaryStage.setTitle("BlackJack");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}