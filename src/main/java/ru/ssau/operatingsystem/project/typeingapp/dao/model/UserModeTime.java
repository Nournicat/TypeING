package ru.ssau.operatingsystem.project.typeingapp.dao.model;

import lombok.*;
import ru.ssau.operatingsystem.project.typeingapp.enums.Language;
import ru.ssau.operatingsystem.project.typeingapp.enums.LanguageType;
import ru.ssau.operatingsystem.project.typeingapp.enums.Mode;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class UserModeTime {
    private Mode mode;
    private Map<LanguageType, LocalTime> russianTime;
    private Map<LanguageType, LocalTime> englishTime;
    private Map<Language, LocalTime> programmingTime;
    private LocalTime QTETime;

    public UserModeTime(Mode mode){
        this.mode = mode;

        russianTime = new HashMap<>();
        englishTime = new HashMap<>();
        programmingTime = new HashMap<>();

        russianTime.put(LanguageType.LETTERS, LocalTime.of(0,0,0));
        russianTime.put(LanguageType.SHORT_WORDS, LocalTime.of(0,0,0));
        russianTime.put(LanguageType.ALPHABET, LocalTime.of(0,0,0));

        englishTime.put(LanguageType.LETTERS, LocalTime.of(0,0,0));
        englishTime.put(LanguageType.SHORT_WORDS, LocalTime.of(0,0,0));
        englishTime.put(LanguageType.ALPHABET, LocalTime.of(0,0,0));

        programmingTime.put(Language.JAVA, LocalTime.of(0,0,0));
        programmingTime.put(Language.CPP, LocalTime.of(0,0,0));
        programmingTime.put(Language.PYTHON, LocalTime.of(0,0,0));

        QTETime = LocalTime.of(0,0,0);
    }

    public UserModeTime(){
        this.mode = Mode.DEFAULT;

        russianTime = new HashMap<>();
        englishTime = new HashMap<>();
        programmingTime = new HashMap<>();

        russianTime.put(LanguageType.LETTERS, LocalTime.of(0,0,0));
        russianTime.put(LanguageType.SHORT_WORDS, LocalTime.of(0,0,0));
        russianTime.put(LanguageType.ALPHABET, LocalTime.of(0,0,0));

        englishTime.put(LanguageType.LETTERS, LocalTime.of(0,0,0));
        englishTime.put(LanguageType.SHORT_WORDS, LocalTime.of(0,0,0));
        englishTime.put(LanguageType.ALPHABET, LocalTime.of(0,0,0));

        programmingTime.put(Language.JAVA, LocalTime.of(0,0,0));
        programmingTime.put(Language.CPP, LocalTime.of(0,0,0));
        programmingTime.put(Language.PYTHON, LocalTime.of(0,0,0));

        QTETime = LocalTime.of(0,0,0);
    }
}
