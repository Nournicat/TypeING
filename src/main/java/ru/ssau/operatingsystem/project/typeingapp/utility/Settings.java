package ru.ssau.operatingsystem.project.typeingapp.utility;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class Settings {
    private static Properties properties;

    private Settings(){
    }

    public static Properties getProperties() {
        return properties;
    }

    public static void loadProperties(){
        String rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();
        String appConfigPath = rootPath + "typing_app.properties";

        try {
            Properties appProperties = new Properties();
            appProperties.load(new FileInputStream(appConfigPath));
            properties = appProperties;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
