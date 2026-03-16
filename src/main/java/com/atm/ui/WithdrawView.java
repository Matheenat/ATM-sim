package com.atm.ui;

public class WithdrawView extends TransactionBaseView {

    public WithdrawView(App app) {
        super(app, "Withdraw Cash", "Confirm Withdrawal");
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
            if (amount > app.getCurrentAccount().getBalance()) {
                showError("Insufficient funds.");
                return;
            }

            app.getMainLayout().setCenter(app.getPinView().getTransactionView("Withdraw", amount));

        } catch (NumberFormatException ex) {
            showError("Please enter a valid number.");
        }
    }
}