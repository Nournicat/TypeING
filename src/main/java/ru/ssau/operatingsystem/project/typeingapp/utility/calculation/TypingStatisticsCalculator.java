package ru.ssau.operatingsystem.project.typeingapp.utility.calculation;

import javafx.scene.control.Label;
import lombok.Getter;
import lombok.Setter;
import ru.ssau.operatingsystem.project.typeingapp.enums.SpeedSetting;
import ru.ssau.operatingsystem.project.typeingapp.utility.Utility;
import ru.ssau.operatingsystem.project.typeingapp.utility.stats.TypingStats;

public class TypingStatisticsCalculator {
    private long startTime;
    private boolean started;

    private TypingStats currStatistic = new TypingStats(0, 0, 0, 0, 0);
    @Getter
    @Setter
    private Timer timeline = new Timer();

    @Getter
    @Setter
    private double accuracy;

    @Getter
    @Setter
    private String finalTime;

    public TypingStatisticsCalculator(){
        this.started = false;
        this.startTime = 0;
    }

    public void calculateStats(String text){
        if (!started && text != null && !text.isEmpty()){
            started = true;
        }

        int symbolsCount = text.length();
        int wordCount = 0;
        if (text != null && !text.trim().isEmpty()) {
            wordCount = text.trim().split("[\\s.,:;!?]+").length;
        }

        double elapsedMinutes = timeline.getElapsedSeconds()/60.0;
        double wpm = (elapsedMinutes > 0) ? wordCount / elapsedMinutes : 0;
        double spm = (elapsedMinutes > 0) ? symbolsCount / elapsedMinutes : 0;

        currStatistic.setWordCount(wordCount);
        currStatistic.setCharacterCount(symbolsCount);
        currStatistic.setWpm(wpm);
        currStatistic.setSpm(spm);
    }

    public void updateStats(Label symbolsCountLabel, Label errorCountLabel, Label speedLabel){
//        infoLabel.setText(String.format("Символов: %d, Слов: %d, Ошибок: %d, Скорость: %.1f слов/мин",
//                                        currStatistic.getCharacterCount(), currStatistic.getWordCount(), currStatistic.getErrorCount(), currStatistic.getWpm()));
        symbolsCountLabel.setText(String.format("Символы: %d", currStatistic.getCharacterCount()));
        errorCountLabel.setText(String.format("Ошибки: %d", currStatistic.getErrorCount()));

        if (Utility.getCurrentSpeedSetting() == SpeedSetting.WPM) speedLabel.setText(String.format("Скорость: %.1f слов/мин" , currStatistic.getWpm()));
        else speedLabel.setText(String.format("Скорость: %.1f символов/мин" , currStatistic.getSpm()));
    }

    public TypingStats getCurrStats(){ return currStatistic; }

    private void calculateAccuracy(int textLength){
        System.out.println(currStatistic.getErrorCount());
        System.out.println(textLength);
        System.out.println((double) currStatistic.getErrorCount()/textLength);

        accuracy = (1 - ((double) currStatistic.getErrorCount()/textLength))*100;
    }

    public void setDataResultPanel(int textLength, Label resultAccuracy, Label resultTime, Label resultSpeed){
        calculateAccuracy(textLength);
        System.out.println(accuracy);
        resultAccuracy.setText(String.format("%.2f%%", accuracy));
        resultTime.setText(finalTime);
        resultSpeed.setText(String.format("%.1f слов/мин", currStatistic.getWpm()));
    }
}
