package com.alexslo.bank.model;

import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertFalse;

public class BalanceOverPeriodTest {

    @Test
    public void equalsTest() {
        BalanceOverPeriod firstObject = new BalanceOverPeriod(BigDecimal.valueOf(10), 2);
        BalanceOverPeriod secondObject = new BalanceOverPeriod(BigDecimal.valueOf(10), 2);
        assertTrue(firstObject.equals(secondObject));
    }

    @Test
    public void notEqualsTest() {
        BalanceOverPeriod firstObject = new BalanceOverPeriod(BigDecimal.valueOf(10), 2);
        BalanceOverPeriod secondObject = new BalanceOverPeriod(BigDecimal.valueOf(1), 3);
        assertFalse(firstObject.equals(secondObject));
    }
}