package ru.ssau.operatingsystem.project.typeingapp.dao.service;

import ru.ssau.operatingsystem.project.typeingapp.dao.model.UserInfo;
import ru.ssau.operatingsystem.project.typeingapp.dao.UserInfoDAO;
import ru.ssau.operatingsystem.project.typeingapp.enums.Language;
import ru.ssau.operatingsystem.project.typeingapp.enums.LanguageType;
import ru.ssau.operatingsystem.project.typeingapp.enums.Mode;

import java.time.LocalTime;

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

    public void addResult(Mode mode, Language language, LanguageType languageType, int count_symbols, float accuracy, LocalTime time, float speed){
        UserInfo user = getUserByModeLanguageAndType(mode, language, languageType);
        user.setCountSymbols(user.getCountSymbols() + count_symbols);
        if(!user.getBestTime().isBefore(time) || user.getBestTime().equals(LocalTime.of(0, 0, 0))) {
            user.setBestTime(time);
            user.setBestAccuracy(Math.max(user.getBestAccuracy(), accuracy));
            user.setBestSpeed(Math.max(user.getBestSpeed(), speed));
        }

        user.setAverageSpeed(user.getAverageSpeed() * ((float) user.getCountStarts() / (user.getCountStarts() + 1)) + speed/(user.getCountStarts() + 1));
        user.setOverallAccuracy(user.getOverallAccuracy() * ((float) user.getCountStarts() / (user.getCountStarts() + 1)) + accuracy/(user.getCountStarts() + 1));

        LocalTime averageTime = user.getAverageTime();
        int h = averageTime.getHour();
        int m = averageTime.getMinute();
        int s = averageTime.getSecond();
        int ss = (int)((h*3600+m*60+s) * ((float) user.getCountStarts() / (user.getCountStarts() + 1)) +
                (float) (time.getSecond() + time.getMinute() * 60 + time.getHour() * 3600) /(user.getCountStarts() + 1));

        user.setAverageTime(LocalTime.of(ss/3600, (ss/60)%60, ss%60));
        user.setCountStarts(user.getCountStarts() + 1);

        updateUser(user);
    }

    public int getOverallCountSymbols(){
        return userDAO.getAllUsers().stream().mapToInt(UserInfo::getCountSymbols).sum();
    }

    public double getOverallAccuracy(){
        int n = 0;
        for(UserInfo user: userDAO.getAllUsers()){
            if(Double.compare(user.getOverallAccuracy(), 0.0) != 0)
                n++;
        }

        return userDAO.getAllUsers().stream().mapToDouble(UserInfo::getOverallAccuracy).sum() / n;
    }

    public UserInfo getUserByModeLanguageAndType(Mode mode, Language language, LanguageType languageType){
        return userDAO.getUserByModeLanguageAndType(mode, language, languageType);
    }

    public void updateUser(UserInfo info){
        userDAO.saveUserInfo(info);
    }

    public void deleteUser(UserInfo info){
        userDAO.deleteUser(info);
    }
}
