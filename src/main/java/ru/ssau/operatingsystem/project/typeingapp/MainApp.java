package ru.ssau.operatingsystem.project.typeingapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ru.ssau.operatingsystem.project.typeingapp.utility.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class MainApp extends Application {
    private static Stage primaryStage;
    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("2.fxml"));
        Scene mainMenu = new Scene(fxmlLoader.load(), 979, 634);
        stage.setTitle("TypingApp");
        stage.setResizable(false);
        stage.getIcons().add(new Image("Keyboard.png"));
        stage.setScene(mainMenu);

        mainMenu.getStylesheets().add((MainApp.class.getResource("styles.css")).toExternalForm());

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