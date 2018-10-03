package com.alexslo.bank.dao;

import com.alexslo.bank.model.User;

public interface UserDao {

    void addUser(User user);

    User getUser(int id);

    User deleteUser(int id);

    boolean isUserExist(int userId);
}
