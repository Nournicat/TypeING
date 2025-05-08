package ru.ssau.operatingsystem.project.typeingapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
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
import ru.ssau.operatingsystem.project.typeingapp.textProviders.TypingTextProvider;
import ru.ssau.operatingsystem.project.typeingapp.utility.Utility;

import javax.lang.model.AnnotatedConstruct;
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
    private Label modeLabel;
    @FXML
    private VBox modesMenu;
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
        Utility.changeLanguage(Language.ENGLISH, LanguageType.ALPHABET);

        Utility.startTyping(
                TextProviderBuilder.of(
                        Language.ENGLISH,
                        LanguageType.ALPHABET));
    }

    @FXML
    void mouseClickEventEnglishLetters(MouseEvent event) {
        Utility.changeLanguage(Language.ENGLISH, LanguageType.LETTERS);

        Utility.startTyping(
                TextProviderBuilder.of(
                        Language.ENGLISH,
                        LanguageType.LETTERS));
    }

    @FXML
    void mouseClickEventEnglishWords(MouseEvent event) {
        Utility.changeLanguage(Language.ENGLISH, LanguageType.SHORT_WORDS);

        Utility.startTyping(
                TextProviderBuilder.of(
                        Language.ENGLISH,
                        LanguageType.SHORT_WORDS));
    }

    @FXML
    void mouseClickEventProgrammingCplus(MouseEvent event) {
        Utility.changeLanguage(Language.CPP, LanguageType.SHORT_WORDS);

        Utility.startTyping(
                TextProviderBuilder.of(
                        Language.CPP,
                        LanguageType.NO_TYPE));
    }

    @FXML
    void mouseClickEventProgrammingJava(MouseEvent event) {
        String[] words = new String[]{
            "int", "public static void", "String", "new ArrayList<>()",
                    "Double", "Arrays.of(1, 2, 3).stream().min().get();",
                    "System.out.println(\"Hello World\");", "Random random = new Random();"
        };
        Utility.changeLanguage(Language.JAVA, LanguageType.NO_TYPE);

        Utility.startTyping(TextProviderBuilder.of(
                Language.JAVA,
                LanguageType.NO_TYPE));
    }

    @FXML
    void mouseClickEventProgrammingPython(MouseEvent event) {
        Utility.changeLanguage(Language.PYTHON, LanguageType.NO_TYPE);

        Utility.startTyping(TextProviderBuilder.of(
                Language.PYTHON,
                LanguageType.NO_TYPE));
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
        Utility.changeLanguage(Language.RUSSIAN, LanguageType.ALPHABET);

        Utility.startTyping(TextProviderBuilder.of(
                Language.RUSSIAN,
                LanguageType.ALPHABET));
    }

    @FXML
    void mouseClickEventRussianLetters(MouseEvent event) {
        Utility.changeLanguage(Language.RUSSIAN, LanguageType.LETTERS);

        Utility.startTyping(TextProviderBuilder.of(
                Language.RUSSIAN,
                LanguageType.LETTERS));
    }

    @FXML
    void mouseClickEventRussianWords(MouseEvent event) {
        Utility.changeLanguage(Language.RUSSIAN, LanguageType.SHORT_WORDS);

        Utility.startTyping(TextProviderBuilder.of(
                Language.RUSSIAN,
                LanguageType.SHORT_WORDS));
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
    void mousePressChangeDefaultMode(MouseEvent event) {
        Utility.setCurrentMode(Mode.DEFAULT);
        updateLabelTime(Utility.getCurrentMode());
        modeLabel.setText("Default");
    }

    @FXML
    void mousePressChangeEraseMode(MouseEvent event) {
        Utility.setCurrentMode(Mode.WITH_ERASING);
        updateLabelTime(Utility.getCurrentMode());
        modeLabel.setText("WithErasing");
    }

    @FXML
    void mousePressChangeOneHPMode(MouseEvent event) {
        Utility.setCurrentMode(Mode.ONE_LIFE);
        updateLabelTime(Utility.getCurrentMode());
        modeLabel.setText("OneLife");
    }

    @FXML
    void mousePressChangeQTEMode(MouseEvent event) {
        Utility.setCurrentMode(Mode.QTE);
    }

    @FXML
    private AnchorPane settingsMenu;

    private boolean visibleSettingsMenu = false;
    @FXML
    void settingsButtonPressed(MouseEvent event){
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
    void wpmButtonPressed(MouseEvent event){
        Utility.setCurrentSpeedSetting(SpeedSetting.WPM);
    }

    @FXML
    void spmButtonPressed(MouseEvent event){
        Utility.setCurrentSpeedSetting(SpeedSetting.SPM);
    }

    @FXML
    void minSecButtonPressed(MouseEvent event){
        Utility.setCurrentTimeSetting(TimeSetting.MINSEC);
    }

    @FXML
    void secButtonPressed(MouseEvent event){
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
