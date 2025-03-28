package ru.ssau.operatingsystem.project.typeingapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import ru.ssau.operatingsystem.project.typeingapp.textProviders.TypingTextProvider;
import ru.ssau.operatingsystem.project.typeingapp.utility.calculation.TypingStatisticsCalculator;
import ru.ssau.operatingsystem.project.typeingapp.utility.Utility;

import java.net.URL;
import java.util.ResourceBundle;

public class TypingController implements Initializable, Controllers {

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
    private TypingTextProvider provider;

    private TypingStatisticsCalculator calculator = new TypingStatisticsCalculator();

    @Override
    public void initialize(URL location, ResourceBundle resources){
        backstage.sceneProperty().addListener((_, _, newScene) -> {
            if (newScene != null) {
//                newScene.setOnKeyTyped(this::handleKeyPressed);
                calculator.getTimeline().startTimer(timerLabel);
                backstage.requestFocus();
//                bindLabelWidth(enteredText, 10);

            }
        });
    }

    public void startTyping(TypingTextProvider stringProvider){
        provider = stringProvider;
        System.out.println("Запущено");
        restartScene();

        String text = getText(stringProvider);
        overlayText.setText(text);

        Scene scene = Utility.getPrimaryStage().getScene();

        scene.setOnKeyTyped(this::handleKeyPressed);
//        scene.setOnKeyPressed(this::handleKeyPressed);  на будущее для получения кодов всех клавиш через getCode(),
//        но без игнорирования специальных комбинаций клавиш по типу Alt+Shift и т.д
        backstage.requestFocus();
    }

    private void handleKeyPressed(KeyEvent event) {
        if (overlayText.getText().isEmpty()) return;

        // Пример обработки нажатия клавиши
        if (event.getCharacter().isEmpty()) return;

        char enteredKey = event.getCharacter().charAt(0);

        if (!overlayText.getText().isEmpty())
            System.out.println(event.getCharacter());
//        infoLabel.setText("Нажата клавиша: " + enteredKey);

        char currentKey = overlayText.getText().charAt(0);
        if (enteredKey == currentKey){
            enteredText.setText(enteredText.getText() + enteredKey);
            overlayText.setText(overlayText.getText().substring(1));
        }
        if ("\b".equals(event.getCharacter())){
            if (!enteredText.getText().isEmpty()) {
                char deletedChar = enteredText.getText().charAt(enteredText.getText().length() - 1);
                enteredText.setText(enteredText.getText().substring(0, enteredText.getText().length() - 1));
                overlayText.setText(deletedChar + overlayText.getText());
            }
        }
        calculator.calculateStats(enteredText.getText());
        calculator.updateStats(infoLabel);

        if (overlayText.getText().isEmpty()){
            calculator.getTimeline().stopTimer();
            resultPanel.setVisible(true);
        }
    }

    private String getText(TypingTextProvider stringProvider){
//        Random random = new Random();
//        int lengthWord = random.nextInt(4, 6);
//        int countWords = random.nextInt(5, 10);
//        RandomString gen = new RandomString(lengthWord, random);
//        RandomTextProvider randomText = new RandomTextProvider(countWords, gen);

        return stringProvider.generate();
    }

    private void restartScene(){
        resultPanel.setVisible(false);
        enteredText.setText("");

        String infoText = "Наберите текст ниже. Скорость набора появится здесь.";
        String timerText = "0 : 00";
        infoLabel.setText(infoText);
        timerLabel.setText(timerText);
    }

    @FXML
    private void restartTyping(){
        restartScene();
        overlayText.setText(provider.generate());
        calculator.getTimeline().startTimer(timerLabel);
    }

    @FXML
    private void exitScene(){
        Utility.backToMenu();
    }
}
