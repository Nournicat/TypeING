package ru.ssau.operatingsystem.project.typeingapp.utility.calculation;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

// Timer
public class Timer {
    private long startTime;
    private Timeline timeline;
    private long elapsedMillis;


    public void startTimer(Label timerLabel){
        startTime = System.currentTimeMillis();

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateTimer(timerLabel)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void updateTimer(Label timerLabel){
        elapsedMillis = System.currentTimeMillis() - startTime;
        timerLabel.setText(formatTime(elapsedMillis));
    }

    private String formatTime(long millis){
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        seconds %= 60;
        return String.format("%02d : %02d", minutes, seconds);
    }

    public long getElapsedSeconds(){
        return elapsedMillis / 1000;
    }

    public void stopTimer(){
        if (timeline != null){
            timeline.stop();
        }
    }
}
