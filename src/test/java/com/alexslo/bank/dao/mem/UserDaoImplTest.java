package com.alexslo.bank.dao.mem;

import com.alexslo.bank.dao.UserDao;
import com.alexslo.bank.exception.UserAlreadyExistException;
import com.alexslo.bank.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

public class UserDaoImplTest {
    private UserDao userDao;
    private Random random;

    @Before
    public void init() {
        userDao = new UserDaoImpl();
        random = new Random();
    }

    @Test(expected = IllegalArgumentException.class)
    public void addUserIdIllegalArgumentExceptionTest() {
        userDao.addUser(null);
    }

    @Test(expected = UserAlreadyExistException.class)
    public void addUserUserAlreadyExistExceptionTest() {
        User user = new User();
        int userId = Math.abs(random.nextInt());
        user.setUerId(userId);
        userDao.addUser(user);
        userDao.addUser(user);
    }

    @Test
    public void addUserTest() {
        User user = new User();
        int userId = Math.abs(random.nextInt());
        user.setUerId(userId);
        userDao.addUser(user);
        Assert.assertEquals(user, userDao.getUser(userId));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getUserTestIllegalArgumentExceptionTest() {
        userDao.getUser(-1);
    }

    @Test
    public void getUserThatDoesntExistTest() {
        Assert.assertNull(userDao.getUser(1));
    }

    @Test
    public void getUserTest() {
        User user = new User();
        User userFromDao;
        int userId = Math.abs(random.nextInt());
        user.setUerId(userId);
        userDao.addUser(user);
        userFromDao = userDao.getUser(userId);
        Assert.assertNotNull(userFromDao);
        Assert.assertEquals(user, userFromDao);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteUserIllegalArgumentExceptionTest() {
        userDao.deleteUser(-1);
    }

    @Test
    public void deleteUserTest() {
        User user = new User();
        int userId = Math.abs(random.nextInt());
        user.setUerId(userId);
        userDao.addUser(user);
        Assert.assertEquals(user, userDao.deleteUser(userId));
    }

    @Test
    public void isUserExistTest() {
        User user = new User();
        int userId = Math.abs(random.nextInt());
        user.setUerId(userId);
        userDao.addUser(user);
        Assert.assertTrue(userDao.isUserExist(userId));
    }
}