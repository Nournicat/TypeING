package ru.ssau.operatingsystem.project.typeingapp.controller;

import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.fxml.FXML;

public class WithErasingTypingController extends AbstractTypingController{

    public WithErasingTypingController(DefaultTypingController controller) {
        this.backstage = controller.backstage;
        this.infoLabel = controller.infoLabel;
        this.timerLabel = controller.timerLabel;
        this.resultPanel = controller.resultPanel;
        this.provider = controller.provider;
        this.calculator = controller.calculator;
        this.typingStarted = controller.typingStarted;
        this.preparingPanel = controller.preparingPanel;
        this.enteredText = controller.enteredText;
        this.overlayText = controller.overlayText;
    }

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

    @FXML
    private AnchorPane preparingPanel;

    @Override
    protected void handleKeyPressed(KeyEvent event) {
        if (getOverlayText().getText().isEmpty()) return;
        if (event.getCharacter().isEmpty()) return;

        char enteredKey = event.getCharacter().charAt(0);
        char currentKey = getOverlayText().getText().charAt(0);
        if (enteredKey == currentKey){
            getEnteredText().setText(getEnteredText().getText() + enteredKey);
            getOverlayText().setText(getOverlayText().getText().substring(1));
        }
        if ("\b".equals(event.getCharacter())){
            if (!getEnteredText().getText().isEmpty()) {
                char deletedChar = getEnteredText().getText().charAt(getEnteredText().getText().length() - 1);
                getEnteredText().setText(getEnteredText().getText().substring(0, getEnteredText().getText().length() - 1));
                getOverlayText().setText(deletedChar + getOverlayText().getText());
            }
        }
        calculator.calculateStats(getEnteredText().getText());
        calculator.updateStats(getInfoLabel());

        if (getOverlayText().getText().isEmpty()){
            calculator.getTimeline().stopTimer();
            getResultPanel().setVisible(true);
        }
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

    @Override
    protected AnchorPane getPreparingPanel(){
        return preparingPanel;
    }
}
