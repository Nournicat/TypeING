package ru.ssau.operatingsystem.project.typeingapp.dao.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.ssau.operatingsystem.project.typeingapp.enums.Language;
import ru.ssau.operatingsystem.project.typeingapp.enums.LanguageType;
import ru.ssau.operatingsystem.project.typeingapp.enums.Mode;

import java.time.LocalTime;

@EqualsAndHashCode
@Getter
@Setter
public class UserInfo {
    private Mode mode;
    private Language language;
    private LanguageType languageType;
    private String name = "DefaultUser";

    private int countStarts;

    private int countSymbols = 0;
    private float overallAccuracy = 0;
    private float bestAccuracy = 0;

    private LocalTime averageTime;
    private LocalTime bestTime;

    private float bestSpeed = 0;
    private float averageSpeed = 0;

    public UserInfo(Mode mode, Language language, LanguageType languageType, String name, int countSymbols, float overallAccuracy, float bestAccuracy, LocalTime averageTime, LocalTime bestTime, float bestSpeed, float averageSpeed, int countStarts) {
        this.mode = mode;
        this.language = language;
        this.languageType = languageType;
        this.name = name;
        this.countSymbols = countSymbols;
        this.overallAccuracy = overallAccuracy;
        this.bestAccuracy = bestAccuracy;
        this.averageTime = averageTime;
        this.bestTime = bestTime;
        this.bestSpeed = bestSpeed;
        this.averageSpeed = averageSpeed;
        this.countStarts = countStarts;
    }

    public UserInfo(Mode mode, Language language, LanguageType languageType) {
        this.mode = mode;
        this.language = language;
        this.languageType = languageType;
        this.averageTime = LocalTime.of(0,0,0);
        this.bestTime = LocalTime.of(0,0,0);
    }

    public UserInfo(){
        this.mode = Mode.DEFAULT;
        this.language = Language.RUSSIAN;
        this.languageType = LanguageType.LETTERS;
        this.averageTime = LocalTime.of(0,0,0);
        this.bestTime = LocalTime.of(0,0,0);
    }
}
