package ru.ssau.operatingsystem.project.typeingapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import ru.ssau.operatingsystem.project.typeingapp.MainApp;
import ru.ssau.operatingsystem.project.typeingapp.utility.Utility;

import java.io.IOException;

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
            throw new RuntimeException(e);
        }

        Utility.changeScene(scene);
    }

}
