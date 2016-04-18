package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class Account {

	private List<Transaction> transactions = new ArrayList<Transaction>();
	protected ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	public List<Transaction> getTransactions() {
		return transactions;
	}

	protected void deposit(double amount) {
		
		lock.writeLock().lock();
		try {
			if (amount <= 0) {
				throw new IllegalArgumentException(Constants.amount_error_msg);
			} else {
				transactions.add(new Transaction(amount));
			}
		} finally {
			lock.writeLock().unlock();
		}
	}

	protected void withdraw(double amount) {

		lock.writeLock().lock();
		try {
			if (amount <= 0) 
				throw new IllegalArgumentException(Constants.amount_error_msg);
			
	 		if ( sumTransactions() < amount ) 
	 			throw new IllegalArgumentException( "account does not have the sufficient funds to withdraw amount: " + amount );
	 		
			transactions.add(new Transaction(-amount));
		} finally {
			lock.writeLock().unlock();
		}
	}

	abstract double interestEarned();

	public double sumTransactions() {
		
		// return checkIfTransactionsExist(true);
		lock.readLock().lock();
		try {
		double amount = 0.0;
		for (Transaction t : transactions)
			amount += t.getAmount();
		return amount;
		} finally {
			lock.readLock().unlock();
		}
	}

}
