package ru.ssau.operatingsystem.project.typeingapp.utility;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class Utility {
    private static Stage primaryStage;

    private Utility() {
    }

    public static Stage getPrimaryStage() {
        if(primaryStage == null)
            throw new NullPointerException("Primary Stage is not initialized");

        return primaryStage;
    }

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    public static void changeScene(Scene scene){
        if(primaryStage == null)
            throw new NullPointerException("Primary Stage is not initialized");

        primaryStage.setScene(scene);
    }
}
