package ru.ssau.operatingsystem.project.typeingapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import ru.ssau.operatingsystem.project.typeingapp.controller.strategy.HandleDefaultStrategy;
import ru.ssau.operatingsystem.project.typeingapp.controller.strategy.HandleOneLifeStrategy;
import ru.ssau.operatingsystem.project.typeingapp.controller.strategy.HandleStrategy;
import ru.ssau.operatingsystem.project.typeingapp.controller.strategy.HandleWithErasingStrategy;
import ru.ssau.operatingsystem.project.typeingapp.enums.SpeedSetting;
import ru.ssau.operatingsystem.project.typeingapp.enums.TimeSetting;
import ru.ssau.operatingsystem.project.typeingapp.textProviders.TypingTextProvider;
import ru.ssau.operatingsystem.project.typeingapp.utility.ElementStack;
import ru.ssau.operatingsystem.project.typeingapp.utility.Utility;
import ru.ssau.operatingsystem.project.typeingapp.utility.calculation.TypingStatisticsCalculator;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;

@Getter
@Setter
public class TypingController implements Initializable, Controller{

    @FXML private VBox backstage;
    @FXML private Label infoLabel;
    @FXML private Label timerLabel;
    @FXML private Label enteredText;
    @FXML private Label errorText;
    @FXML private Label overlayText;
    @FXML private Label speedLabel;
    @FXML private Label errorCountLabel;
    @FXML private Label symbolsCountLabel;
    @FXML private Label enteredButton;
    @FXML private AnchorPane preparingPanel;
    @FXML private AnchorPane resultPanel;
    @FXML private AnchorPane newRecordPanel;
    @FXML private Label resultAccuracyLabel;
    @FXML private Label resultTimeLabel;
    @FXML private Label resultSpeedLabel;

    private TypingTextProvider provider;
    private final TypingStatisticsCalculator calculator = new TypingStatisticsCalculator();

    private boolean typingStarted = false;
    private boolean typingInitialized = false; // флаг для сигнализации того, что инициализация ввода уже прошла(или не прошла)
    private boolean flagMistake = false;
    private boolean firstClick = true; // чтобы игнорировать первый ввод Enter, а последующие учитывать как обычные символы

    private int textLength;

    private HandleStrategy strategy;

    private void modeToHandle(Scene scene){
        switch (Utility.getCurrentMode()){
            case ONE_LIFE -> strategy = new HandleOneLifeStrategy();
            case WITH_ERASING -> strategy = new HandleWithErasingStrategy();
            default -> strategy = new HandleDefaultStrategy();
        }
        scene.setOnKeyTyped(event -> strategy.handleKeyPressed(event, this));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        getBackstage().sceneProperty().addListener((_, _, newScene) -> {
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
        textLength = text.length();
        getOverlayText().setText(text);

        Scene scene = Utility.getPrimaryStage().getScene();

        scene.setOnKeyPressed(event -> {
//            System.out.println(event.getCode());
            if ((event.getCode() == KeyCode.ENTER) && (!typingInitialized)){
                typingStarted = true;
                typingInitialized = true;
                getPreparingPanel().setVisible(false);
                calculator.getTimeline().startTimer(getTimerLabel(), Utility.getCurrentTimeSetting());
                modeToHandle(scene);
                getBackstage().requestFocus();
                event.consume();
            }
        });
        getBackstage().requestFocus();
//        calculator.getTimeline().startTimer(getTimerLabel());
//        scene.setOnKeyTyped(this::handleKeyPressed);
//        getBackstage().requestFocus();
    }
    private int currIndex = 0;

    @Setter(AccessLevel.PRIVATE)
    private Stack<ElementStack> stack = new Stack<>();

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

//        getInfoLabel().setText("Наберите текст ниже. Скорость набора появится здесь.");
        getSymbolsCountLabel().setText("Символы: 0");
        getErrorCountLabel().setText("Ошибки: 0");

        if (Utility.getCurrentSpeedSetting() == SpeedSetting.WPM) getSpeedLabel().setText("Скорость: 0 слов/мин");
        else getSpeedLabel().setText("Скорость: 0 символов/мин");

        stack.clear();
        calculator.getCurrStats().setErrorCount(0);

        if (Utility.getCurrentTimeSetting() == TimeSetting.MINSEC) getTimerLabel().setText("0 : 00");
        else getTimerLabel().setText("0");
    }

    @FXML
    private void restartTyping(){
        if (calculator.getTimeline().getTimerStarted()){
            calculator.getTimeline().stopTimer();
        }
        restartScene();
        getOverlayText().setText(provider.generate());
        startTyping(provider);
//        calculator.getTimeline().startTimer(getTimerLabel());
    }

    @FXML
    private void exitScene(){
        Utility.backToMenu();
    }

    @FXML
    void mouseChangeEventEnter(MouseEvent event) {
        Utility.changeCursor(Cursor.HAND);
    }

    @FXML
    void mouseChangeEventExit(MouseEvent event) {
        Utility.changeCursor(Cursor.DEFAULT);
    }

    public void setTextEnteredButton(KeyEvent event, char enteredKey){
        if (!" ".equals(event.getCharacter()) && !"\b".equals(event.getCharacter())) enteredButton.setText("" + enteredKey);
        else{
            if (" ".equals(event.getCharacter())) enteredButton.setText("SPACE");
            else enteredButton.setText("BACKSPACE");
        }
    }
}
