package ru.ssau.operatingsystem.project.typeingapp.utility;

public class TypingStatisticsCalculator {
    private long startTime;
    private boolean started;
    private static final int AVG_WORD_LENGTH = 5;

    public TypingStatisticsCalculator(){
        this.started = false;
        this.startTime = 0;
    }

    public void calculateStats(String text, TypingStats currStatistic){
        if (!started && text != null && !text.isEmpty()){
            started = true;
            startTime = System.currentTimeMillis();
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
}
