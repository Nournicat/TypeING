package ru.ssau.operatingsystem.project.typeingapp.dao.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ru.ssau.operatingsystem.project.typeingapp.MainApp;
import ru.ssau.operatingsystem.project.typeingapp.dao.model.UserInfo;
import ru.ssau.operatingsystem.project.typeingapp.enums.Language;
import ru.ssau.operatingsystem.project.typeingapp.enums.LanguageType;
import ru.ssau.operatingsystem.project.typeingapp.enums.Mode;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class UserInfoJSONRepositoryImpl implements UserInfoRepository {
    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules().enable(SerializationFeature.INDENT_OUTPUT);
    private final File file = Path.of(System.getenv("APPDATA") + "\\TypeING\\stats.json").toFile();

    public UserInfoJSONRepositoryImpl() throws URISyntaxException {
        if(!file.exists()) {
            try {
                new File(file.getParent()).mkdirs();
                file.createNewFile();
                List<UserInfo> list = new ArrayList<>();
                for(Mode mode: List.of(Mode.DEFAULT, Mode.ONE_LIFE, Mode.WITH_ERASING)){
                    for(Language language: List.of(Language.RUSSIAN, Language.ENGLISH)){
                        for(LanguageType languageType: List.of(LanguageType.LETTERS, LanguageType.SHORT_WORDS, LanguageType.ALPHABET))
                            list.add(new UserInfo(mode, language, languageType));
                    }

                    for(Language language: List.of(Language.JAVA, Language.PYTHON, Language.CPP))
                        list.add(new UserInfo(mode, language, LanguageType.NO_TYPE));
                }

                objectMapper.writeValue(file, list);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public UserInfo findByModeLanguageAndType(Mode mode, Language language, LanguageType languageType) {
        return findAll().stream().filter(e->(e.getMode() == mode && e.getLanguage() == language && e.getLanguageType() == languageType)).findFirst().orElse(new UserInfo(mode, language, languageType));
    }

    @Override
    public List<UserInfo> findAll() {
        ArrayList<UserInfo> list;
        try {
            list = objectMapper.readValue(file, new TypeReference<>(){});
        }
        catch (IOException e){
            return List.of();
        }
        return list;
    }

    @Override
    public void update(UserInfo arg) {
        List<UserInfo> list = findAll();
        int needUpdateIndex = -1;
        for (UserInfo user: list) {
            if(user.getMode() == arg.getMode() && user.getLanguage() == arg.getLanguage() && user.getLanguageType() == arg.getLanguageType()) {
                needUpdateIndex = list.indexOf(user);
                break;
            }
        }
        list.set(needUpdateIndex, arg);
        try {
            objectMapper.writeValue(file, list);
        }
        catch (IOException ignored){
            System.err.println("Cannot write UserInfo in JSON. Path: " + file.getAbsolutePath());
        }
    }

    @Override
    public void delete(UserInfo arg) {
        List<UserInfo> list = findAll();
        list.remove(arg);
        try {
            objectMapper.writeValue(file, list);
        }
        catch (IOException ignored){
            System.err.println("Cannot write UserInfo in JSON. Path: " + file.getAbsolutePath());
        }
    }
}
