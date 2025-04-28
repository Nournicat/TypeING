package ru.ssau.operatingsystem.project.typeingapp.dao.service;

import ru.ssau.operatingsystem.project.typeingapp.dao.model.UserModeTime;
import ru.ssau.operatingsystem.project.typeingapp.dao.UserTimeDAO;
import ru.ssau.operatingsystem.project.typeingapp.enums.Language;
import ru.ssau.operatingsystem.project.typeingapp.enums.LanguageType;
import ru.ssau.operatingsystem.project.typeingapp.enums.Mode;

import java.time.LocalTime;

public class UserTimeService {
    private static UserTimeService instance;
    private final UserTimeDAO userDAO = UserTimeDAO.getInstance();

    private UserTimeService() {
    }

    public static UserTimeService getInstance(){
        if(instance == null)
            instance = new UserTimeService();

        return instance;
    }

    public LocalTime getTime(Mode mode, Language language, LanguageType type){
        UserModeTime user = userDAO.getUserByMode(mode);
        switch (language){
            case RUSSIAN:
                return user.getRussianTime().get(type);
            case ENGLISH:
                return user.getEnglishTime().get(type);
            case QTE:
                return user.getQTETime();
            case CPP:
            case PYTHON:
            case JAVA:
                return user.getProgrammingTime().get(language);
        }

        return null;
    }

    public void updateBestTime(Mode mode, Language language, LanguageType type, LocalTime newTime){
        UserModeTime user = userDAO.getUserByMode(mode);
        LocalTime oldTime;

        switch (language){
            case RUSSIAN:
                oldTime = user.getRussianTime().get(type);
                if(timeIsBest(newTime, oldTime))
                    user.getRussianTime().replace(type, newTime);
                break;
            case ENGLISH:
                oldTime = user.getEnglishTime().get(type);
                if(timeIsBest(newTime, oldTime))
                    user.getEnglishTime().replace(type, newTime);
                break;
            case QTE:
                oldTime = user.getQTETime();
                if(timeIsBest(newTime, oldTime))
                    user.setQTETime(newTime);
                break;
            case CPP:
            case PYTHON:
            case JAVA:
                oldTime = user.getProgrammingTime().get(language);
                if(timeIsBest(newTime, oldTime))
                    user.getProgrammingTime().replace(language, newTime);
                break;
        }

        userDAO.saveUserTime(user);
    }

    private boolean timeIsBest(LocalTime t1, LocalTime t2){
        return (t1.isBefore(t2) || t2.equals(LocalTime.of(0, 0, 0)));
    }
}
