package ru.ssau.operatingsystem.project.typeingapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import ru.ssau.operatingsystem.project.typeingapp.dao.model.UserInfo;
import ru.ssau.operatingsystem.project.typeingapp.dao.service.UserInfoService;
import ru.ssau.operatingsystem.project.typeingapp.enums.*;
import ru.ssau.operatingsystem.project.typeingapp.utility.Settings;
import ru.ssau.operatingsystem.project.typeingapp.utility.Utility;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    @FXML private Label averageSpeed;
    @FXML private Label averageTime;
    @FXML private Label bestAccuracy;
    @FXML private Label bestSpeed;
    @FXML private Label bestTime;
    @FXML private Label generalAccuracy;
    @FXML private Label generalCountSymbols;
    @FXML private Label nicknameUser;
    @FXML private Label settingButtonLabel;
    @FXML private VBox vboxModes;
    @FXML private VBox vboxSettings;


    @FXML private Label languageLabel1;
    @FXML private Label languageLabel2;
    @FXML private Label languageLabel3;
    @FXML private Label languageLabel4;
    @FXML private Label languageTypeLabel1;
    @FXML private Label languageTypeLabel2;
    @FXML private Label languageTypeLabel3;
    @FXML private Label languageTypeLabel4;
    @FXML private Label labelSWPM1;
    @FXML private Label labelSWPM2;


    @FXML
    void exitToMenu() {
        Utility.backToMenu();
    }

    @FXML
    void mouseChangeEventEnter() {
        Utility.changeCursor(Cursor.HAND);
    }

    @FXML
    void mouseChangeEventExit() {
        Utility.changeCursor(Cursor.DEFAULT);
    }

    private static final HashMap<Language, Language> languageMap;
    private static final HashMap<LanguageType, LanguageType> languageTypeMap;
    static{
        languageMap = new HashMap<>();
        languageMap.put(Language.RUSSIAN, Language.ENGLISH);
        languageMap.put(Language.ENGLISH, Language.JAVA);
        languageMap.put(Language.JAVA, Language.PYTHON);
        languageMap.put(Language.PYTHON, Language.CPP);
        languageMap.put(Language.CPP, Language.RUSSIAN);

        languageTypeMap = new HashMap<>();
        languageTypeMap.put(LanguageType.LETTERS, LanguageType.ALPHABET);
        languageTypeMap.put(LanguageType.ALPHABET, LanguageType.SHORT_WORDS);
        languageTypeMap.put(LanguageType.SHORT_WORDS, LanguageType.LETTERS);
        languageTypeMap.put(LanguageType.NO_TYPE, LanguageType.LETTERS);
    }
    @FXML
    void mouseChangeLanguageType() {
        Language currentLanguage = Utility.getCurrentLanguage();
        LanguageType currentLanguageType = Utility.getCurrentLanguageType();

        if(List.of(Language.CPP, Language.JAVA, Language.PYTHON).contains(currentLanguage))
            Utility.setCurrentLanguage(languageMap.get(currentLanguage));
        else{
            if(currentLanguageType == LanguageType.SHORT_WORDS)
                Utility.setCurrentLanguage(languageMap.get(currentLanguage));

            Utility.setCurrentLanguageType(languageTypeMap.get(currentLanguageType));
        }

        if(List.of(Language.CPP, Language.JAVA, Language.PYTHON).contains(Utility.getCurrentLanguage()))
            Utility.setCurrentLanguageType(LanguageType.NO_TYPE);
        else if(Utility.getCurrentLanguageType() == LanguageType.NO_TYPE)
            Utility.setCurrentLanguageType(LanguageType.LETTERS);

        updateLanguageLabels();
        updateLabels();
    }

    private static final HashMap<Language, String> languageStringMap;
    private static final HashMap<LanguageType, String> languageTypeStringMap;
    static{
        languageStringMap = new HashMap<>();
        languageTypeStringMap = new HashMap<>();

        languageStringMap.put(Language.RUSSIAN, "Русский язык");
        languageStringMap.put(Language.ENGLISH, "Английский язык");
        languageStringMap.put(Language.CPP, "C++");
        languageStringMap.put(Language.JAVA, "Java");
        languageStringMap.put(Language.PYTHON, "Python");

        languageTypeStringMap.put(LanguageType.NO_TYPE, "");
        languageTypeStringMap.put(LanguageType.LETTERS, "Буквы");
        languageTypeStringMap.put(LanguageType.SHORT_WORDS, "Короткие слова");
        languageTypeStringMap.put(LanguageType.ALPHABET, "Алфавит");
    }

    private void updateLanguageLabels(){
        Language currentLanguage = Utility.getCurrentLanguage();
        LanguageType currentLanguageType = Utility.getCurrentLanguageType();

        languageLabel1.setText(languageStringMap.get(currentLanguage));
        languageLabel2.setText(languageStringMap.get(currentLanguage));
        languageLabel3.setText(languageStringMap.get(currentLanguage));
        languageLabel4.setText(languageStringMap.get(currentLanguage));

        if(!List.of(Language.CPP, Language.JAVA, Language.PYTHON).contains(currentLanguage)) {
            languageTypeLabel1.setText(languageTypeStringMap.get(currentLanguageType));
            languageTypeLabel2.setText(languageTypeStringMap.get(currentLanguageType));
            languageTypeLabel3.setText(languageTypeStringMap.get(currentLanguageType));
            languageTypeLabel4.setText(languageTypeStringMap.get(currentLanguageType));
        }
        else{
            languageTypeLabel1.setText("");
            languageTypeLabel2.setText("");
            languageTypeLabel3.setText("");
            languageTypeLabel4.setText("");
        }
    }

    @FXML
    void openSettingsList(){
        vboxSettings.setVisible(!vboxSettings.isVisible());
    }

    @FXML
    void openModeList(){
        vboxModes.setVisible(!vboxModes.isVisible());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        vboxModes.setVisible(false);
        vboxSettings.setVisible(false);
        nicknameUser.setText(Settings.getUsername());

        updateLabels();
    }

    @FXML void mouseChangeModeToDefault() {
        if(Utility.getCurrentMode() != Mode.DEFAULT) {
            Utility.setCurrentMode(Mode.DEFAULT);
            settingButtonLabel.setText(Mode.DEFAULT.getName());
            updateLabels();
        }
    }

    @FXML void mouseChangeModeToOneLife() {
        if(Utility.getCurrentMode() != Mode.ONE_LIFE) {
            Utility.setCurrentMode(Mode.ONE_LIFE);
            settingButtonLabel.setText(Mode.ONE_LIFE.getName());
            updateLabels();
        }
    }

    @FXML void mouseChangeModeToWithErase() {
        if(Utility.getCurrentMode() != Mode.WITH_ERASING) {
            Utility.setCurrentMode(Mode.WITH_ERASING);
            settingButtonLabel.setText(Mode.WITH_ERASING.getName());
            updateLabels();
        }
    }

    @FXML void changeSpeedToCPM() {
        if(Utility.getCurrentSpeedSetting() != SpeedSetting.WPM) {
            Utility.setCurrentSpeedSetting(SpeedSetting.WPM);
            updateLabels();
        }
    }

    @FXML void changeSpeedToWPM() {
        if(Utility.getCurrentSpeedSetting() != SpeedSetting.SPM) {
            Utility.setCurrentSpeedSetting(SpeedSetting.SPM);
            updateLabels();
        }
    }

    @FXML void minSecButtonPressed() {
        Utility.setCurrentTimeSetting(TimeSetting.MINSEC);
    }

    @FXML void secButtonPressed() {
        Utility.setCurrentTimeSetting(TimeSetting.SEC);
    }

    private static final HashMap<SpeedSetting, String> speedSetting;
    static{
        speedSetting = new HashMap<>();

        speedSetting.put(SpeedSetting.WPM, "слов/мин");
        speedSetting.put(SpeedSetting.SPM, "симв/мин");
    }

    private String performStringFloatLabel(double arg){
        return Double.compare(arg, Double.valueOf(arg).intValue()) == 0 ? String.valueOf(Double.valueOf(arg).intValue()) : String.format("%.1f", arg);
    }

    private void updateLabels(){
        UserInfoService service = Utility.getUserInfoService();
        UserInfo user = service.getUserByModeLanguageAndType(Utility.getCurrentMode(), Utility.getCurrentLanguage(), Utility.getCurrentLanguageType());

        labelSWPM1.setText(speedSetting.get(Utility.getCurrentSpeedSetting()));
        labelSWPM2.setText(speedSetting.get(Utility.getCurrentSpeedSetting()));

        generalAccuracy.setText(performStringFloatLabel(service.getOverallAccuracy()) + "%");
        bestAccuracy.setText(performStringFloatLabel(user.getBestAccuracy()) + "%");

        generalCountSymbols.setText(String.valueOf(service.getOverallCountSymbols()));

        averageSpeed.setText(String.valueOf((int)user.getAverageSpeed()));
        bestSpeed.setText(String.valueOf((int)user.getBestSpeed()));

        averageTime.setText(user.getAverageTime().format(DateTimeFormatter.ofPattern("m:ss")));
        bestTime.setText(user.getBestTime().format(DateTimeFormatter.ofPattern("m:ss")));
    }
}
