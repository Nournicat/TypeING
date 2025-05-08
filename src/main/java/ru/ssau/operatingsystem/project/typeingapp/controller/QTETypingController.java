package ru.ssau.operatingsystem.project.typeingapp.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import ru.ssau.operatingsystem.project.typeingapp.textProviders.TypingTextProvider;
import ru.ssau.operatingsystem.project.typeingapp.utility.Utility;
import ru.ssau.operatingsystem.project.typeingapp.utility.calculation.TypingStatisticsCalculator;

import java.net.URL;
import java.util.ResourceBundle;

public class QTETypingController implements Initializable, Controller{
    @FXML
    private VBox backstage;
    @FXML
    private Label infoLabel;

    @FXML
    private Label timerLabel;

    @FXML
    private VBox resultPanel;

    @FXML
    private Label currentKey;

    private Timeline backgroundTimeline;
    private int intervalSeconds;

    private TypingStatisticsCalculator calculator = new TypingStatisticsCalculator();

    @Override
    public void initialize(URL location, ResourceBundle resources){
        backstage.sceneProperty().addListener((_, _, newScene) -> {
            if (newScene != null) {
                calculator.getTimeline().startTimer(timerLabel, Utility.getCurrentTimeSetting());
                backstage.requestFocus();
            }
        });

        backgroundTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            long elapsed = calculator.getTimeline().getElapsedSeconds();
            if (elapsed % intervalSeconds == 0 && elapsed != 0) {
                System.out.println("Какое-то событие каждые 5 секунд");
            }
        }));
        backgroundTimeline.setCycleCount(Timeline.INDEFINITE);
        backgroundTimeline.play();
    }
    // уровень сложности планируется доставать из Settings
    @Override
    public void startTyping(TypingTextProvider keyProvider){}

}
