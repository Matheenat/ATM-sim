package com.atm.logic;

public class Account {
	private String id;
	private String pin;
	private double balance;
	
	public Account(String pin, double balance) {
		this.pin = pin;
		this.balance = Math.max(0, balance);
	}
	public void setID(String id) {
		this.id = id;
	}
	public String getID() {
		return this.id;
	}
	
	public double getBalance() {
		return this.balance;
	}
	
	public boolean validatePin(String inputPin) {
		return this.pin.equals(inputPin);
	}
	
	public boolean withdraw(double amount) {
		if(amount > 0 && amount <= this.balance) {
			this.balance -= amount;
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean deposit(double amount) {
		if(amount > 0) {
			this.balance += amount;
			return true;
		}
		else {
			return false;
		}
	}
}
