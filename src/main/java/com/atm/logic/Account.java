package com.atm.logic;

import java.util.ArrayList;

import java.util.List;
public class Account {
	private String id;
	private String pin;
	private double balance;
	private String name;
	private List<String> transactionHistory = new ArrayList<>();
	
	public Account(String pin, double balance) {
		this.pin = pin;
		this.balance = Math.max(0, balance);
		this.name = "New user";
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
	public void setID(String id) {
		this.id = id;
	}
	public String getID() {
		return this.id;
	}
	
	public String getPin() {
		return this.pin;
	}
	
	public double getBalance() {
		return this.balance;
	}
	
	public List<String> getTransactionHistory(){
		return this.transactionHistory;
	}
	
	public void addHistoryRecord(String StringText) {
		this.transactionHistory.add(StringText);
	}
	
	public boolean validatePin(String inputPin) {
		return this.pin.equals(inputPin);
	}
	
	public boolean withdraw(double amount) {
		if(amount > 0 && amount <= this.balance) {
			this.balance -= amount;
			this.transactionHistory.add("withdraw $" + amount);
			return true;
		}
		else {
			return false;
		}
	}
	public boolean withdraw(double amount, String t) {
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
			this.transactionHistory.add("deposit $" + amount);
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean deposit(double amount, String t) {
		if(amount > 0) {
			this.balance += amount;
			this.transactionHistory.add("receieve $" + amount);
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean transfer(Account receiver, double amount) {
		if(this.withdraw(amount, "t")) {
			receiver.deposit(amount, "t");
			this.transactionHistory.add("transfer $" + amount + " to account id: " + receiver.getID());
			return true;
		}
		
		return false;
	}
}
