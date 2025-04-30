package ru.ssau.operatingsystem.project.typeingapp.utility;

import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import ru.ssau.operatingsystem.project.typeingapp.MainApp;
import ru.ssau.operatingsystem.project.typeingapp.controller.*;
import ru.ssau.operatingsystem.project.typeingapp.dao.service.UserInfoService;
import ru.ssau.operatingsystem.project.typeingapp.dao.service.UserTimeService;
import ru.ssau.operatingsystem.project.typeingapp.enums.Language;
import ru.ssau.operatingsystem.project.typeingapp.enums.LanguageType;
import ru.ssau.operatingsystem.project.typeingapp.enums.Mode;
import ru.ssau.operatingsystem.project.typeingapp.textProviders.TypingTextProvider;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Utility {
    @Setter @Getter private static Stage primaryStage;
    @Setter @Getter private static Mode currentMode = Mode.DEFAULT;
    @Setter @Getter private static boolean firstStart = true;
    @Getter private static Language currentLanguage = Language.RUSSIAN;
    @Getter private static LanguageType currentLanguageType = LanguageType.LETTERS;

    public static void changeLanguage(Language language, LanguageType type){
        currentLanguage = language;
        currentLanguageType = type;
    }

    public static void setCurrentLanguage(Language currentLanguage) {
        Utility.currentLanguage = currentLanguage;
    }

    public static void setCurrentLanguageType(LanguageType currentLanguageType) {
        Utility.currentLanguageType = currentLanguageType;
    }

    private Utility() {
    }

    public static void changeScene(Scene scene){
        if (primaryStage == null)
            throw new NullPointerException("Primary Stage is not initialized");

        primaryStage.setScene(scene);
    }

    public static UserInfoService getUserInfoService(){return UserInfoService.getInstance();}
    public static UserTimeService getUserTimeService(){return UserTimeService.getInstance();}

    public static void changeCursor(Cursor cursor){
        primaryStage.getScene().setCursor(cursor);
    }
    public static void startTyping(TypingTextProvider stringProvider) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("3_1.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 979, 634);

            Utility.changeScene(scene);
            ((Controllers) fxmlLoader.getController()).startTyping(stringProvider);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void backToMenu(){
        Scene scene;
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("2.fxml"));
        try {
            scene = new Scene(fxmlLoader.load(), 979, 634);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Utility.changeScene(scene);
    }

    public static String timeToString(LocalTime time){
        return time.format(DateTimeFormatter.ofPattern("mm:ss"));
    }
}
