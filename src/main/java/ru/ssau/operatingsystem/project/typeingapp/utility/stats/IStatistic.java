package ru.ssau.operatingsystem.project.typeingapp.utility.stats;

public interface IStatistic {
    int getCharacterCount();
    void setCharacterCount(int count);
    double getWpm();
    void setWpm(double wpm);
    double getSpm();
    void setSpm(double spm);
    int getErrorCount();
    void setErrorCount(int errors);
    double getAccuracy();
    void setAccuracy(double accuracy);
    void setWordCount(int wordCount);
    int getCorrectCount();
    void setCorrectCount(int correctCount);
}
