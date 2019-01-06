package com.alexslo.bank.model;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class BalanceOverPeriodTest {

    @Test
    public void equalsTest() {
        BalanceOverPeriod firstObject = new BalanceOverPeriod(BigDecimal.valueOf(10), 2);
        BalanceOverPeriod secondObject = new BalanceOverPeriod(BigDecimal.valueOf(10), 2);
        assertEquals(firstObject, secondObject);
    }

    @Test
    public void notEqualsTest() {
        BalanceOverPeriod firstObject = new BalanceOverPeriod(BigDecimal.valueOf(10), 2);
        BalanceOverPeriod secondObject = new BalanceOverPeriod(BigDecimal.valueOf(1), 3);
        assertNotEquals(firstObject, secondObject);
    }

    @Test
    public void hashCodeTest() {
        BalanceOverPeriod bp = new BalanceOverPeriod(BigDecimal.valueOf(100), 1);
        BalanceOverPeriod pb = new BalanceOverPeriod(BigDecimal.valueOf(100), 1);
        assertTrue(bp.hashCode() == pb.hashCode());
    }
}