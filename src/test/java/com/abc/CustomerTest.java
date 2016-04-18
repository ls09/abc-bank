package com.abc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CustomerTest {
	
    @Test //Test customer statement generation
    public void testCustomerWithNoTransactions(){

        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "Total $0.00\n" +
                "\n" +
                "Savings Account\n" +
                "Total $0.00\n" +
                "\n" +
                "Total In All Accounts $0.00", henry.getStatement());
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testCustomerWithOverDraw(){ //Test customer trying to over draw

        Account checkingAccount = new CheckingAccount();

        Customer henry = new Customer("Mike").openAccount(checkingAccount);
        checkingAccount.deposit(100.0);
        checkingAccount.withdraw(200.0);
    }

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }
    
    @Test
    public void testMaxiSavingsAccountStatement(){

        Account maxiSavingsAccount = new MaxiSavingsAccount();

        Customer henry = new Customer("Henry").openAccount(maxiSavingsAccount);

        maxiSavingsAccount.deposit(1300.0);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Maxi Savings Account\n" +
                "  deposit $1,300.00\n" +
                "Total $1,300.00\n" +
                "\n" +
                "Total In All Accounts $1,300.00", henry.getStatement());
    }
    
    @Test
    public void testZeroAccount(){
        Customer oscar = new Customer("Oscar");
        assertEquals(0, oscar.getNumberOfAccounts());
    }

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new SavingsAccount());
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new CheckingAccount());
        oscar.openAccount(new SavingsAccount());
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new SavingsAccount());
        oscar.openAccount(new CheckingAccount());
        oscar.openAccount(new MaxiSavingsAccount());
        
        assertEquals(3, oscar.getNumberOfAccounts());
    }
    
    @Test
    public void testTransferFromCheckingToSavings() {

        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();
        
        Customer oscar = new Customer("Oscar")
                .openAccount(checkingAccount)
                .openAccount(savingsAccount);
        
        checkingAccount.deposit(500.0);
        oscar.transfer(checkingAccount, savingsAccount, 10);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testTransferFromCheckingToChecking() { 
    
    Account checkingAccount1= new CheckingAccount();
    Account checkingAccount2= new CheckingAccount(); 
    
    Customer oscar = new Customer("Oscar")
    		.openAccount(checkingAccount1)
         .openAccount(checkingAccount2);
    
    checkingAccount1.deposit(500.0);
    oscar.transfer(checkingAccount1, checkingAccount2, 10);
    //System.out.println(oscar.getStatement());
    
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testTransferFromNotPresent() { //from account not present

        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();
        
        Customer oscar = new Customer("Oscar")
                //.openAccount(checkingAccount)
                .openAccount(savingsAccount);
        
        checkingAccount.deposit(500.0);
        oscar.transfer(checkingAccount, savingsAccount, 10);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testTransferToNotPresent() { //to account not present

        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();
        
        Customer oscar = new Customer("Oscar")
                .openAccount(checkingAccount);
                //.openAccount(savingsAccount);
        
        checkingAccount.deposit(500.0);
        oscar.transfer(checkingAccount, savingsAccount, 10);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testTransferWithInsufficientFunds() { //from account has insufficient funds

        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();
        
        Customer oscar = new Customer("Oscar")
                .openAccount(checkingAccount)
                .openAccount(savingsAccount);
        
        //checkingAccount.deposit(500.0);
        oscar.transfer(checkingAccount, savingsAccount, 10);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testTransferWithInvalidAmount() { //attempt to transfer with invalid amount

        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();
        
        Customer oscar = new Customer("Oscar")
                .openAccount(checkingAccount)
                .openAccount(savingsAccount);
        
        checkingAccount.deposit(500.0);
        oscar.transfer(checkingAccount, savingsAccount, -10);
    }
}
