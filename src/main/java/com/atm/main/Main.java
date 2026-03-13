package com.atm.main;
import com.atm.logic.*;
public class Main {

	public static void main(String[] args) {
		Bank myBank = new Bank();
		myBank.addAccount(new Account("123",5000));
		myBank.addAccount(new Account("25363",366));
		Account currentSession = myBank.loginCheck("25363","2");
		
		if (currentSession != null) {
		    System.out.println("Login Successful! Balance: " + currentSession.getBalance());
		} else {
		    System.out.println("Access Denied: Invalid ID or PIN.");
		}

	}

}
