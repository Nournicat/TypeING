package ru.ssau.operatingsystem.project.typeingapp.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import ru.ssau.operatingsystem.project.typeingapp.enums.QTEdifficult;
import ru.ssau.operatingsystem.project.typeingapp.enums.SpeedSetting;
import ru.ssau.operatingsystem.project.typeingapp.enums.TimeSetting;
import ru.ssau.operatingsystem.project.typeingapp.textProviders.TypingTextProvider;
import ru.ssau.operatingsystem.project.typeingapp.utility.Utility;
import ru.ssau.operatingsystem.project.typeingapp.utility.calculation.TypingStatisticsCalculator;
import ru.ssau.operatingsystem.project.typeingapp.utility.calculation.qteStatisticsCalculator;

import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;

@Getter
@Setter
public class QTETypingController implements Initializable{
    private int numberOfTests = 10;
    private int counterIterations = numberOfTests;


    @FXML private VBox backstage;
    @FXML private Label timerLabel;
    @FXML private Label errorCountLabel;
    @FXML private Label correntCountLabel;
    @FXML private Label currentButtonLabel;
    @FXML private AnchorPane preparingPanel;
    @FXML private AnchorPane resultPanel;
    @FXML private Label resultAccuracyLabel;
    @FXML private Label resultTimeLabel;
    @FXML private Label resultSpeedLabel;


    private KeyCode currentKey;


    private int currentSeconds = 0;
    private int windowInterval;
    private QTEdifficult modeQTE = QTEdifficult.NAN;
    @FXML private AnchorPane chooseModePanel;

    @FXML private AnchorPane easyModeButton;
    @FXML
    private void selectEasyMode(){
        modeQTE = QTEdifficult.EASY;
        windowInterval = 4;
        chooseModePanel.setVisible(false);
    }

    @FXML private AnchorPane mediumModeButton;
    @FXML
    private void selectMediumMode(){
        modeQTE = QTEdifficult.MEDIUM;
        windowInterval = 2;
        chooseModePanel.setVisible(false);
    }

    @FXML private AnchorPane hardModeButton;
    @FXML
    private void selectHardMode(){
        modeQTE = QTEdifficult.HARD;
        windowInterval = 1;
        chooseModePanel.setVisible(false);
    }

    private boolean isBackgroundTimelineStarted = false;
    private Timeline backgroundTimeline;


    private qteStatisticsCalculator calculator = new qteStatisticsCalculator();

    private boolean typingStarted = false;
    private boolean typingInitialized = false;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        backstage.sceneProperty().addListener((_, _, newScene) -> {
            if (newScene != null) {
                backstage.requestFocus();
                changeButton();
            }
        });

    }

    public void startTyping(){
        restartScene();

        Scene scene = Utility.getPrimaryStage().getScene();

        scene.setOnKeyPressed(event -> {
//            System.out.println(event.getCode());
            if ((event.getCode() == KeyCode.ENTER) && (!typingInitialized) && (modeQTE != QTEdifficult.NAN)){
                typingStarted = true;
                typingInitialized = true;
                getPreparingPanel().setVisible(false);
                calculator.getTimeline().startTimer(getTimerLabel(), Utility.getCurrentTimeSetting());
                backgroundTimeline = new Timeline(new KeyFrame(Duration.seconds(1), eventChange -> {
                    resultStatistic();
                    if (currentSeconds!=windowInterval) {
                        currentSeconds+=1;
                    }
                    else{
                        changeButton();
                        currentSeconds = 0;
                        int currentError = calculator.getCurrStats().getErrorCount();
                        calculator.getCurrStats().setErrorCount(currentError+1);
                    }
                    calculator.updateStats(correntCountLabel, errorCountLabel);
                }));
                scene.setOnKeyPressed(pressEvent -> handleKeyPressed(pressEvent));
                backgroundTimeline.setCycleCount(Timeline.INDEFINITE);
                backgroundTimeline.play();
                isBackgroundTimelineStarted = true;

                getBackstage().requestFocus();
                event.consume();
            }
        });


        getBackstage().requestFocus();
    }


    private void handleKeyPressed(KeyEvent event){
        if (!isTypingInitialized()) return;
        if (!isTypingStarted()) return;
        if (event.getCharacter().isEmpty()) return;
        if (counterIterations == 0) return;

        KeyCode code = event.getCode();
        System.out.println(KeyCodeMapper.getName(code));
        System.out.println(KeyCodeMapper.getName(currentKey));
        if (code == currentKey) {
            calculator.getCurrStats().setCorrectCount(calculator.getCurrStats().getCorrectCount() + 1);
            changeButton();
        }
        else {
            changeButton();
            calculator.getCurrStats().setErrorCount(calculator.getCurrStats().getErrorCount() + 1);
        }
        currentSeconds = 0;
        calculator.updateStats(correntCountLabel, errorCountLabel);
        resultStatistic();
    }

    @FXML
    private void exitScene(){
        Utility.backToMenu();
    }

    @FXML
    private void restartTyping(){
        if (calculator.getTimeline().getTimerStarted()){
            calculator.getTimeline().stopTimer();
        }
        if (isBackgroundTimelineStarted){
            backgroundTimeline.stop();
        }
        restartScene();
        startTyping();
//        calculator.getTimeline().startTimer(getTimerLabel());
    }

    private void restartScene(){
        typingStarted = false;
        typingInitialized = false; // Сброс, чтобы снова можно было начать ввод

        getPreparingPanel().setVisible(true);
        getChooseModePanel().setVisible(true);
        modeQTE = QTEdifficult.NAN;
        getResultPanel().setVisible(false);

//        getInfoLabel().setText("Наберите текст ниже. Скорость набора появится здесь.");
        getCorrentCountLabel().setText("Верно: 0");
        getErrorCountLabel().setText("Ошибки: 0");

        calculator.getCurrStats().setErrorCount(0);
        calculator.getCurrStats().setCorrectCount(0);
        counterIterations = numberOfTests;

        if (Utility.getCurrentTimeSetting() == TimeSetting.MINSEC) getTimerLabel().setText("0 : 00");
        else getTimerLabel().setText("0");

        changeButton();
    }

    private void changeButton(){
        currentKey = KeyCodeMapper.getRandomKeyCode();
        String strKey = KeyCodeMapper.getName(currentKey);
        currentButtonLabel.setText(strKey);
        if (typingInitialized) counterIterations--;
//        System.out.println(counterIterations);
    }

    private void resultStatistic(){
        if (counterIterations == 0){
            getCalculator().getTimeline().stopTimer();
            backgroundTimeline.stop();
            getResultPanel().setVisible(true);
            getCalculator().setFinalTime(getTimerLabel().getText());
            getCalculator().setDataResultPanel(
                    numberOfTests,
                    getResultAccuracyLabel(),
                    getResultTimeLabel(),
                    getResultSpeedLabel()
            );

            Utility.getUserTimeService().updateBestTime(
                    Utility.getCurrentMode(),
                    Utility.getCurrentLanguage(),
                    Utility.getCurrentLanguageType(),
                    LocalTime.ofSecondOfDay(getCalculator().getTimeline().getElapsedSeconds())
            );
        }
    }

    @FXML
    void mouseChangeEventEnter(MouseEvent event) {
        Utility.changeCursor(Cursor.HAND);
    }

    @FXML
    void mouseChangeEventExit(MouseEvent event) {
        Utility.changeCursor(Cursor.DEFAULT);
    }

}


