package com.atm.main;
import com.atm.logic.*;
import com.atm.data.*;
import java.util.Scanner;
public class Main {

	public static void main(String[] args) {
		Bank myBank = new Bank();
		FileHandler fh = new FileHandler();
		fh.loadAccounts(myBank);
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
			}
			
			while(userSession) {
				System.out.println("1. Balance\n2. Withdraw\n3. Deposit\n4. Transfer\n5. Logout");
				String choice = keyboard.nextLine();
				switch(choice) {
				case "1":
					System.out.println("Balance: " + currentSession.getBalance() + "\n");
					break;
					
				case "2":
					System.out.println("Withdraw: ");
					try {
						String inputString = keyboard.next();
						double input = Double.parseDouble(inputString);						
						currentSession.withdraw(input);
						fh.saveAccounts(myBank.getAllAccounts());
					}
					catch (NumberFormatException e) {
						System.out.println("Please input a valid number");
					}
					break;
					
				case "3":
					System.out.println("Deposit: ");
					try {
						String inputString = keyboard.next();
						double input = Double.parseDouble(inputString);
						currentSession.deposit(input);
						fh.saveAccounts(myBank.getAllAccounts());
					}
					catch (NumberFormatException e) {
						System.out.println("Please input a valid number");
					}
					break;
				case "4":
					System.out.println("Recipient Id: ");
					String targetId = keyboard.next();
					Account receiver = myBank.getAccountId(targetId);
					
					if (receiver != null && receiver != currentSession) {
				        System.out.print("Enter amount to transfer: ");
				        double amount = Double.parseDouble(keyboard.nextLine());
				        
				        if (currentSession.transfer(receiver, amount)) {
				            System.out.println("Transfer successful!");
				            fh.saveAccounts(myBank.getAllAccounts()); 
				        } else {
				            System.out.println("Transfer failed: Insufficient funds.");
				        }
				    } else {
				        System.out.println("Error: Recipient not found or invalid.");
				    }
				    break;
					
				case "5":
					userSession = false;
					System.out.print("Do you want to quit\n");
					String con = keyboard.nextLine();
					if(con.equals("y") || con.equals("yes")) {
						running = false;
						fh.saveAccounts(myBank.getAllAccounts());
					}
					if(con.equals("n") || con.equals("no")) {
						running = true;
					}
					break;
					
				case "rem acc":
					System.out.print("Account to delete: ");
					String target = keyboard.nextLine();
					myBank.removeAccount(target);
					fh.saveAccounts(myBank.getAllAccounts());
				}
			}
		}
	}
}
