package com.alexslo.bank.mem;


import com.alexslo.bank.model.User;
import com.alexslo.bank.model.UserRole;

import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements com.alexslo.bank.dao.UserDao {

    private final List<User> store = new ArrayList<>();

    public UserDaoImpl() {

    }

    @Override
    public boolean addUser(User user) {
        for (User u : store) {
            if (u.getUserLogin().equals(user.getUserLogin()) &&
                    u.getPassword().equals(user.getPassword())) {
                return false;
            }
        }
        return store.add(user);
    }

    @Override
    public User getUserById(int id) {
        User result = new User();
        result.setuserId(-1);

        for (User user : store) {
            if (user.getuserId() == id) {
                result = user;
            }
        }
        return result;
    }

    @Override
    public User getUserByLoginPassword(String login, String password) {
        User result = new User();
        result.setuserId(-1);

        for (User user : store) {
            if (user.getUserLogin().equals(login) && user.getPassword().equals(password)) {
                result = user;
            }
        }
        return result;
    }

    @Override
    public UserRole getUserRoleByLoginPassword(String login, String password) {
        UserRole result = UserRole.GUEST;
        for (User user : store) {
            if (user.getUserLogin().equals(login) && user.getPassword().equals(password)) {
                result = user.getRole();
            }
        }
        return result;
    }

    @Override
    public void deleteUserById(int id) {

    }

    @Override
    public boolean userExists(String login, String password) {
        boolean result = false;
        for (User user : store) {
            if (user.getUserLogin().equals(login) && user.getPassword().equals(password)) {
                result = true;
                break;
            }
        }
        return result;
    }
}
