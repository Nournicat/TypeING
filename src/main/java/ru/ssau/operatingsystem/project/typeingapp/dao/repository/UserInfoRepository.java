package ru.ssau.operatingsystem.project.typeingapp.dao.repository;

import ru.ssau.operatingsystem.project.typeingapp.dao.model.UserInfo;
import ru.ssau.operatingsystem.project.typeingapp.enums.Language;
import ru.ssau.operatingsystem.project.typeingapp.enums.LanguageType;
import ru.ssau.operatingsystem.project.typeingapp.enums.Mode;

import java.util.List;

public interface UserInfoRepository {
    UserInfo findByModeLanguageAndType(Mode mode, Language language, LanguageType languageType);
    List<UserInfo> findAll();
    void update(UserInfo arg);
    void delete(UserInfo arg);
}
