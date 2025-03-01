package ru.ssau.operatingsystem.project.typeingapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
            throw new RuntimeException(e);
        }

//        String easy = "абвгд";
//        RandomString stringGenerator = new RandomString(4, new SecureRandom(), easy);
//        RandomTextProvider textProvider = new RandomTextProvider(10, stringGenerator);
//        System.out.println(textProvider.generate());

        Utility.changeScene(scene);
        TypingController controller = fxmlLoader.getController();
        controller.initialize();
    }

}
