package com.atm.main;
import com.atm.logic.*;
import java.util.Scanner;
public class Main {

	public static void main(String[] args) {
		Bank myBank = new Bank();
		myBank.addAccount(new Account("123",5000));
		myBank.addAccount(new Account("25363",366));
		
		Scanner keyboard = new Scanner(System.in); 
		System.out.print("Enter ID: ");
		String inputID = keyboard.nextLine();
		System.out.print("Enter PIN: ");
		String inputPin = keyboard.nextLine();

		
		Account currentSession = myBank.loginCheck(inputID, inputPin);
		
		if (currentSession != null) {
		    System.out.println("Login Successful! Balance: " + currentSession.getBalance());
		} else {
		    System.out.println("Access Denied: Invalid ID or PIN.");
		}

	}

}
