package ru.ssau.operatingsystem.project.typeingapp.controller.strategy;

import javafx.scene.input.KeyEvent;
import ru.ssau.operatingsystem.project.typeingapp.controller.TypingController;

public interface HandleStrategy {
    void handleKeyPressed(KeyEvent event, TypingController context);
}
