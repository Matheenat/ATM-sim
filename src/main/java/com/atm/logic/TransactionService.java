package com.atm.logic;

import com.atm.ui.App;

public class TransactionService {
    private App app;

    public TransactionService(App app) {
        this.app = app;
    }

    public void execute(String type, Account receiver, Double amount) {
        TransactionManager.process(app.getCurrentAccount(), type, receiver, amount);
        app.getFH().saveAccounts(app.getBank().getAllAccounts());
        app.calculateCashFlow();
        app.showDashboard();
    }
}