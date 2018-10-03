package com.alexslo.bank.dao.mem;

import com.alexslo.bank.dao.UserDao;
import com.alexslo.bank.exception.UserAlreadyExistException;
import com.alexslo.bank.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserDaoImpl implements UserDao {

    private Map<Integer, User> usersById;

    public UserDaoImpl() {
        usersById = new HashMap<>();
    }

    @Override
    public void addUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException();
        }
        if (isUserExist(user.getUserId())) {
            throw new UserAlreadyExistException();
        }
        usersById.put(user.getUserId(), user);
    }

    @Override
    public User getUser(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Incorrect id!");
        }
        if (!isUserExist(id)) {
            return null;
        }
        return usersById.get(id);
    }

    @Override
    public User deleteUser(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Incorrect id!");
        }
        return usersById.remove(id);
    }

    @Override
    public boolean isUserExist(int id) {
        return usersById.containsKey(id);
    }
}
