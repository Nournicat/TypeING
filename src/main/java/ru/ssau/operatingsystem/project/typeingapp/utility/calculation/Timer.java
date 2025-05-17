package ru.ssau.operatingsystem.project.typeingapp.utility.calculation;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import ru.ssau.operatingsystem.project.typeingapp.enums.TimeSetting;

// Timer
public class Timer {
    private long startTime;
    private Timeline timeline;
    private long elapsedMillis;
    private boolean timerStarted = false;


    public void startTimer(Label timerLabel, TimeSetting mode){
        startTime = System.currentTimeMillis();
        timerStarted = true;
        if (mode == TimeSetting.MINSEC) timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateTimerMinSec(timerLabel)));
        else timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateTimerSec(timerLabel)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void updateTimerMinSec(Label timerLabel){
        elapsedMillis = System.currentTimeMillis() - startTime;
        timerLabel.setText(formatTimeMinSec(elapsedMillis));
    }

    private void updateTimerSec(Label timerLabel){
        elapsedMillis = System.currentTimeMillis() - startTime;

        timerLabel.setText(formatTimeSec(elapsedMillis));
    }

    private String formatTimeMinSec(long millis){
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        seconds %= 60;

        return String.format("%d : %02d", minutes, seconds);
    }

    private String formatTimeSec(long elapsedMillis){
        long seconds = getElapsedSeconds();
        return String.format("%02d", seconds);
    }

    public long getElapsedSeconds(){
        return elapsedMillis / 1000;
    }

    public void stopTimer(){
        if (timeline != null){
            timeline.stop();
        }
        timerStarted = false;
    }

    public boolean getTimerStarted(){ return timerStarted; }
}
