package com.atm.main;
import com.atm.logic.*;
import java.util.Scanner;
public class Main {

	public static void main(String[] args) {
		Bank myBank = new Bank();
		Scanner keyboard = new Scanner(System.in);
		boolean running = true;
		
		myBank.addAccount(new Account("123",5000));
		myBank.addAccount(new Account("25363",366));
		
		boolean userSession = false;
		while(running) {
			System.out.print("Welcome to ATM\n");
			System.out.print("Enter ID: ");
			String inputID = keyboard.nextLine();
			System.out.print("Enter PIN: ");
			String inputPin = keyboard.nextLine();

			
			Account currentSession = myBank.loginCheck(inputID, inputPin);
			if (currentSession != null) {
				userSession = true;
			    System.out.println("Login Successful!\n");
			} else {
			    System.out.println("Access Denied: Invalid ID or PIN.");
			    running = false;
			}
			
			while(userSession) {
				System.out.println("1. Balance\n2. Withdraw\n3. Deposit\n4. Logout");
				String choice = keyboard.nextLine();
				if(choice.equals("4")) {
					userSession = false;
				}
			}
			
			System.out.print("Do you want to quit\n");
			String con = keyboard.nextLine();
			if(con.equals("y") || con.equals("yes")) {
				running = false;
			}
		}
	}
}
