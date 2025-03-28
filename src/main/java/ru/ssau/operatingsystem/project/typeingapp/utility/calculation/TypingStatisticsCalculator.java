package ru.ssau.operatingsystem.project.typeingapp.utility.calculation;

import javafx.scene.control.Label;
import ru.ssau.operatingsystem.project.typeingapp.utility.stats.TypingStats;

public class TypingStatisticsCalculator {
    private long startTime;
    private boolean started;

    private TypingStats currStatistic = new TypingStats(0, 0, 0);

    private Timer timeline = new Timer();

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

    public void updateStats(Label infoLabel){
        infoLabel.setText(String.format("Символов: %d, Слов: %d, Скорость: %.1f слов/мин",
                                        currStatistic.getCharacterCount(), currStatistic.getWordCount(), currStatistic.getWpm()));
    }

    public Timer getTimeline(){
        return timeline;
    }
}
