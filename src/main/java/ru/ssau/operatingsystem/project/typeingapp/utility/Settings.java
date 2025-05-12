package ru.ssau.operatingsystem.project.typeingapp.utility;


import ru.ssau.operatingsystem.project.typeingapp.MainApp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
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
        InputStream defaultConfig = MainApp.class.getResourceAsStream("config.properties");
        Path jarPath = Path.of(Path.of(new File(MainApp.class.getProtectionDomain().getCodeSource().getLocation()
                .toURI()).getPath()).getParent() + "\\config.properties");

        try {
            if(!jarPath.toFile().exists())
                Files.copy(
                        defaultConfig,
                        Paths.get(jarPath.toUri())
                );

            Properties appProperties = new Properties();
            appProperties.load(new FileInputStream(String.valueOf(jarPath)));
            properties = appProperties;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
