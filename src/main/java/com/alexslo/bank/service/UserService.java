package com.alexslo.bank.service;

import com.alexslo.bank.dao.UserDao;
import com.alexslo.bank.model.User;

public class UserService {

    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void addNewUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException();
        }
        if (!userDao.isUserExist(user.getUserId())) {
            userDao.addUser(user);
        }
    }

    public User deleteUserById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Incorrect id!");
        }
        return userDao.deleteUser(id);
    }
}
