package com.alexslo.bank.service;

import com.alexslo.bank.dao.UserDao;
import com.alexslo.bank.dao.mem.UserDaoImpl;
import com.alexslo.bank.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

public class UserServiceTest {
    private UserDao userDao;
    private UserService userService;
    private Random random;

    @Before
    public void init() {
        userDao = new UserDaoImpl();
        userService = new UserService(userDao);
        random = new Random();
    }

    @Test(expected = IllegalArgumentException.class)
    public void addNewUserNullParameterTest() {
        userService.addNewUser(null);
    }

    @Test
    public void addNewUserTest() {
        int userId = Math.abs(random.nextInt());
        User user = new User();
        user.setUerId(userId);
        userService.addNewUser(user);
        Assert.assertEquals(user, userDao.getUser(userId));
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteUserByIdincorrectArgumentTest() {
        userService.deleteUserById(-1);
    }

    @Test
    public void deleteUserByIdTest() {
        int userId = Math.abs(random.nextInt());
        User user = new User();
        user.setUerId(userId);
        userService.addNewUser(user);
        Assert.assertEquals(user, userService.deleteUserById(userId));
    }
}