package ru.ssau.operatingsystem.project.typeingapp.dao.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ru.ssau.operatingsystem.project.typeingapp.MainApp;
import ru.ssau.operatingsystem.project.typeingapp.dao.model.UserInfo;
import ru.ssau.operatingsystem.project.typeingapp.enums.Mode;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class UserInfoJSONRepositoryImpl implements UserInfoRepository {
    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules().enable(SerializationFeature.INDENT_OUTPUT);
    private final File file = new File(URI.create(MainApp.class.getResource("") + "stats.json"));

    public UserInfoJSONRepositoryImpl() {
        if(!file.exists()) {
            try {
                file.createNewFile();
                List<UserInfo> list = new ArrayList<>();
                list.add(new UserInfo(Mode.DEFAULT));
                list.add(new UserInfo(Mode.ONE_LIFE));
                list.add(new UserInfo(Mode.WITH_ERASING));
                objectMapper.writeValue(file, list);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public UserInfo findByMode(Mode mode) {
        return findAll().stream().filter(e->e.getMode() == mode).findFirst().orElse(new UserInfo(mode));
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
