package ru.ssau.operatingsystem.project.typeingapp.dao;

import ru.ssau.operatingsystem.project.typeingapp.dao.model.UserModeTime;
import ru.ssau.operatingsystem.project.typeingapp.dao.repository.UserTimeJSONRepositoryImpl;
import ru.ssau.operatingsystem.project.typeingapp.dao.repository.UserTimeRepository;
import ru.ssau.operatingsystem.project.typeingapp.enums.Mode;

import java.net.URISyntaxException;
import java.util.List;

public class UserTimeDAO {
    private static UserTimeDAO instance;
    private final UserTimeRepository repository = new UserTimeJSONRepositoryImpl();

    private UserTimeDAO() throws URISyntaxException {
    }

    public static UserTimeDAO getInstance(){
        if(instance == null) {
            try {
                instance = new UserTimeDAO();
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }

        return instance;
    }

    public UserModeTime getUserByMode(Mode mode){
        return repository.findByMode(mode);
    }

    public List<UserModeTime> getAllUsers(){
        return repository.findAll();
    }

    public void saveUserTime(UserModeTime user){
        repository.update(user);
    }

    public void deleteUser(UserModeTime user){
        repository.delete(user);
    }
}
