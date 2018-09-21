package com.alexslo.bank.dao.mem;

import com.alexslo.bank.dao.UserDao;
import com.alexslo.bank.model.Exception.NotCorrectPasswordException;
import com.alexslo.bank.model.Exception.UserDoNotExistException;
import com.alexslo.bank.model.User;
import com.alexslo.bank.model.UserRole;

import java.util.HashMap;
import java.util.Map;

public class UserDaoImpl implements UserDao {

    private Map<Integer, User> userMap;
    private Map<String, User> userLoginMap;

    public UserDaoImpl() {
        userMap = new HashMap<>();
        userLoginMap = new HashMap<>();
    }

    @Override
    public void addUser(User user) {
        userMap.put(user.getUserId(), user);
        userLoginMap.put(user.getUserLogin(), user);
    }

    @Override
    public User getUserById(int id) {
        return userMap.get(id);
    }

    @Override
    public User getUserByLoginPassword(String login, String password) {
        User result = userLoginMap.get(login);
        if (result.getPassword().equals(password)) {
            return result;
        } else {
            return null;
        }
    }

    @Override
    public UserRole getUserRoleByLogin(String login) {
        return userLoginMap.get(login).getRole();
    }

    @Override
    public void deleteUserById(int id) {
        userLoginMap.remove(userMap.get(id).getUserLogin());
        userMap.remove(id);
    }


    @Override
    public boolean isPasswordCorrect(String login, String password) throws NotCorrectPasswordException {
        User fromLogin = userLoginMap.get(login);
        if (!fromLogin.getPassword().equals(password)) {
            throw new NotCorrectPasswordException();
        } else {
            return true;
        }
    }

    @Override
    public boolean userExist(String login) throws UserDoNotExistException {
        User fromLogin = userLoginMap.get(login);
        if (fromLogin != null) {
            return true;
        } else {
            throw new UserDoNotExistException();
        }
    }

    @Override
    public boolean userExist(int id) throws UserDoNotExistException {
        User fromId = userMap.get(id);
        if (fromId != null) {
            return true;
        } else {
            throw new UserDoNotExistException();
        }
    }
}
