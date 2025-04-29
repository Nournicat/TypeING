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
import ru.ssau.operatingsystem.project.typeingapp.textProviders.TypingTextProvider;
import ru.ssau.operatingsystem.project.typeingapp.utility.ElementStack;
import ru.ssau.operatingsystem.project.typeingapp.utility.Utility;
import ru.ssau.operatingsystem.project.typeingapp.utility.calculation.TypingStatisticsCalculator;

import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.Stack;

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
    public Label errorText;

    @FXML
    public Label overlayText;

    @FXML
    public Label speedLabel;
    @FXML
    public Label errorCountLabel;
    @FXML
    public Label symbolsCountLabel;

    @FXML
    public Label enteredButton;

    @FXML
    public AnchorPane preparingPanel;

    private final TypingStatisticsCalculator calculator = new TypingStatisticsCalculator();
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
        getOverlayText().setText(text);

        Scene scene = Utility.getPrimaryStage().getScene();

        scene.setOnKeyPressed(event -> {
//            System.out.println(event.getCode());
            if ((event.getCode() == KeyCode.ENTER) && (!typingInitialized)){
                typingStarted = true;
                typingInitialized = true;
                getPreparingPanel().setVisible(false);
                calculator.getTimeline().startTimer(getTimerLabel());
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
    Stack<ElementStack> stack = new Stack<>();
    private void handleKeyPressedDefault(KeyEvent event) {
        if (!typingInitialized) return;
        if (getOverlayText().getText().isEmpty()) return;
        if (event.getCharacter().isEmpty()) return;

        if (("\r".equals(event.getCharacter()) || "\n".equals(event.getCharacter()))) return;

        char enteredKey = event.getCharacter().charAt(0);
        setTextEnteredButton(event, enteredKey);
        char currentKey = getOverlayText().getText().charAt(0);
        if (enteredKey == currentKey){
            getEnteredText().setText(getEnteredText().getText() + enteredKey);
            getOverlayText().setText(getOverlayText().getText().substring(1));
            currIndex+=1;
            System.out.println(currIndex);
        }
        else {
            if (stack.isEmpty() || stack.peek().getErrorIndex()!=currIndex) {
                stack.push(new ElementStack(currIndex, currentKey));
                int errorCount = calculator.getCurrStats().getErrorCount();
                calculator.getCurrStats().setErrorCount(++errorCount);
                System.out.println(currIndex);
            }

        }
        calculator.calculateStats(getEnteredText().getText());
        calculator.updateStats(getSymbolsCountLabel(), getErrorCountLabel(), getSpeedLabel());

        if (getOverlayText().getText().isEmpty()){
            calculator.getTimeline().stopTimer();
//            getResultPanel().setVisible(true);

            Utility.getUserTimeService().updateBestTime(
                    Utility.getCurrentMode(),
                    Utility.getCurrentLanguage(),
                    Utility.getCurrentLanguageType(),
                    LocalTime.ofSecondOfDay(calculator.getTimeline().getElapsedSeconds())
            );
        }
    }

    private void handleKeyPressedWithErasing(KeyEvent event) {
        if (!typingInitialized) return;
        if (!typingStarted) return;
        if (("\r".equals(event.getCharacter()) || "\n".equals(event.getCharacter()))) return;
        if (getOverlayText().getText().isEmpty()) return;
        if (event.getCharacter().isEmpty()) return;

        char enteredKey = event.getCharacter().charAt(0);
        setTextEnteredButton(event, enteredKey);
        char currentKey = getOverlayText().getText().charAt(0);
//        System.out.println("enteredKey = " + enteredKey);
//        System.out.println("currentKey = " + currentKey);
        if (enteredKey == currentKey){
//            System.out.println("Правильный символ");
            getEnteredText().setText(getEnteredText().getText() + enteredKey);
            getOverlayText().setText(getOverlayText().getText().substring(1));
            currIndex+=1;
//            System.out.println(currIndex);
        }
        else {
            if ("\b".equals(event.getCharacter())) {
                if (!getEnteredText().getText().isEmpty()) {
                    currIndex -= 1;
//                    System.out.println(stack.isEmpty());
                    if (!stack.isEmpty()) System.out.println(stack.peek().getErrorIndex() + " " + stack.peek().getCorrectSymbol());

                    if (stack.isEmpty() || stack.peek().getErrorIndex() != currIndex) {
//                        System.out.println("Стирание без стека");
                        char deletedChar = getEnteredText().getText().charAt(getEnteredText().getText().length() - 1);
                        getEnteredText().setText(getEnteredText().getText().substring(0, getEnteredText().getText().length() - 1));
                        getOverlayText().setText(deletedChar + getOverlayText().getText());
                    }
                    else {
//                        System.out.println("Стирание со стеком");
                        char correctSymbol = stack.pop().getCorrectSymbol();
                        getEnteredText().setText(getEnteredText().getText().substring(0, getEnteredText().getText().length() - 1));
                        getOverlayText().setText(correctSymbol + getOverlayText().getText());

                        int errorCount = calculator.getCurrStats().getErrorCount();
                        calculator.getCurrStats().setErrorCount(--errorCount);
                    }
//                    System.out.println(currIndex);
                }
            }
            else {
//                System.out.println("Неправильный символ");
                getEnteredText().setText(getEnteredText().getText() + enteredKey);
                getOverlayText().setText(getOverlayText().getText().substring(1));
                stack.push(new ElementStack(currIndex, currentKey));
                currIndex+=1;
//                System.out.println(currIndex);

                int errorCount = calculator.getCurrStats().getErrorCount();
                calculator.getCurrStats().setErrorCount(++errorCount);
            }
        }
        calculator.calculateStats(getEnteredText().getText());
        calculator.updateStats(getSymbolsCountLabel(), getErrorCountLabel(), getSpeedLabel());

        if (getOverlayText().getText().isEmpty()){
            calculator.getTimeline().stopTimer();
//            getResultPanel().setVisible(true);

            Utility.getUserTimeService().updateBestTime(
                    Utility.getCurrentMode(),
                    Utility.getCurrentLanguage(),
                    Utility.getCurrentLanguageType(),
                    LocalTime.ofSecondOfDay(calculator.getTimeline().getElapsedSeconds())
            );
        }
    }

    private void handleKeyPressedOneLife(KeyEvent event) {
        if (("\r".equals(event.getCharacter()) || "\n".equals(event.getCharacter())) && firstClick){
            firstClick = false;
            return;
        }

        if (!typingInitialized) return;
        if (flagMistake) return;
        if (getOverlayText().getText().isEmpty()) return;
        if (event.getCharacter().isEmpty()) return;

        char enteredKey = event.getCharacter().charAt(0);
        setTextEnteredButton(event, enteredKey);
        char currentKey = getOverlayText().getText().charAt(0);
        if (enteredKey == currentKey){
            getEnteredText().setText(getEnteredText().getText() + enteredKey);
            getOverlayText().setText(getOverlayText().getText().substring(1));
        }
        else{
            calculator.getTimeline().stopTimer();
//            getResultPanel().setVisible(true);
            int errorCount = calculator.getCurrStats().getErrorCount();
            calculator.getCurrStats().setErrorCount(++errorCount);
            flagMistake = true;
        }
        calculator.calculateStats(getEnteredText().getText());
        calculator.updateStats(getSymbolsCountLabel(), getErrorCountLabel(), getSpeedLabel());

        if (getOverlayText().getText().isEmpty()){
            calculator.getTimeline().stopTimer();
//            getResultPanel().setVisible(true);

            Utility.getUserTimeService().updateBestTime(
                    Utility.getCurrentMode(),
                    Utility.getCurrentLanguage(),
                    Utility.getCurrentLanguageType(),
                    LocalTime.ofSecondOfDay(calculator.getTimeline().getElapsedSeconds())
            );
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
//        getResultPanel().setVisible(false);
        getEnteredText().setText("");

//        getInfoLabel().setText("Наберите текст ниже. Скорость набора появится здесь.");
        getSymbolsCountLabel().setText("Символы: 0");
        getErrorCountLabel().setText("Ошибки: 0");
        getSpeedLabel().setText("Скорость: 0 слов/мин");
        stack.clear();
        calculator.getCurrStats().setErrorCount(0);
        getTimerLabel().setText("0 : 00");
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

    private VBox getBackstage(){ return backstage; }
    private Label getSymbolsCountLabel(){ return symbolsCountLabel; }
    private Label getErrorCountLabel(){ return errorCountLabel; }
    private Label getSpeedLabel(){ return speedLabel; }
    private Label getTimerLabel(){ return timerLabel; }
    private Label getEnteredText(){ return enteredText; }
    private Label getErrorText(){ return errorText; }
    private Label getOverlayText(){ return overlayText; }
    private AnchorPane getPreparingPanel(){ return preparingPanel; }


    @FXML
    void mouseChangeEventEnter(MouseEvent event) {
        Utility.changeCursor(Cursor.HAND);
    }

    @FXML
    void mouseChangeEventExit(MouseEvent event) {
        Utility.changeCursor(Cursor.DEFAULT);
    }

    private void setTextEnteredButton(KeyEvent event, char enteredKey){
        if (!" ".equals(event.getCharacter()) && !"\b".equals(event.getCharacter())) enteredButton.setText("" + enteredKey);
        else{
            if (" ".equals(event.getCharacter())) enteredButton.setText("SPACE");
            else enteredButton.setText("BACKSPACE");
        }
    }
}
