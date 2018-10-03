package com.alexslo.bank.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class UserTest {

    @Test
    public void getUserIdTest() {
        Random rand = new Random();
        int userId = Math.abs(rand.nextInt());
        User user = new User();
        user.setUerId(userId);
        Assert.assertEquals(userId, user.getUserId());
    }
}