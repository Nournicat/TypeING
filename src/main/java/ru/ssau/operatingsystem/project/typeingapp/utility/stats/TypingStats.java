package ru.ssau.operatingsystem.project.typeingapp.utility.stats;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TypingStats implements IStatistic {
    private int characterCount;
    private int wordCount;
    private double wpm;
    private double spm;
    private int errorCount;
    private double accuracy;

    public TypingStats(int characterCount, int wordCount, double wpm, double spm, int errorCount){
        this.characterCount = characterCount;
        this.wordCount = wordCount;
        this.wpm = wpm;
        this.spm = spm;
        this.errorCount = errorCount;
    }



}
