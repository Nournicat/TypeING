package ru.ssau.operatingsystem.project.typeingapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import ru.ssau.operatingsystem.project.typeingapp.textProviders.TypingTextProvider;
import ru.ssau.operatingsystem.project.typeingapp.utility.calculation.TypingStatisticsCalculator;
import ru.ssau.operatingsystem.project.typeingapp.utility.Utility;

import java.net.URL;
import java.util.ResourceBundle;

public class DefaultTypingController extends AbstractTypingController {

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

    @FXML
    private AnchorPane preparingPanel;

    @Override
    protected VBox getBackstage() {
        return backstage;
    }

    @Override
    protected Label getInfoLabel() {
        return infoLabel;
    }

    @Override
    protected Label getTimerLabel() {
        return timerLabel;
    }

    @Override
    protected Label getEnteredText() {
        return enteredText;
    }

    @Override
    protected Label getOverlayText() {
        return overlayText;
    }

    @Override
    protected VBox getResultPanel() {
        return resultPanel;
    }

    @Override
    protected AnchorPane getPreparingPanel(){
        return preparingPanel;
    }
}
