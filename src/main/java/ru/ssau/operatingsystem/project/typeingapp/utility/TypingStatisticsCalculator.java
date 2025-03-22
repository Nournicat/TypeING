package ru.ssau.operatingsystem.project.typeingapp.utility;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.sql.Time;

public class TypingStatisticsCalculator {
    private long startTime;
    private boolean started;

    private TypingStats currStatistic = new TypingStats(0, 0, 0);

    private Timeline timeline;

    public TypingStatisticsCalculator(){
        this.started = false;
        this.startTime = 0;
    }

    public void calculateStats(String text){
        if (!started && text != null && !text.isEmpty()){
            started = true;
        }

        int characters = text.length();
        int wordCount = 0;
        if (text != null && !text.trim().isEmpty()) {
            wordCount = text.trim().split("[\\s.,:;!?]+").length;
        }
        long elapsedMillis = System.currentTimeMillis() - startTime;
        double elapsedMinutes = elapsedMillis/60000.0;
        double wpm = (elapsedMinutes > 0) ? wordCount / elapsedMinutes : 0;

        currStatistic.setWordCount(wordCount);
        currStatistic.setCharacterCount(characters);
        currStatistic.setWpm(wpm);
    }

    public void startTimer(Label timerLabel){
        startTime = System.currentTimeMillis();

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateTimer(timerLabel)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void updateTimer(Label timerLabel){
        long elapsedMillis = System.currentTimeMillis() - startTime;
        timerLabel.setText(formatTime(elapsedMillis));
    }

    private String formatTime(long millis){
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        seconds %= 60;
        return String.format("%02d : %02d", minutes, seconds);
    }

    public void stopTimer(){
        if (timeline != null){
            timeline.stop();
        }
    }

    public void updateStats(Label infoLabel){
        infoLabel.setText(String.format("Символов: %d, Слов: %d, Скорость: %.1f слов/мин",
                                        currStatistic.getCharacterCount(), currStatistic.getWordCount(), currStatistic.getWpm()));
    }
}
