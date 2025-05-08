package ru.ssau.operatingsystem.project.typeingapp.enums;

import lombok.Getter;

@Getter
public enum TimeSetting {
    MINSEC("MINSEC"),
    SEC("SEC");

    private final String name;

    TimeSetting(String name) { this.name = name; }
}
