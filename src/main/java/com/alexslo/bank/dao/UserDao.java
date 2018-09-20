package com.alexslo.bank.dao;

import com.alexslo.bank.model.Exception.NotCorrectPasswordException;
import com.alexslo.bank.model.Exception.UserDoNotExistException;
import com.alexslo.bank.model.User;
import com.alexslo.bank.model.UserRole;

public interface UserDao {

    void addUser(User user);

    User getUserById(int id);

    User getUserByLoginPassword(String login, String password);

    UserRole getUserRoleByLogin(String login);

    void deleteUserById(int id);

    boolean userExist(String login) throws UserDoNotExistException;

    boolean userExist(int userId) throws UserDoNotExistException;

    boolean isPasswordCorrect(String login, String password) throws NotCorrectPasswordException;

}
