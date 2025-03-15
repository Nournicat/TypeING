package ru.ssau.operatingsystem.project.typeingapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import ru.ssau.operatingsystem.project.typeingapp.MainApp;
import ru.ssau.operatingsystem.project.typeingapp.RandomString;
import ru.ssau.operatingsystem.project.typeingapp.RandomTextProvider;
import ru.ssau.operatingsystem.project.typeingapp.utility.Utility;

import java.io.IOException;
import java.security.SecureRandom;

public class ModesMenuController {

    @FXML
    private AnchorPane pane1;

    @FXML
    void pane1Click(MouseEvent event) {
        Scene scene;
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("3.fxml"));
        try {
            scene = new Scene(fxmlLoader.load(), 600, 400);
        } catch (IOException e) {
            System.out.println("Ошибка");
            throw new RuntimeException(e);
        }

//        String easy = "абвгд";
//        RandomString stringGenerator = new RandomString(4, new SecureRandom(), easy);
//        RandomTextProvider textProvider = new RandomTextProvider(10, stringGenerator);
//        System.out.println(textProvider.generate());

        Utility.changeScene(scene);
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
    void mouseClickEventEnglishAlphabet(MouseEvent event) {

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

    }

    @FXML
    void mouseClickEventProgrammingPython(MouseEvent event) {

    }

    @FXML
    void mouseClickEventRussianAlphabet(MouseEvent event) {

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

}
