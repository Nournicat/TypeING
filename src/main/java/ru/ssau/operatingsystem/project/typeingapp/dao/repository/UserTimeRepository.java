package ru.ssau.operatingsystem.project.typeingapp.dao.repository;

import ru.ssau.operatingsystem.project.typeingapp.dao.model.UserModeTime;
import ru.ssau.operatingsystem.project.typeingapp.enums.Mode;

import java.util.List;

public interface UserTimeRepository {
    UserModeTime findByMode(Mode mode);
    List<UserModeTime> findAll();
    void update(UserModeTime arg);
    void delete(UserModeTime arg);
}
