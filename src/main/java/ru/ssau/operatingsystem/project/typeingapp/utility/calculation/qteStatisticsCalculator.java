package ru.ssau.operatingsystem.project.typeingapp.utility.calculation;

import javafx.scene.control.Label;
import lombok.Getter;
import lombok.Setter;
import ru.ssau.operatingsystem.project.typeingapp.enums.SpeedSetting;
import ru.ssau.operatingsystem.project.typeingapp.utility.Utility;
import ru.ssau.operatingsystem.project.typeingapp.utility.stats.IStatistic;
import ru.ssau.operatingsystem.project.typeingapp.utility.stats.TypingStats;

public class qteStatisticsCalculator {
    private long startTime;
    private boolean started;

    private IStatistic currStatistic = new TypingStats(0, 0, 0, 0, 0);
    @Getter
    @Setter
    private Timer timeline = new Timer();

    @Getter
    @Setter
    private String finalTime;

    public qteStatisticsCalculator(){
        this.started = false;
        this.startTime = 0;
    }

    public void updateStats(Label correctCountLabel, Label errorCountLabel){
//        infoLabel.setText(String.format("Символов: %d, Слов: %d, Ошибок: %d, Скорость: %.1f слов/мин",
//                                        currStatistic.getCharacterCount(), currStatistic.getWordCount(), currStatistic.getErrorCount(), currStatistic.getWpm()));
        correctCountLabel.setText(String.format("Верно: %d", currStatistic.getCorrectCount()));
        errorCountLabel.setText(String.format("Ошибки: %d", currStatistic.getErrorCount()));

    }

    public IStatistic getCurrStats(){ return currStatistic; }

    private void calculateAccuracy(int textLength){
        double accuracy = (1 - ((double) currStatistic.getErrorCount()/textLength))*100;
        currStatistic.setAccuracy(accuracy);
    }

    public void setDataResultPanel(int textLength, Label resultAccuracy, Label resultTime, Label resultSpeed){
        calculateAccuracy(textLength);
        System.out.println(currStatistic.getAccuracy());
        resultAccuracy.setText(String.format("%.2f%%", currStatistic.getAccuracy()));
        resultTime.setText(finalTime);
        resultSpeed.setText(String.format("%.1f симв/мин", currStatistic.getSpm()));
    }
}
