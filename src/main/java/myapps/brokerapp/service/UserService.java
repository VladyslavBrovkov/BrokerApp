package myapps.brokerapp.service;

import myapps.brokerapp.model.User;

import java.util.Set;

public interface UserService {

    User findUserByLogin(String login);
    void registerUser(User user);
    Set<User> getAll();

}
