package ru.ssau.operatingsystem.project.typeingapp.utility.stats;

public class TypingStats implements IStatistic {
    private int characterCount;
    private int wordCount;
    private double wpm;
    private double spm;
    private int errorCount;

    public TypingStats(int characterCount, int wordCount, double wpm, double spm, int errorCount){
        this.characterCount = characterCount;
        this.wordCount = wordCount;
        this.wpm = wpm;
        this.spm = spm;
        this.errorCount = errorCount;
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

    public void setSpm(double spm){
        this.spm = spm;
    }

    public void setErrorCount(int errorCount){
        this.errorCount = errorCount;
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
    public double getSpm(){ return spm; }

    public int getErrorCount(){ return errorCount; }

}
