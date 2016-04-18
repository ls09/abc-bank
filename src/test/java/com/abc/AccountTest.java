package com.abc;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AccountTest {
	
	static Account checkingAccount;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		checkingAccount = new CheckingAccount();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		checkingAccount = null;
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test (expected= IllegalArgumentException.class) 
	public void testForZeroDeposit() {
		checkingAccount.deposit(0);
	}


	@Test (expected= IllegalArgumentException.class) 
	public void testForNegativeDeposit() {
		checkingAccount.deposit(-1);
	}
	
	@Test (expected= IllegalArgumentException.class) 
	public void testForZeroWithdraw() {
		checkingAccount.withdraw(0);
	}


	@Test (expected= IllegalArgumentException.class) 
	public void testForNegativeWithdraw() {
		checkingAccount.withdraw(-1);
	}
	
}
