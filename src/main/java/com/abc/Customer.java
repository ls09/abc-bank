package com.abc;

import static java.lang.Math.abs;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import static com.abc.Constants.from_to_same_type_error_msg;
import static com.abc.Constants.missing_from_account;
import static com.abc.Constants.missing_to_account;
import static com.abc.Constants.insufficient_funds_for_transfer;
import static com.abc.Constants.invalid_transfer_amount;

public class Customer {
	
    private String name;
    private List<Account> accounts;
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    
	public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

	/*
    public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
	*/

    public String getName() {
        return name;
    }

    public Customer openAccount(Account account) {

		lock.writeLock().lock();
		try {
			accounts.add(account);
			return this;
		} finally {
			lock.writeLock().unlock();
		}
    }

    public int getNumberOfAccounts() {
    	
		lock.readLock().lock();
		try {
			return accounts.size();
		} finally {
			lock.readLock().unlock();
		}
    }

    public double totalInterestEarned() {
    	
		lock.readLock().lock();
		try {
			double total = 0;
			for (Account a : accounts)
				total += a.interestEarned();
			return total;
		} finally {
			lock.readLock().unlock();
		}
    }

	public String getStatement() {
		
		String statement = null;
		statement = "Statement for " + name + "\n";
		
		double total = 0.0;
		lock.readLock().lock();
		try {
			for (Account a : accounts) {
				statement += "\n" + statementForAccount(a) + "\n";
				total += a.sumTransactions();
			}
			statement += "\nTotal In All Accounts " + toDollars(total);
			return statement;
		} finally {
			lock.readLock().unlock();
		}

	}

    private String statementForAccount(Account a) {
    	
		String s = "";

		// Translate to pretty account type
		if (a instanceof CheckingAccount)
			s += "Checking Account\n";
		else if (a instanceof SavingsAccount)
			s += "Savings Account\n";
		else // if (a instanceof MaxiSavingsAccount)
			s += "Maxi Savings Account\n";

		// Now total up all the transactions
		lock.readLock().lock();
		try {
			double total = 0.0;
			for (Transaction t : a.getTransactions()) {
				s += "  " + (t.getAmount() < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.getAmount()) + "\n";
				total += t.getAmount();
			}
			s += "Total " + toDollars(total);
			return s;
		} finally {
			lock.readLock().unlock();
		}
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }

	public void transfer( Account from, Account to, double amount ) {
		
		if (from.getClass() == to.getClass()) {
			throw new IllegalArgumentException(from_to_same_type_error_msg );
		}
		
 		if ( !accounts.contains(from) ) {
 			throw new IllegalArgumentException( missing_from_account );
 		}
 		
 		if ( !this.accounts.contains( to ) ) {
 			throw new IllegalArgumentException( missing_to_account );
 		}
 		
 		if ( from.sumTransactions() < amount ) {
 			throw new IllegalArgumentException( insufficient_funds_for_transfer + amount );
 		}
 		
 		if ( amount <= 0 ) {
 			throw new IllegalArgumentException( invalid_transfer_amount );
 		}
 		
 		lock.writeLock().lock();
 		try {
	 		from.withdraw( amount );
	 		to.deposit( amount );
 		}
 		finally {
 			lock.writeLock().unlock();
 		}
 	}     
}
