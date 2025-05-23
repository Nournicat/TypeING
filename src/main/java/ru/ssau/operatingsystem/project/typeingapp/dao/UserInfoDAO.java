package ru.ssau.operatingsystem.project.typeingapp.dao;

import ru.ssau.operatingsystem.project.typeingapp.dao.model.UserInfo;
import ru.ssau.operatingsystem.project.typeingapp.dao.repository.UserInfoJSONRepositoryImpl;
import ru.ssau.operatingsystem.project.typeingapp.dao.repository.UserInfoRepository;
import ru.ssau.operatingsystem.project.typeingapp.enums.Language;
import ru.ssau.operatingsystem.project.typeingapp.enums.LanguageType;
import ru.ssau.operatingsystem.project.typeingapp.enums.Mode;

import java.net.URISyntaxException;
import java.util.List;

public class UserInfoDAO {
    private static UserInfoDAO instance;
    private final UserInfoRepository userInfoRepository = new UserInfoJSONRepositoryImpl();

    private UserInfoDAO() throws URISyntaxException {
    }

    public static UserInfoDAO getInstance(){
        if(instance == null) {
            try {
                instance = new UserInfoDAO();
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }

        return instance;
    }

    public UserInfo getUserByModeLanguageAndType(Mode mode, Language language, LanguageType languageType){
        return userInfoRepository.findByModeLanguageAndType(mode, language, languageType);
    }

    public List<UserInfo> getAllUsers(){
        return userInfoRepository.findAll();
    }

    public void saveUserInfo(UserInfo user){
        userInfoRepository.update(user);
    }

    public void deleteUser(UserInfo user){
        userInfoRepository.delete(user);
    }
}
