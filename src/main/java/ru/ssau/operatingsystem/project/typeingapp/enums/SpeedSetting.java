package ru.ssau.operatingsystem.project.typeingapp.enums;

import lombok.Getter;

@Getter
public enum SpeedSetting {
    WPM("WPM"),
    SPM("SPM");

    private final String name;

    SpeedSetting(String name) { this.name = name; }
}
