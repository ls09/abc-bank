package com.abc;

public class CheckingAccount extends Account {

	public double interestEarned() {
		
		lock.readLock().lock();
		try {
		double amount = sumTransactions();
		return amount * 0.001;
		} finally {
			lock.readLock().unlock();
		}
	}
}
