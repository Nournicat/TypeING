package ru.ssau.operatingsystem.project.typeingapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import ru.ssau.operatingsystem.project.typeingapp.textProviders.TypingTextProvider;
import ru.ssau.operatingsystem.project.typeingapp.utility.Utility;
import ru.ssau.operatingsystem.project.typeingapp.utility.calculation.TypingStatisticsCalculator;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class AbstractTypingController implements Initializable, Controllers{
    protected TypingStatisticsCalculator calculator = new TypingStatisticsCalculator();
    protected TypingTextProvider provider;

    protected boolean typingStarted = false;
    protected boolean typingInitialized = false;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        getBackstage().sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
//                calculator.getTimeline().startTimer(getTimerLabel());
                getBackstage().requestFocus();
            }
        });
    }

    public void startTyping(TypingTextProvider stringProvider){
        this.provider = stringProvider;
        restartScene();
        String text = getText(stringProvider);
        getOverlayText().setText(text);

        Scene scene = Utility.getPrimaryStage().getScene();

        scene.setOnKeyPressed(event -> {
            System.out.println(event.getCode());
            if ((event.getCode() == KeyCode.ENTER) && (!typingInitialized)){
                typingStarted = true;
                typingInitialized = true;
                getPreparingPanel().setVisible(false);
                calculator.getTimeline().startTimer(getTimerLabel());
                scene.setOnKeyTyped(this::handleKeyPressed);
                getBackstage().requestFocus();
            }
        });
        getBackstage().requestFocus();
//        calculator.getTimeline().startTimer(getTimerLabel());
//        scene.setOnKeyTyped(this::handleKeyPressed);
//        getBackstage().requestFocus();
    }

    protected void handleKeyPressed(KeyEvent event) {
        if (!typingStarted) return;
        if (getOverlayText().getText().isEmpty()) return;
        if (event.getCharacter().isEmpty()) return;

        char enteredKey = event.getCharacter().charAt(0);
        char currentKey = getOverlayText().getText().charAt(0);
        if (enteredKey == currentKey){
            getEnteredText().setText(getEnteredText().getText() + enteredKey);
            getOverlayText().setText(getOverlayText().getText().substring(1));
        }
        calculator.calculateStats(getEnteredText().getText());
        calculator.updateStats(getInfoLabel());

        if (getOverlayText().getText().isEmpty()){
            calculator.getTimeline().stopTimer();
            getResultPanel().setVisible(true);
        }
    }

    private String getText(TypingTextProvider stringProvider){
        return stringProvider.generate();
    }

    protected void restartScene(){
        typingStarted = false;
        typingInitialized = false;
        getPreparingPanel().setVisible(true);
        getResultPanel().setVisible(false);
        getEnteredText().setText("");

        getInfoLabel().setText("Наберите текст ниже. Скорость набора появится здесь.");
        getTimerLabel().setText("0 : 00");
    }

    @FXML
    protected void restartTyping(){
        restartScene();
        getOverlayText().setText(provider.generate());
        startTyping(provider);
//        calculator.getTimeline().startTimer(getTimerLabel());
    }

    @FXML
    protected void exitScene(){
        Utility.backToMenu();
    }

    protected abstract VBox getBackstage();
    protected abstract Label getInfoLabel();
    protected abstract Label getTimerLabel();
    protected abstract Label getEnteredText();
    protected abstract Label getOverlayText();
    protected abstract VBox getResultPanel();
    protected abstract AnchorPane getPreparingPanel();
}
