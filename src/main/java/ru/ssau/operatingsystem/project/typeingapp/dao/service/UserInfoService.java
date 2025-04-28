package ru.ssau.operatingsystem.project.typeingapp.dao.service;

import ru.ssau.operatingsystem.project.typeingapp.dao.model.UserInfo;
import ru.ssau.operatingsystem.project.typeingapp.dao.UserInfoDAO;
import ru.ssau.operatingsystem.project.typeingapp.enums.Mode;

public class UserInfoService {
    private static UserInfoService instance;
    private final UserInfoDAO userDAO = UserInfoDAO.getInstance();

    private UserInfoService() {
    }

    public static UserInfoService getInstance(){
        if(instance == null)
            instance = new UserInfoService();

        return instance;
    }

    public UserInfo getUserByMode(Mode mode){
        return userDAO.getUserByMode(mode);
    }

    public void updateUser(UserInfo info){
        userDAO.saveUserInfo(info);
    }

    public void deleteUser(UserInfo info){
        userDAO.deleteUser(info);
    }
}
