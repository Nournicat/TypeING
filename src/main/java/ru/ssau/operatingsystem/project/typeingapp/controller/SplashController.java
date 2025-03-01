package ru.ssau.operatingsystem.project.typeingapp;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.FadeTransition;
import javafx.scene.paint.Color;

import java.io.IOException;

public class SplashController {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label label1;

    @FXML
    private Label label2;

    @FXML
    private Label label3;

    public void initialize() {
        Task<Void> splashTask = new Task<>() {
            @Override
            protected Void call() {
                for (int i = 0; i <= 100; i += 10) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    updateProgress(i, 100);
                }
                return null;
            }
        };

        progressBar.progressProperty().bind(splashTask.progressProperty());

        splashTask.setOnSucceeded(event -> Platform.runLater(this::loadMainMenu));
        new Thread(splashTask).start();

        playLabelAnimations();
        playColorAnimations();
    }

    private void playLabelAnimations() {
        FadeTransition fadeIn1 = new FadeTransition(Duration.seconds(1), label1);
        fadeIn1.setFromValue(0);
        fadeIn1.setToValue(1);
        fadeIn1.play();

        fadeIn1.setOnFinished(event -> {
            FadeTransition fadeIn2 = new FadeTransition(Duration.seconds(1), label2);
            fadeIn2.setFromValue(0);
            fadeIn2.setToValue(1);
            fadeIn2.play();

            fadeIn2.setOnFinished(event2 -> {
                FadeTransition fadeIn3 = new FadeTransition(Duration.seconds(1), label3);
                fadeIn3.setFromValue(0);
                fadeIn3.setToValue(1);
                fadeIn3.play();
            });
        });
    }

    private void playColorAnimations() {
        Color baseColor = Color.web("#379728");

        Timeline colorAnimation = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        new KeyValue(label1.textFillProperty(), baseColor.deriveColor(0, 1, 1, 1)),
                        new KeyValue(label2.textFillProperty(), baseColor.deriveColor(0, 1, 1, 1)),
                        new KeyValue(label3.textFillProperty(), baseColor.deriveColor(0, 1, 1, 1))
                ),
                new KeyFrame(Duration.seconds(3),
                        new KeyValue(label1.textFillProperty(), baseColor.deriveColor(0, 1, 1, 0.2)),
                        new KeyValue(label2.textFillProperty(), baseColor.deriveColor(0, 1, 1, 0.2)),
                        new KeyValue(label3.textFillProperty(), baseColor.deriveColor(0, 1, 1, 0.2))
                ),
                new KeyFrame(Duration.seconds(6),
                        new KeyValue(label1.textFillProperty(), baseColor.deriveColor(0, 1, 1, 1)),
                        new KeyValue(label2.textFillProperty(), baseColor.deriveColor(0, 1, 1, 1)),
                        new KeyValue(label3.textFillProperty(), baseColor.deriveColor(0, 1, 1, 1))
                )
        );

        colorAnimation.setCycleCount(Timeline.INDEFINITE);
        colorAnimation.setAutoReverse(true);
        colorAnimation.play();
    }

    private void loadMainMenu() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("1.fxml"));
            Scene mainMenuScene = new Scene(fxmlLoader.load(), 600, 400);

            Stage stage = MainApp.getPrimaryStage();

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), rootPane);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(e -> stage.setScene(mainMenuScene));
            fadeOut.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
