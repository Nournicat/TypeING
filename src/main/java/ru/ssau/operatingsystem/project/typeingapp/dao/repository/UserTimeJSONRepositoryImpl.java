package ru.ssau.operatingsystem.project.typeingapp.dao.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ru.ssau.operatingsystem.project.typeingapp.MainApp;
import ru.ssau.operatingsystem.project.typeingapp.dao.model.UserModeTime;
import ru.ssau.operatingsystem.project.typeingapp.enums.Mode;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class UserTimeJSONRepositoryImpl implements UserTimeRepository{
    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules().enable(SerializationFeature.INDENT_OUTPUT);
    private final File file = Path.of(System.getenv("APPDATA") + "\\TypeING\\time.json").toFile();

    public UserTimeJSONRepositoryImpl() throws URISyntaxException {
        if(!file.exists()) {
            try {
                new File(file.getParent()).mkdirs();
                file.createNewFile();
                List<UserModeTime> list = new ArrayList<>();
                list.add(new UserModeTime(Mode.DEFAULT));
                list.add(new UserModeTime(Mode.ONE_LIFE));
                list.add(new UserModeTime(Mode.WITH_ERASING));
                objectMapper.writeValue(file, list);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public UserModeTime findByMode(Mode mode) {
        return findAll().stream().filter(e->e.getMode() == mode).findFirst().orElse(new UserModeTime(mode));
    }

    @Override
    public List<UserModeTime> findAll() {
        ArrayList<UserModeTime> list;
        try {
            list = objectMapper.readValue(file, new TypeReference<>(){});
        }
        catch (IOException e){
            return List.of();
        }
        return list;
    }

    @Override
    public void update(UserModeTime arg) {
        List<UserModeTime> list = findAll();
        int needUpdateIndex = -1;
        for (UserModeTime user: list) {
            if(user.getMode() == arg.getMode()) {
                needUpdateIndex = list.indexOf(user);
                break;
            }
        }
        list.set(needUpdateIndex, arg);
        try {
            objectMapper.writeValue(file, list);
        }
        catch (IOException ignored){
            System.err.println("Cannot write UserModeTime in JSON. Path: " + file.getAbsolutePath());
        }
    }

    @Override
    public void delete(UserModeTime arg) {
        List<UserModeTime> list = findAll();
        list.remove(arg);
        try {
            objectMapper.writeValue(file, list);
        }
        catch (IOException ignored){
            System.err.println("Cannot write UserInfo in JSON. Path: " + file.getAbsolutePath());
        }
    }
}
