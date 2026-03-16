package com.atm.ui;

import com.atm.logic.Account;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class TransferView extends TransactionBaseView {

    private TextField recipientField;

    public TransferView(App app) {
        super(app, "Transfer Cash", "Confirm Transfer");

        Label recipientLabel = new Label("Recipient's ID");
        recipientField = new TextField();
        recipientField.setPromptText("Enter recipient's ID");
        recipientField.setMaxWidth(300);

        layout.getChildren().add(2, recipientLabel);
        layout.getChildren().add(3, recipientField);
    }

    @Override
    protected void handleAction() {
        String stringAmount = amountField.getText().trim();
        String targetID = recipientField.getText().trim();
        if (targetID.isEmpty()) {
            showError("Please enter a valid ID.");
            return;
        }

        Account recipient = app.getBank().getAccountId(targetID);
        if (recipient == null) {
            showError("Account not found with this ID.");
            return;
        }

        if (recipient.equals(app.getCurrentAccount())) {
            showError("Cannot transfer money to yourself.");
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

            app.getMainLayout().setCenter(app.getPinView().getTransferView("Transfer", targetID, amount));

        } catch (NumberFormatException ex) {
            showError("Please enter a valid number.");
        }
    }
}