package ru.ssau.operatingsystem.project.typeingapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import ru.ssau.operatingsystem.project.typeingapp.MainApp;
import ru.ssau.operatingsystem.project.typeingapp.textProviders.TypingTextProvider;
import ru.ssau.operatingsystem.project.typeingapp.utility.Mode;
import ru.ssau.operatingsystem.project.typeingapp.utility.Utility;
import ru.ssau.operatingsystem.project.typeingapp.utility.calculation.TypingStatisticsCalculator;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.function.Function;

public class TypingController implements Initializable, Controllers{

   
    @FXML
    public VBox backstage;
    @FXML
    public Label infoLabel;

    @FXML
    public Label timerLabel;

    @FXML
    public Label enteredText;

    @FXML
    public Label overlayText;

    @FXML
    public VBox resultPanel;

    @FXML
    public AnchorPane preparingPanel;

    private TypingStatisticsCalculator calculator = new TypingStatisticsCalculator();
    private TypingTextProvider provider;

    private boolean typingStarted = false;
    private boolean typingInitialized = false; // флаг для сигнализации того, что инициализация ввода уже прошла(или не прошла)

    private boolean flagMistake = false;
    private boolean firstClick = true; // чтобы игнорировать первый ввод Enter, а последующие учитывать как обычные символы

    private void modeToHandle(Scene scene){
        switch (Utility.getCurrentMode()){
            case ONE_LIFE -> scene.setOnKeyTyped(this::handleKeyPressedOneLife);
            case WITH_ERASING -> scene.setOnKeyTyped(this::handleKeyPressedWithErasing);
            default -> scene.setOnKeyTyped(this::handleKeyPressedDefault);
        }
    }

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
                modeToHandle(scene);
                getBackstage().requestFocus();
            }
        });
        getBackstage().requestFocus();
//        calculator.getTimeline().startTimer(getTimerLabel());
//        scene.setOnKeyTyped(this::handleKeyPressed);
//        getBackstage().requestFocus();
    }

    private void handleKeyPressedDefault(KeyEvent event) {
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

    private void handleKeyPressedWithErasing(KeyEvent event) {
        if (!typingStarted) return;
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

    private void handleKeyPressedOneLife(KeyEvent event) {
        if (("\r".equals(event.getCharacter()) || "\n".equals(event.getCharacter())) && firstClick){
            firstClick = false;
            return;
        }

        if (!typingStarted) return;
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
            flagMistake = true;
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

    private void restartScene(){
        typingStarted = false;
        typingInitialized = false; // Сброс, чтобы снова можно было начать ввод

        flagMistake = false;
        firstClick = true;
        getPreparingPanel().setVisible(true);
        getResultPanel().setVisible(false);
        getEnteredText().setText("");

        getInfoLabel().setText("Наберите текст ниже. Скорость набора появится здесь.");
        getTimerLabel().setText("0 : 00");
    }

    @FXML
    private void restartTyping(){
        restartScene();
        getOverlayText().setText(provider.generate());
        startTyping(provider);
//        calculator.getTimeline().startTimer(getTimerLabel());
    }

    @FXML
    private void exitScene(){
        Utility.backToMenu();
    }

    private VBox getBackstage(){ return backstage; }
    private Label getInfoLabel(){ return infoLabel; }
    private Label getTimerLabel(){ return timerLabel; }
    private Label getEnteredText(){ return enteredText; }
    private Label getOverlayText(){ return overlayText; }
    private VBox getResultPanel(){ return resultPanel; }
    private AnchorPane getPreparingPanel(){ return preparingPanel; }
}
