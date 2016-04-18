package com.abc;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;
    private static Bank bank;
    
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		bank = Bank.getInstance(); 
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}
	
	@Before
	public void setUp() throws Exception {
	
	}

	@After
	public void tearDown() throws Exception {
		bank.clearCustomers();
		//bank = null;
	}
    
    @Test
    public void testCustomerSummaryWithZeroAccount() {
        bank = Bank.getInstance(); 
        Customer john = new Customer("John");
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (0 accounts)", bank.customerSummary());
    }

    @Test
    public void CustomerSummaryWithOneAccount() {
        bank = Bank.getInstance(); 
        Customer john = new Customer("Peter");
        john.openAccount(new CheckingAccount());
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - Peter (1 account)", bank.customerSummary());
    }
    
    @Test
    public void testCustomerSummaryWithTwoAccounts() {
        bank = Bank.getInstance(); 
        Customer john = new Customer("Mary");
        john.openAccount(new CheckingAccount());
        john.openAccount(new SavingsAccount());
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - Mary (2 accounts)", bank.customerSummary());
    }

    @Test
    public void testCheckingAccount() {
        bank = Bank.getInstance(); 
        Account checkingAccount = new CheckingAccount();
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void testSavingsAccount_500() {
        bank = Bank.getInstance(); 
        Account savingsAccount = new SavingsAccount();
        bank.addCustomer(new Customer("Camel").openAccount(savingsAccount));

        savingsAccount.deposit(500.0);

        assertEquals(0.5, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test
    public void testSavingsAccount_1500() {
        bank = Bank.getInstance(); 
        Account savingsAccount = new SavingsAccount();
        bank.addCustomer(new Customer("Harry").openAccount(savingsAccount));

        savingsAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test
    public void testCheckingAndSavingsAccount() {
        bank = Bank.getInstance(); 
        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();
        Customer bill = new Customer("Vishal").openAccount(checkingAccount).openAccount(savingsAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(500.0);

        assertEquals(0.6, bank.totalInterestPaid(), DOUBLE_DELTA);
    }


    @Test
    public void testMaxiSavingsAccount() {
        bank = Bank.getInstance(); 
        Account maxiSavingsAccount = new MaxiSavingsAccount();
        bank.addCustomer(new Customer("Ming").openAccount(maxiSavingsAccount));

        maxiSavingsAccount.deposit(3000.0);

        assertEquals(170.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test
    public void testMaxiSavingsAccount_1500() {
        bank = Bank.getInstance(); 
        Account maxiSavingsAccount = new MaxiSavingsAccount();
        bank.addCustomer(new Customer("Tim").openAccount(maxiSavingsAccount));

        maxiSavingsAccount.deposit(1500.0);

        assertEquals(45.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test
    public void testMaxiSavingsAccount_500() {
        bank = Bank.getInstance(); 
        Account maxiSavingsAccount = new MaxiSavingsAccount();
        bank.addCustomer(new Customer("Joanne").openAccount(maxiSavingsAccount));

        maxiSavingsAccount.deposit(500.0);

        assertEquals(10.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test
    public void testZeroCustomers() {
        bank = Bank.getInstance(); 
        bank.getFirstCustomer();
        assertEquals("Error", bank.getFirstCustomer());
    }

}
