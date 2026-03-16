package com.atm.ui;

public class DepositView extends TransactionBaseView {

    public DepositView(App app) {
        super(app, "Deposit Cash", "Confirm Deposit");
    }

    @Override
    protected void handleAction() {
        String stringAmount = amountField.getText().trim();

        if (stringAmount.isEmpty()) {
            showError("Please enter a valid number.");
            return;
        }

        try {
            Double amount = Double.parseDouble(stringAmount);
            
            if (amount <= 0) {
                showError("Amount must be greater than 0.");
                return;
            }
            
            app.getMainLayout().setCenter(app.getPinView().getTransactionView("Deposit", amount));

        } catch (NumberFormatException ex) {
            showError("Please enter a valid number.");
        }
    }
}