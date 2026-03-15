package com.atm.logic;

import java.util.HashMap;

import java.util.Map;
public class Bank {
	private Map<String, Account> accounts = new HashMap<>();
	private int nextId = 1;
	
	public Bank() {}
	
	public Account getAccountId(String id) {
		return accounts.get(id);
	}
	public void setNextId(int nextId) {
		this.nextId = nextId;
	}
	
	public Map<String, Account> getAllAccounts(){
		return this.accounts;
	}
	
	public void addAccount(Account acc) {
		if (acc.getID() == null) {
	        acc.setID(String.valueOf(nextId));
	        nextId++;
	    }
		this.accounts.put(acc.getID(), acc);
	}
	
	public void removeAccount(String id) {
	    this.accounts.remove(id);
	}
	
	public Account loginCheck(String id, String pin) {
		Account account = accounts.get(id);
		if(account != null) {
			if(account.validatePin(pin)) {
				return account;
			}
		}
		return null;
	}
}
