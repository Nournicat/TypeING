package ru.ssau.operatingsystem.project.typeingapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

public class OneLifeTypingController extends AbstractTypingController{

    private boolean flagMistake = false;

    @FXML
    private VBox backstage;
    @FXML
    private Label infoLabel;

    @FXML
    private Label timerLabel;

    @FXML
    private Label enteredText;

    @FXML
    private Label overlayText;

    @FXML
    private VBox resultPanel;

    @Override
    protected void handleKeyPressed(KeyEvent event) {
        if (flagMistake) return;
        if (getOverlayText().getText().isEmpty()) return;
        if (event.getCharacter().isEmpty()) return;

        char enteredKey = event.getCharacter().charAt(0);
        char currentKey = getOverlayText().getText().charAt(0);
        if (enteredKey == currentKey){
            getEnteredText().setText(getEnteredText().getText() + enteredKey);
            getOverlayText().setText(getOverlayText().getText().substring(1));
        }
        else{
            calculator.getTimeline().stopTimer();
            getResultPanel().setVisible(true);
        }
        calculator.calculateStats(getEnteredText().getText());
        calculator.updateStats(getInfoLabel());

        if (getOverlayText().getText().isEmpty()){
            calculator.getTimeline().stopTimer();
            getResultPanel().setVisible(true);
            flagMistake = true;
        }
    }

    @Override
    protected void restartScene(){
        flagMistake = false;
        getResultPanel().setVisible(false);
        getEnteredText().setText("");

        getInfoLabel().setText("Наберите текст ниже. Скорость набора появится здесь.");
        getTimerLabel().setText("0 : 00");
    }

    @Override
    protected VBox getBackstage() {
        return backstage;
    }

    @Override
    protected Label getInfoLabel() {
        return infoLabel;
    }

    @Override
    protected Label getTimerLabel() {
        return timerLabel;
    }

    @Override
    protected Label getEnteredText() {
        return enteredText;
    }

    @Override
    protected Label getOverlayText() {
        return overlayText;
    }

    @Override
    protected VBox getResultPanel() {
        return resultPanel;
    }
}
