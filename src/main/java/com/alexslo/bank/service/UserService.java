package com.alexslo.bank.service;

import com.alexslo.bank.dao.mem.UserDaoImpl;
import com.alexslo.bank.model.Exception.NotCorrectPasswordException;
import com.alexslo.bank.model.Exception.UserDoNotExistException;
import com.alexslo.bank.model.User;


public class UserService {
    private UserDaoImpl userDao;

    public UserService() {
        userDao = new UserDaoImpl();
    }

    public void addNewUser(User user) {
        try {
            if (!userDao.userExist(user.getUserId())) {
                userDao.addUser(user);
            }
        } catch (UserDoNotExistException e) {
            e.getMessage();
        }
    }

    public void deleteUserById(int id) {
        try {
            if (userDao.userExist(id)) {
                userDao.deleteUserById(id);
            }
        } catch (UserDoNotExistException e) {
            e.getMessage();
        }
    }

    public User getUserByLoginPassword(String login, String password) {
        try {
            if (userDao.userExist(login)) {
                if (userDao.isPasswordCorrect(login, password)) {
                    return userDao.getUserByLoginPassword(login, password);
                }
            }
        } catch (UserDoNotExistException | NotCorrectPasswordException e) {
            e.getMessage();
        }
        return null;
    }


}
