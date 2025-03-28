package ru.ssau.operatingsystem.project.typeingapp.utility.stats;

public class TypingStats implements IStatistic {
    private int characterCount;
    private int wordCount;
    private double wpm;

    public TypingStats(int characterCount, int wordCount, double wpm){
        this.characterCount = characterCount;
        this.wordCount = wordCount;
        this.wpm = wpm;
    }

    public void setCharacterCount(int characterCount){
        this.characterCount = characterCount;
    }

    public void setWordCount(int wordCount){
        this.wordCount = wordCount;
    }

    public void setWpm(double wpm){
        this.wpm = wpm;
    }

    public int getCharacterCount(){
        return characterCount;
    }

    public int getWordCount(){
        return wordCount;
    }

    public double getWpm(){
        return wpm;
    }

}
