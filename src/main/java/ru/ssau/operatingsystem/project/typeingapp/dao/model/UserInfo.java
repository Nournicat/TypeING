package ru.ssau.operatingsystem.project.typeingapp.dao.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.ssau.operatingsystem.project.typeingapp.enums.Mode;

import java.awt.image.BufferedImage;
import java.time.LocalTime;

@EqualsAndHashCode
@Getter
@Setter
public class UserInfo {
    private Mode mode;
    private String name = "DefaultUser";
    private BufferedImage avatar;

    private int countSymbols = 0;
    private float overallAccuracy = 0;
    private float bestAccuracy = 0;

    private LocalTime averageTime;
    private LocalTime bestTime;

    private float bestSpeed = 0;
    private float averageSpeed = 0;

    public UserInfo(Mode mode, BufferedImage avatar, String name, int countSymbols, float overallAccuracy, float bestAccuracy, LocalTime averageTime, LocalTime bestTime, float bestSpeed, float averageSpeed) {
        this.mode = mode;
        this.name = name;
        this.avatar = avatar;
        this.countSymbols = countSymbols;
        this.overallAccuracy = overallAccuracy;
        this.bestAccuracy = bestAccuracy;
        this.averageTime = averageTime;
        this.bestTime = bestTime;
        this.bestSpeed = bestSpeed;
        this.averageSpeed = averageSpeed;
    }

    public UserInfo(Mode mode) {
        this.mode = mode;
        this.averageTime = LocalTime.of(0,0,0);
        this.bestTime = LocalTime.of(0,0,0);
        this.avatar = null;
    }
}
