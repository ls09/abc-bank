package com.abc;

public class SavingsAccount extends Account {

	public double interestEarned() {

		lock.readLock().lock();
		try {
			double amount = sumTransactions();
			if (amount <= 1000)
				return amount * 0.001;
			else
				return 1 + (amount - 1000) * 0.002;
		} finally {
			lock.readLock().unlock();
		}
	}
}
