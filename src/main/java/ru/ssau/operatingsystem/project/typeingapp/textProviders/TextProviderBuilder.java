package ru.ssau.operatingsystem.project.typeingapp.textProviders;

import org.yaml.snakeyaml.Yaml;
import ru.ssau.operatingsystem.project.typeingapp.MainApp;
import ru.ssau.operatingsystem.project.typeingapp.enums.Language;
import ru.ssau.operatingsystem.project.typeingapp.enums.LanguageType;

import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.Map;

public class TextProviderBuilder {
    private static final Yaml yaml = new Yaml();
    private TextProviderBuilder(){}

    public static TypingTextProvider of(Language language, LanguageType type){
        try (InputStream input = MainApp.class.getResourceAsStream("dictionary/dictionary.yaml")) {
            Map<String, Object> dictionary = yaml.load(input);
            switch(language){
                case CPP:
                case PYTHON:
                case JAVA: {
                    return new RandomStringTextProvider(10,
                            ((Map<?, ?>)dictionary.get("programming")).get(language.name().toLowerCase()).toString().split("\n")
                    );
                }
            }

            switch(type){
                case LETTERS -> {
                    return new RandomTextProvider(10, new RandomString(4, new SecureRandom(),
                            (String)((Map<?, ?>)dictionary.get(language.name().toLowerCase())).get(type.name().toLowerCase())));
                }
                case SHORT_WORDS -> {
                    return new RandomStringTextProvider(10, ((String)((Map<?, ?>)dictionary.get(language.name().toLowerCase())).get(type.name().toLowerCase())).split(" "));
                }
                case ALPHABET -> {
                    return new RandomStringTextProvider(1, new String[] {(String)((Map<?, ?>)dictionary.get(language.name().toLowerCase())).get(type.name().toLowerCase())});
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
