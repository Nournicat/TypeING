package ru.ssau.operatingsystem.project.typeingapp.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import ru.ssau.operatingsystem.project.typeingapp.utility.Utility;

public class TypingController {
    @FXML
    private VBox backstage;
    @FXML
    private Label infoLabel;


    @FXML
    private Label enteredText;
    @FXML
    private Label overlayText;

    public void initialize(){
            System.out.println("Запущено");
            Scene scene = Utility.getPrimaryStage().getScene();
            scene.setOnKeyPressed(this::handleKeyPressed);
            backstage.requestFocus();
    }
    public void handleKeyPressed(KeyEvent event) {
        // Пример обработки нажатия клавиши
        infoLabel.setText("Нажата клавиша: " + event.getText());
        System.out.println("Нажата клавиша: " + event.getText());
    }
}
