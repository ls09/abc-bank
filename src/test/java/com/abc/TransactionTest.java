package com.abc;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TransactionTest {
    @Test
    public void testTransaction() {
        Transaction t = new Transaction(5);
        assertTrue(t instanceof Transaction);
    }
}
