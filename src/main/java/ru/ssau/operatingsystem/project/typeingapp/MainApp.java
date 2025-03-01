package ru.ssau.operatingsystem.project.typeingapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ru.ssau.operatingsystem.project.typeingapp.utility.Utility;
import java.io.IOException;

public class MainApp extends Application {
    private static Stage primaryStage;
    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("splash.fxml"));
        Scene splashScene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("TypingApp");
        stage.getIcons().add(new Image("Keyboard.png"));
        stage.setScene(splashScene);
        stage.show();
        Utility.setPrimaryStage(stage);
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch();
    }

}