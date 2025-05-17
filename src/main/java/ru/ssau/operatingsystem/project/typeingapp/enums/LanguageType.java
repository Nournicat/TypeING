package ru.ssau.operatingsystem.project.typeingapp.enums;

public enum LanguageType {
    NO_TYPE("no type"),
    LETTERS("letters"),
    ALPHABET("alphabet"),
    SHORT_WORDS("short_words");

    private final String name;

    LanguageType(String name) {
        this.name = name;
    }
}
