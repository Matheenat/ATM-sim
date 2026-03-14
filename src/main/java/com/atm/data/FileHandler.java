package com.atm.data;

import com.atm.logic.*;
import java.io.*;
import java.util.*;

public class FileHandler {
	private String filename = "accounts.txt";
	public void saveAccounts(Map<String, Account> accounts) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Account acc : accounts.values()) {
                String line = acc.getID() + "," + 
                              acc.getPin() + "," + 
                              acc.getBalance();
                writer.println(line);
            }
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving to file: " + e.getMessage());
        }
    }
	
	public void loadAccounts(Bank bank) {
		File file = new File(filename);
        if (!file.exists()) return; 

        int maxId = 0;
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");

                String id = parts[0];
                String pin = parts[1];
                double balance = Double.parseDouble(parts[2]);

                Account acc = new Account(pin, balance);
                acc.setID(id);
                bank.addAccount(acc); 

                maxId = Math.max(maxId, Integer.parseInt(id));
            }

            bank.setNextId(maxId + 1);
            
        } catch (FileNotFoundException e) {
            System.out.println("No save file found.");
        }
    }
}
