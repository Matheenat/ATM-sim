package com.atm.logic;
import java.util.HashMap;
import java.util.Map;
public class Bank {
	private Map<String, Account> accounts = new HashMap<>();
	private int nextId = 1;
	
	public Bank() {}
	
	public void addAccount(Account acc) {
		String generatedId = String.valueOf(nextId);
		acc.setID(generatedId);
		this.accounts.put(generatedId, acc);
		
		this.nextId++;
	}
	
	public void removeAccount(Account acc) {
		
	}
	public Account loginCheck(String pin, String id) {
		Account account = accounts.get(id);
		if(account != null) {
			if(account.validatePin(pin)) {
				return account;
			}
		}
		return null;
	}
}
