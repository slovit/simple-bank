package com.alexslo.bank.dao;

import com.alexslo.bank.model.User;
import com.alexslo.bank.model.UserRole;

public interface UserDao {

    boolean addUser(User user);

    User getUserById(int id);

    User getUserByLoginPassword(String login, String password);

    UserRole getUserRoleByLoginPassword(String login, String password);

    void deleteUserById(int id);

    boolean userExists(String login, String password);

}
