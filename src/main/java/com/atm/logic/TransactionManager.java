package com.atm.logic;

public class TransactionManager {
	public static void process(Account sender, String type, Account receiver, Double amount) {
        switch (type) {
            case "Withdraw" -> sender.withdraw(amount);
            case "Deposit"  -> sender.deposit(amount);
            case "Transfer" -> {
                if (receiver != null) {
                    sender.transfer(receiver, amount);
                }
            }
        }
    }
}
