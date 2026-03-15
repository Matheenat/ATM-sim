package com.atm.main;

import com.atm.logic.*;
import com.atm.data.*;
import java.util.Scanner;
public class Main {

	public static void main(String[] args) {
		Bank myBank = new Bank();
		FileHandler fh = new FileHandler();
		fh.loadAccounts(myBank);
		try (Scanner keyboard = new Scanner(System.in)) {
			boolean running = true;
			boolean userSession = false;
			boolean signupBool = false;
			boolean loginBool = false;
			
			while(running) {
				System.out.print("Welcome to ATM\n");
				System.out.print("1. Create Account\n2. Login\n3. Quit\n");
				String firstSreenInput = keyboard.nextLine();
				switch(firstSreenInput) {
				case "1":
					signupBool = true;
					break;
					
				case "2":
					loginBool = true;
					break;
				case "3":
					running = false;
				}
				while(signupBool) {
					System.out.print("Create Account\n");
					System.out.print("Input PIN: ");
					String pinInput = keyboard.nextLine();
					System.out.print("Input starting deposit: ");
					String depositInput = keyboard.nextLine();
					
					Double startingBalance;
					if (depositInput.trim().isEmpty()) {
				        startingBalance = 0.0;
				    } 
					else {
				        try {
				            startingBalance = Double.parseDouble(depositInput);
				        } 
				        catch (NumberFormatException e) {
				            System.out.println("Invalid amount! Setting starting balance to $0.");
				            startingBalance = 0.0;
				        }
				    }
					Account newAcc = new Account(pinInput, startingBalance);
					myBank.addAccount(newAcc);
					System.out.print("Your account id number is: " + newAcc.getID() + "\n");
					fh.saveAccounts(myBank.getAllAccounts());
					signupBool = false;
				}
				
				
				while(loginBool) {
					System.out.print("Enter ID: ");
					String inputID = keyboard.nextLine();
					System.out.print("Enter PIN: ");
					String inputPin = keyboard.nextLine();
					System.out.print("\n");

					Account currentSession = myBank.loginCheck(inputID, inputPin);
					if (currentSession != null) {
						userSession = true;
					    System.out.println("Login Successful!\n");
					    loginBool = false;
					} else {
					    System.out.println("Access Denied: Invalid ID or PIN.");
					}
					
					while(userSession) {
						System.out.println("1. Balance\n2. Withdraw\n3. Deposit\n4. Transfer\n5. Ledger\n6. Logout");
						String choice = keyboard.nextLine();
						switch(choice) {
						case "1":
							System.out.println("Balance: " + currentSession.getBalance() + "\n");
							break;
							
						case "2":
							System.out.println("Withdraw: ");
							try {
								String inputString = keyboard.nextLine();
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
								String inputString = keyboard.nextLine();
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
							String targetId = keyboard.nextLine();
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
							System.out.println("--- Transaction History ---");
							for (String record : currentSession.getTransactionHistory()) {
						        System.out.println("- " + record);
						    }
						    break;
						case "6":
							userSession = false;
							System.out.print("Do you want to quit\n");
							String con = keyboard.nextLine();
							if(con.equals("y") || con.equals("yes")) {
								running = false;
								loginBool = false;
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
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
