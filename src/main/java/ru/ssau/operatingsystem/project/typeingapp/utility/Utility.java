package ru.ssau.operatingsystem.project.typeingapp.utility;

import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.ssau.operatingsystem.project.typeingapp.MainApp;
import ru.ssau.operatingsystem.project.typeingapp.controller.*;
import ru.ssau.operatingsystem.project.typeingapp.textProviders.TypingTextProvider;

import java.io.IOException;

public class Utility {
    private static Stage primaryStage;
    private static Mode currentMode = Mode.DEFAULT;

    public static Mode getCurrentMode(){return currentMode;}
    public static void setCurrentMode(Mode newMode){currentMode = newMode;}

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
            FXMLLoader fxmlLoader;
            switch(currentMode){
                case QTE -> fxmlLoader = new FXMLLoader(MainApp.class.getResource("3/qte.fxml"));
                case ONE_LIFE -> fxmlLoader = new FXMLLoader(MainApp.class.getResource("3/one_life.fxml"));
                case WITH_ERASING -> fxmlLoader = new FXMLLoader(MainApp.class.getResource("3/with_erase.fxml"));
                default -> fxmlLoader = new FXMLLoader(MainApp.class.getResource("3/default.fxml"));
            }

            Scene scene = new Scene(fxmlLoader.load(), 600, 400);

            Utility.changeScene(scene);
            ((Controllers) fxmlLoader.getController()).startTyping(stringProvider);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void backToMenu(){
        Scene scene;
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("2.fxml"));
        try {
            scene = new Scene(fxmlLoader.load(), 600, 400);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Utility.changeScene(scene);
    }
}
