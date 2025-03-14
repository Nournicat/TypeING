package ru.ssau.operatingsystem.project.typeingapp.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import ru.ssau.operatingsystem.project.typeingapp.MainApp;
import ru.ssau.operatingsystem.project.typeingapp.RandomString;
import ru.ssau.operatingsystem.project.typeingapp.RandomTextProvider;
import ru.ssau.operatingsystem.project.typeingapp.utility.TypingStatisticsCalculator;
import ru.ssau.operatingsystem.project.typeingapp.utility.TypingStats;
import ru.ssau.operatingsystem.project.typeingapp.utility.Utility;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class TypingController implements Initializable {

    @FXML
    private VBox backstage;
    @FXML
    private Label infoLabel;
    private final String infoText = "Наберите текст ниже. Скорость набора появится здесь.";

    @FXML
    private Label enteredText;

    @FXML
    private Label overlayText;


    @FXML
    private VBox resultPanel;


    TypingStats statistic = new TypingStats(0, 0, 0);
    TypingStatisticsCalculator calculator = new TypingStatisticsCalculator();

    @Override
    public void initialize(URL location, ResourceBundle resources){
        backstage.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyTyped(this::handleKeyPressed);
                backstage.requestFocus();
                startTyping();
            }
        });

    }

    @FXML
    private void startTyping(){
        System.out.println("Запущено");
        restartScene();

        String text = getText();
        overlayText.setText(text);

        Scene scene = Utility.getPrimaryStage().getScene();
        scene.setOnKeyTyped(this::handleKeyPressed);
        backstage.requestFocus();
    }

    @FXML
    private void exitScene(){
        Scene scene;
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("2.fxml"));
        try {
            scene = new Scene(fxmlLoader.load(), 600, 400);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Utility.changeScene(scene);
    }
    private void handleKeyPressed(KeyEvent event) {

        // Пример обработки нажатия клавиши
        if (event.getCharacter().isEmpty()) return;

        char enteredKey = event.getCharacter().charAt(0);

        // Пока только для логгирования
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
        calculator.calculateStats(enteredText.getText(), statistic);
        statistic.updateStats(infoLabel);

        if (overlayText.getText().isEmpty()){
            resultPanel.setVisible(true);
            return;
        }
    }

    private String getText(){
        Random random = new Random();
        int lengthWord = random.nextInt(4, 6);
        int countWords = random.nextInt(5, 10);
        RandomString gen = new RandomString(lengthWord, random);
        RandomTextProvider randomText = new RandomTextProvider(countWords, gen);

        return randomText.generate();
    }

    private void restartScene(){
        resultPanel.setVisible(false);
        enteredText.setText("");

        infoLabel.setText(infoText);
    }

}
