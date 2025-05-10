package ru.ssau.operatingsystem.project.typeingapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import lombok.Getter;
import ru.ssau.operatingsystem.project.typeingapp.enums.SpeedSetting;
import ru.ssau.operatingsystem.project.typeingapp.utility.Settings;
import ru.ssau.operatingsystem.project.typeingapp.utility.Utility;

import java.io.IOException;
import java.net.URISyntaxException;

public class MainApp extends Application {
    @Getter
    private static Stage primaryStage;
    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        Font.loadFont(
                getClass().getResourceAsStream("font/Literata-Regular.ttf"),
                1
        );
        Font.loadFont(
                getClass().getResourceAsStream("font/Inter-Regular.ttf.ttf"),
                1
        );

        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("2.fxml"));
        Scene mainMenu = new Scene(fxmlLoader.load(), 979, 634);
        stage.setTitle("TypingApp");
        stage.setResizable(false);
        stage.getIcons().add(new Image("Keyboard.png"));
        stage.setScene(mainMenu);

        stage.show();
        Utility.setPrimaryStage(stage);
    }

    public static void main(String[] args) throws URISyntaxException {
        Settings.loadProperties();
        launch();
    }
}