package ru.ssau.operatingsystem.project.typeingapp.enums;


import lombok.Getter;

@Getter
public enum QTEdifficult {
    EASY("Easy"),
    MEDIUM("Medium"),
    HARD("Hard"),
    NAN("NAN");

    private final String name;

    QTEdifficult(String name) {
        this.name = name;
    }
}