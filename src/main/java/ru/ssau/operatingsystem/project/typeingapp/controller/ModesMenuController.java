package ru.ssau.operatingsystem.project.typeingapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import ru.ssau.operatingsystem.project.typeingapp.*;
import ru.ssau.operatingsystem.project.typeingapp.textProviders.RandomString;
import ru.ssau.operatingsystem.project.typeingapp.textProviders.RandomStringTextProvider;
import ru.ssau.operatingsystem.project.typeingapp.textProviders.RandomTextProvider;
import ru.ssau.operatingsystem.project.typeingapp.utility.Mode;
import ru.ssau.operatingsystem.project.typeingapp.utility.Utility;

import java.io.IOException;
import java.net.URL;
import java.security.SecureRandom;
import java.util.ResourceBundle;

public class ModesMenuController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        restartScene();
    }

    @FXML
    void mouseChangeEventEnter(MouseEvent event) {
        Utility.changeCursor(Cursor.HAND);
    }

    @FXML
    void mouseChangeEventExit(MouseEvent event) {
        Utility.changeCursor(Cursor.DEFAULT);
    }

    @FXML
    void changeSceneToMenu(MouseEvent event) {
        Scene scene;
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("1.fxml"));
        try {
            scene = new Scene(fxmlLoader.load(), 600, 400);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Utility.changeScene(scene);
    }

    @FXML
    void changeSceneToProfile(MouseEvent event) {

    }

    @FXML
    void changeSceneToSettings(MouseEvent event) {

    }

    @FXML
    private Label modeLabel;
    @FXML
    private HBox modesMenu;
    @FXML
    private boolean visibleModesMenu = false;
    @FXML
    void modesChangePressed(MouseEvent event){
        if (!visibleModesMenu) {
            modesMenu.setVisible(true);
            visibleModesMenu = true;
        }
        else{
            modesMenu.setVisible(false);
            visibleModesMenu = false;
        }
    }

    @FXML
    private HBox englishMenu;
    private boolean visibleEnglishMenu = false;
    @FXML
    void englishButtonPressed(MouseEvent event){
        if (!visibleEnglishMenu) {
            englishMenu.setVisible(true);
            visibleEnglishMenu = true;
        }
        else{
            englishMenu.setVisible(false);
            visibleEnglishMenu = false;
        }
    }

    @FXML
    void mouseClickEventEnglishAlphabet(MouseEvent event) {
        RandomStringTextProvider textProvider = wordsGenerator(1, new String[]{
                        "abcdefghijklmnopqrstuvwxyz"
                }
        );

        Utility.startTyping(textProvider);
    }

    @FXML
    void mouseClickEventEnglishLetters(MouseEvent event) {
        RandomTextProvider textProvider = letterGenerator("dfjk");

        Utility.startTyping(textProvider);
    }

    @FXML
    void mouseClickEventEnglishWords(MouseEvent event) {

    }

    @FXML
    void mouseClickEventProgrammingCplus(MouseEvent event) {

    }

    @FXML
    void mouseClickEventProgrammingJava(MouseEvent event) {
        String[] words = new String[]{
            "int", "public static void", "String", "new ArrayList<>()",
                    "Double", "Arrays.of(1, 2, 3).stream().min().get();",
                    "System.out.println(\"Hello World\");", "Random random = new Random();"
        };
        RandomStringTextProvider textProvider = wordsGenerator(words.length, words);

        Utility.startTyping(textProvider);
    }

    @FXML
    void mouseClickEventProgrammingPython(MouseEvent event) {

    }

    @FXML
    private HBox russianMenu;
    private boolean visibleRussianMenu = false;
    @FXML
    void russianButtonPressed(MouseEvent event){
        if (!visibleRussianMenu) {
            russianMenu.setVisible(true);
            visibleRussianMenu = true;
        }
        else{
            russianMenu.setVisible(false);
            visibleRussianMenu = false;
        }
    }
    @FXML
    void mouseClickEventRussianAlphabet(MouseEvent event) {
        RandomStringTextProvider textProvider = wordsGenerator(1, new String[]{
                "абвгдеёжзийклмнопрстуфхцчшщъыьэюя"
        }
        );

        Utility.startTyping(textProvider);
    }

    @FXML
    void mouseClickEventRussianLetters(MouseEvent event) {
        RandomTextProvider textProvider = letterGenerator("ваол");

        Utility.startTyping(textProvider);
    }

    @FXML
    void mouseClickEventRussianWords(MouseEvent event) {

    }

    RandomTextProvider letterGenerator(String str){
        RandomString stringGenerator = new RandomString(6, new SecureRandom(), str);

        return new RandomTextProvider(10, stringGenerator);
    }

    RandomStringTextProvider wordsGenerator(int count, String[] words){
        return new RandomStringTextProvider(count, words);
    }

    @FXML
    void mousePressChangeDefaultMode(MouseEvent event) {
        Utility.setCurrentMode(Mode.DEFAULT);
        modeLabel.setText("Default");
    }

    @FXML
    void mousePressChangeEraseMode(MouseEvent event) {
        Utility.setCurrentMode(Mode.WITH_ERASING);
        modeLabel.setText("WithErasing");
    }

    @FXML
    void mousePressChangeOneHPMode(MouseEvent event) {
        Utility.setCurrentMode(Mode.ONE_LIFE);
        modeLabel.setText("OneLife");
    }

    @FXML
    void mousePressChangeQTEMode(MouseEvent event) {
        Utility.setCurrentMode(Mode.QTE);
    }

    private void restartScene(){
        visibleRussianMenu = false;
        russianMenu.setVisible(false);

        visibleEnglishMenu = false;
        englishMenu.setVisible(false);


        visibleModesMenu = false;
        modesMenu.setVisible(false);
        modeLabel.setText("Режим");
    }

}
