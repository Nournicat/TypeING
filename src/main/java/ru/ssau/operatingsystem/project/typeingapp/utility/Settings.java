package ru.ssau.operatingsystem.project.typeingapp.utility;


import ru.ssau.operatingsystem.project.typeingapp.MainApp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class Settings {
    private static Properties properties;

    private Settings(){
    }

    public static String getSpeedSetting(){
        return getString("speed-setting");
    }

    public static String getTimeSetting(){
        return getString("time-setting");
    }

    public static String getUsername(){
        return getString("username");
    }

    public static String getString(String key) {
        return properties.getProperty(key);
    }

    public static String getString(String key, String def) {
        return properties.getProperty(key, def);
    }

    public static Object get(String key) {
        return properties.get(key);
    }

    public static void loadProperties() throws URISyntaxException {
        URI rootPath = MainApp.class.getResource("").toURI();
        URI appConfigPath = URI.create(rootPath + "config.properties");
        URI defaultConfig = MainApp.class.getResource("config.properties").toURI();

        try {
            if(!new File(appConfigPath).exists())
                Files.copy(
                        Paths.get(defaultConfig),
                        Paths.get(appConfigPath)
                );

            Properties appProperties = new Properties();
            appProperties.load(new FileInputStream(appConfigPath.getPath()));
            properties = appProperties;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
