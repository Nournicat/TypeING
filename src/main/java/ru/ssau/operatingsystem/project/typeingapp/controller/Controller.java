package ru.ssau.operatingsystem.project.typeingapp.controller;

import javafx.scene.input.KeyEvent;
import ru.ssau.operatingsystem.project.typeingapp.textProviders.TypingTextProvider;

public interface Controller {
    void startTyping(TypingTextProvider provider);
}
