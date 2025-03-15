package ru.ssau.operatingsystem.project.typeingapp.utility;

import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.ssau.operatingsystem.project.typeingapp.MainApp;
import ru.ssau.operatingsystem.project.typeingapp.TypingTextProvider;
import ru.ssau.operatingsystem.project.typeingapp.controller.TypingController;

import java.io.IOException;

public class Utility {
    private static Stage primaryStage;

    private Utility() {
    }
    public static Stage getPrimaryStage() {
        return primaryStage;
    }
    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    public static void changeScene(Scene scene){
        if (primaryStage == null)
            throw new NullPointerException("Primary Stage is not initialized");

        primaryStage.setScene(scene);
    }

    public static void changeCursor(Cursor cursor){
        primaryStage.getScene().setCursor(cursor);
    }
    public static void startTyping(TypingTextProvider stringProvider) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("3.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);

            Utility.changeScene(scene);
            ((TypingController) fxmlLoader.getController()).startTyping(stringProvider);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
