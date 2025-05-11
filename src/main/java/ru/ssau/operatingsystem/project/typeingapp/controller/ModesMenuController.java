package ru.ssau.operatingsystem.project.typeingapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ru.ssau.operatingsystem.project.typeingapp.*;
import ru.ssau.operatingsystem.project.typeingapp.dao.service.UserTimeService;
import ru.ssau.operatingsystem.project.typeingapp.enums.*;
import ru.ssau.operatingsystem.project.typeingapp.textProviders.RandomString;
import ru.ssau.operatingsystem.project.typeingapp.textProviders.RandomStringTextProvider;
import ru.ssau.operatingsystem.project.typeingapp.textProviders.RandomTextProvider;
import ru.ssau.operatingsystem.project.typeingapp.textProviders.TextProviderBuilder;
import ru.ssau.operatingsystem.project.typeingapp.utility.Utility;

import java.io.IOException;
import java.net.URL;
import java.security.SecureRandom;
import java.util.ResourceBundle;

public class ModesMenuController implements Initializable {
    @FXML private Label englishWordsTime;
    @FXML private Label englishLetterTime;
    @FXML private Label englishAlphabetTime;
    @FXML private Label russianAlphabetTime;
    @FXML private Label russianLetterTime;
    @FXML private Label russianWordsTime;
    @FXML private HBox russianMenu;

    private boolean visibleRussianMenu = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateLabelTime(Utility.getCurrentMode());
        if (!Utility.isFirstStart()){
            modeLabel.setText(Utility.getCurrentMode().getName());
        }
        else Utility.setFirstStart(false);
        restartScene();
    }
    private void updateLabelTime(Mode mode){
        UserTimeService service = Utility.getUserTimeService();
        englishLetterTime.setText(Utility.timeToString(service.getTime(mode, Language.ENGLISH, LanguageType.LETTERS)));
        englishWordsTime.setText(Utility.timeToString(service.getTime(mode, Language.ENGLISH, LanguageType.SHORT_WORDS)));
        englishAlphabetTime.setText(Utility.timeToString(service.getTime(mode, Language.ENGLISH, LanguageType.ALPHABET)));

        russianLetterTime.setText(Utility.timeToString(service.getTime(mode, Language.RUSSIAN, LanguageType.LETTERS)));
        russianWordsTime.setText(Utility.timeToString(service.getTime(mode, Language.RUSSIAN, LanguageType.SHORT_WORDS)));
        russianAlphabetTime.setText(Utility.timeToString(service.getTime(mode, Language.RUSSIAN, LanguageType.ALPHABET)));
    }

    @FXML
    void mouseChangeEventEnter() {
        Utility.changeCursor(Cursor.HAND);
    }

    @FXML
    void mouseChangeEventExit() {
        Utility.changeCursor(Cursor.DEFAULT);
    }

    @FXML
    private Label modeLabel;
    @FXML
    private VBox modesMenu;
    private boolean visibleModesMenu = false;
    @FXML
    void modesChangePressed(){
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
    void englishButtonPressed(){
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
    void mouseClickEventEnglishAlphabet() {
        Utility.changeLanguage(Language.ENGLISH, LanguageType.ALPHABET);

        Utility.startTyping(
                TextProviderBuilder.of(
                        Language.ENGLISH,
                        LanguageType.ALPHABET));
    }

    @FXML
    void mouseClickEventEnglishLetters() {
        Utility.changeLanguage(Language.ENGLISH, LanguageType.LETTERS);

        Utility.startTyping(
                TextProviderBuilder.of(
                        Language.ENGLISH,
                        LanguageType.LETTERS));
    }

    @FXML
    void mouseClickEventEnglishWords() {
        Utility.changeLanguage(Language.ENGLISH, LanguageType.SHORT_WORDS);

        Utility.startTyping(
                TextProviderBuilder.of(
                        Language.ENGLISH,
                        LanguageType.SHORT_WORDS));
    }

    @FXML
    void mouseClickEventProgrammingCplus() {
        Utility.changeLanguage(Language.CPP, LanguageType.NO_TYPE);

        Utility.startTyping(
                TextProviderBuilder.of(
                        Language.CPP,
                        LanguageType.NO_TYPE));
    }

    @FXML
    void mouseClickEventProgrammingJava() {
        Utility.changeLanguage(Language.JAVA, LanguageType.NO_TYPE);

        Utility.startTyping(TextProviderBuilder.of(
                Language.JAVA,
                LanguageType.NO_TYPE));
    }

    @FXML
    void mouseClickEventProgrammingPython() {
        Utility.changeLanguage(Language.PYTHON, LanguageType.NO_TYPE);

        Utility.startTyping(TextProviderBuilder.of(
                Language.PYTHON,
                LanguageType.NO_TYPE));
    }

    @FXML
    void russianButtonPressed(){
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
    void mouseClickEventRussianAlphabet() {
        Utility.changeLanguage(Language.RUSSIAN, LanguageType.ALPHABET);

        Utility.startTyping(TextProviderBuilder.of(
                Language.RUSSIAN,
                LanguageType.ALPHABET));
    }

    @FXML
    void mouseClickEventRussianLetters() {
        Utility.changeLanguage(Language.RUSSIAN, LanguageType.LETTERS);

        Utility.startTyping(TextProviderBuilder.of(
                Language.RUSSIAN,
                LanguageType.LETTERS));
    }

    @FXML
    void mouseClickEventRussianWords() {
        Utility.changeLanguage(Language.RUSSIAN, LanguageType.SHORT_WORDS);

        Utility.startTyping(TextProviderBuilder.of(
                Language.RUSSIAN,
                LanguageType.SHORT_WORDS));
    }

    @FXML
    void mouseClickQTE() {
        Utility.startQTE();
    }

    @Deprecated(since = "2.2")
    RandomTextProvider letterGenerator(String str){
        RandomString stringGenerator = new RandomString(6, new SecureRandom(), str);

        return new RandomTextProvider(10, stringGenerator);
    }

    @Deprecated(since = "2.2")
    RandomStringTextProvider wordsGenerator(int count, String[] words){
        return new RandomStringTextProvider(count, words);
    }

    @FXML
    void mousePressChangeDefaultMode() {
        Utility.setCurrentMode(Mode.DEFAULT);
        updateLabelTime(Utility.getCurrentMode());
        modeLabel.setText("Default");
    }

    @FXML
    void mousePressChangeEraseMode() {
        Utility.setCurrentMode(Mode.WITH_ERASING);
        updateLabelTime(Utility.getCurrentMode());
        modeLabel.setText("WithErasing");
    }

    @FXML
    void mousePressChangeOneHPMode() {
        Utility.setCurrentMode(Mode.ONE_LIFE);
        updateLabelTime(Utility.getCurrentMode());
        modeLabel.setText("OneLife");
    }


    @FXML
    private AnchorPane settingsMenu;

    private boolean visibleSettingsMenu = false;
    @FXML
    void settingsButtonPressed(){
        if (!visibleSettingsMenu) {
            settingsMenu.setVisible(true);
            visibleSettingsMenu = true;
        }
        else{
            settingsMenu.setVisible(false);
            visibleSettingsMenu = false;
        }
    }

    @FXML
    void profileButtonPressed(){
        Scene scene;
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("profile.fxml"));
        try {
            scene = new Scene(fxmlLoader.load(), 979, 634);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Utility.changeScene(scene);
    }

    @FXML
    void wpmButtonPressed(){
        Utility.setCurrentSpeedSetting(SpeedSetting.WPM);
    }

    @FXML
    void spmButtonPressed(){
        Utility.setCurrentSpeedSetting(SpeedSetting.SPM);
    }

    @FXML
    void minSecButtonPressed(){
        Utility.setCurrentTimeSetting(TimeSetting.MINSEC);
    }

    @FXML
    void secButtonPressed(){
        Utility.setCurrentTimeSetting(TimeSetting.SEC);
    }

    private void restartScene(){
        visibleRussianMenu = false;
        russianMenu.setVisible(false);

        visibleEnglishMenu = false;
        englishMenu.setVisible(false);


        visibleModesMenu = false;
        modesMenu.setVisible(false);
//        modeLabel.setText("Режим");

        visibleSettingsMenu = false;
        settingsMenu.setVisible(false);
    }

}
