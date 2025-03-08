package ru.ssau.operatingsystem.project.typeingapp.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import ru.ssau.operatingsystem.project.typeingapp.RandomString;
import ru.ssau.operatingsystem.project.typeingapp.RandomTextProvider;
import ru.ssau.operatingsystem.project.typeingapp.utility.TypingStatisticsCalculator;
import ru.ssau.operatingsystem.project.typeingapp.utility.TypingStats;
import ru.ssau.operatingsystem.project.typeingapp.utility.Utility;

import java.util.Random;

public class TypingController {
    @FXML
    private VBox backstage;
    @FXML
    private Label infoLabel;


    @FXML
    private Label enteredText;
    @FXML
    private Label overlayText;

    TypingStats statistic = new TypingStats(0, 0, 0);
    TypingStatisticsCalculator calculator = new TypingStatisticsCalculator();

    public void initialize(){
            System.out.println("Запущено");
            String text = getText();
            overlayText.setText(text);
            Scene scene = Utility.getPrimaryStage().getScene();
            scene.setOnKeyTyped(this::handleKeyPressed);
            backstage.requestFocus();
    }
    private void handleKeyPressed(KeyEvent event) {
        // Пример обработки нажатия клавиши
        if (event.getCharacter().isEmpty()) return;

        char enteredKey = event.getCharacter().charAt(0);

        // Пока только для логгирования
//        infoLabel.setText("Нажата клавиша: " + enteredKey);

        if (overlayText.getText().isEmpty())
            return;

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
        calculator.calculateStats(enteredText.getText(), statistic);
        statistic.updateStats(infoLabel);
    }

    private String getText(){
        Random random = new Random();
        int lengthWord = random.nextInt(4, 6);
        int countWords = random.nextInt(5, 10);
        RandomString gen = new RandomString(lengthWord, random);
        RandomTextProvider randomText = new RandomTextProvider(countWords, gen);

        return randomText.generate();
    }

}
