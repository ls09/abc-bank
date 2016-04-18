package com.abc;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public final class Bank {
	
	protected static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	private static ArrayList<Customer> customers; 
	private static Bank bank = null;

	public void clearCustomers() {
		
		lock.writeLock().lock();
		try {
		if (customers != null) customers.clear();
		}
		finally {
			lock.writeLock().unlock();
		}
	}
	
	private Bank() {
		customers = new ArrayList<Customer>();
	}

	public static Bank getInstance() {
		
 		
		lock.writeLock().lock();
		try {
			
			if (bank == null )
				bank = new Bank();
			
			if (customers == null)
				customers = new ArrayList<Customer>();
				
			return bank;
		}
		finally {
			lock.writeLock().unlock();
		}
	}

	public void addCustomer(Customer customer) {
		
		lock.writeLock().lock();
		try {
		customers.add(customer);
		} finally {
			lock.writeLock().unlock();
		}
	}

	public String customerSummary() {
		String summary = "Customer Summary";
		lock.readLock().lock();
		try {
		for (Customer c : customers)
			summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
		return summary;
		} finally {
			lock.readLock().unlock();
		}
	}

	// Make sure correct plural of word is created based on the number passed
	// in:
	// If number passed in is 1 just return the word otherwise add an 's' at the
	// end
	private String format(int number, String word) {
		return number + " " + (number == 1 ? word : word + "s");
	}

	public double totalInterestPaid() {
		lock.readLock().lock();
		try {
		double total = 0;
		for (Customer c : customers)
			total += c.totalInterestEarned();
		return total;
		} finally {
			lock.readLock().unlock();
		}

		
	}

	public String getFirstCustomer() {
		
		lock.writeLock().lock(); // Use writeLock because customers list is modified below
		try {
		customers = null;
		try {
			return customers.get(0).getName();
		} catch (Exception e) {
			e.printStackTrace();
			return "Error";
		}
		} finally {
			lock.writeLock().lock();
		}
	}
}
