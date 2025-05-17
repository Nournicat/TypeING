package ru.ssau.operatingsystem.project.typeingapp.enums;

import lombok.Getter;

@Getter
public enum Mode {
    DEFAULT("Default"),
    ONE_LIFE("OneLife"),
    WITH_ERASING("WithErasing");

    private final String name;

    Mode(String name) {
        this.name = name;
    }

}
