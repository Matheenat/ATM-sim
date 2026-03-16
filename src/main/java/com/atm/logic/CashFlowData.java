package com.atm.logic;

import java.util.List;

public class CashFlowData{
	private Double totalWithdraw = 0.0;
	private Double totalDeposit = 0.0;
	private Double totalReceived = 0.0;
	private Double totalTransfer = 0.0;
	
	public CashFlowData(Account acc) {
		if(acc != null) {
			calculate(acc.getTransactionHistory());
		}
	}
	
	private void calculate(List<String> history) {
		double amount = 0.0;
		String amountStr;
		for(int i = history.size() - 1; i >= 0; i--) {
			String line = history.get(i);
			if(line.contains("withdraw")) {
				amountStr = line.replace("withdraw $", "");
				amount = Double.parseDouble(amountStr);
				totalWithdraw += amount;
			}
			else if(line.contains("deposit")) {
				amountStr = line.replace("deposit $", "");
				amount = Double.parseDouble(amountStr);
				totalDeposit += amount;
			}
			else if(line.contains("transfer")) {
				int start = line.indexOf("$") + 1;
				int end = line.indexOf(" ", start);
				amountStr = line.substring(start, end);
				amount = Double.parseDouble(amountStr);
				totalTransfer += amount;
			}
			else if(line.contains("receieve")) {
				amountStr = line.replace("receieve $", "");
				amount = Double.parseDouble(amountStr);
				totalReceived += amount;
			}
		}
    }
	public double getTotalIn() { return totalDeposit + totalReceived; }
    public double getTotalOut() { return totalWithdraw + totalTransfer; }
    
    public double getTotalWithdraw() { return totalWithdraw; }
    public double getTotalDeposit() { return totalDeposit; }
    public double getTotalTransfer() { return totalTransfer; }
    public double getTotalReceived() { return totalReceived; }
}
