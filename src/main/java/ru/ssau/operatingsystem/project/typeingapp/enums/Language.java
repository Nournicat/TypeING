package ru.ssau.operatingsystem.project.typeingapp.enums;

public enum Language {
    RUSSIAN("russian"),
    ENGLISH("english"),
    CPP("cpp"),
    JAVA("java"),
    PYTHON("python"),
    QTE("qte");

    private final String name;

    Language(String name) {
        this.name = name;
    }
}
