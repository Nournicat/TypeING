package ru.ssau.operatingsystem.project.typeingapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import ru.ssau.operatingsystem.project.typeingapp.MainApp;
import ru.ssau.operatingsystem.project.typeingapp.utility.Utility;

import java.io.IOException;

public class Controller1 {

    @FXML
    private Button btn1;

    @FXML
    private Button btn2;

    @FXML
    private Button btn3;

    @FXML
    void btn1Press(ActionEvent event) {
        Scene scene;
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("2.fxml"));
        try {
            scene = new Scene(fxmlLoader.load(), 600, 400);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Utility.changeScene(scene);
    }

}