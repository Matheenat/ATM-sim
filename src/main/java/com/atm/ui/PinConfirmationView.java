package com.atm.ui;

import com.atm.logic.Account;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.VBox;

public class PinConfirmationView extends BaseView {

    public PinConfirmationView(App app) {
        super(app);
    }
    private VBox createBaseLayout(String subHeaderText) {
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        
        Label header = new Label("Input PIN to proceed");
        header.setStyle("-fx-font-family: 'Inter'; -fx-font-size: 22px; -fx-font-weight: bold;");
        
        layout.getChildren().add(header);

        if (subHeaderText != null) {
            Label subHeader = new Label(subHeaderText);
            subHeader.setStyle("-fx-font-family: 'Inter'; -fx-font-size: 14px; -fx-font-weight: bold;");
            layout.getChildren().add(subHeader);
        }

        return layout;
    }

    public Parent getTransactionView(String type, Double amount) {
        return getTransferView(type, null, amount);
    }

    public Parent getTransferView(String type, String targetID, Double amount) {
        VBox layout = createBaseLayout(type + " $" + amount + "?");
        Label statusLabel = new Label("");
        PasswordField pinField = new PasswordField();
        pinField.setMaxWidth(200);
        Button confirmBtn = new Button("Authorize");
        confirmBtn.setStyle("-fx-background-color: #00A950; -fx-text-fill: white;");

        confirmBtn.setOnAction(e -> {
            if (app.getCurrentAccount() != null && app.getCurrentAccount().validatePin(pinField.getText())) {
                Account receiver = (targetID != null) ? app.getBank().getAccountId(targetID) : null;
                
                app.txService.execute(type, receiver, amount);
                app.showDashboard();
            } else {
                statusLabel.setText("Invalid PIN!");
                statusLabel.setStyle("-fx-text-fill: red;");
            }
        });

        layout.getChildren().add(0, statusLabel);
        layout.getChildren().addAll(pinField, confirmBtn);
        return layout;
    }

    public Parent getResetView(String scene) {
        VBox layout = createBaseLayout(null);
        Label statusLabel = new Label("");
        PasswordField pinField = new PasswordField();
        pinField.setMaxWidth(200);
        Button confirmBtn = new Button("Authorize");
        confirmBtn.setStyle("-fx-background-color: #00A950; -fx-text-fill: white;");

        confirmBtn.setOnAction(e -> {
            if (app.getCurrentAccount() != null && app.getCurrentAccount().validatePin(pinField.getText())) {
                app.resetManagerView(scene);
            } else {
                statusLabel.setText("Invalid PIN!");
                statusLabel.setStyle("-fx-text-fill: red;");
            }
        });

        layout.getChildren().add(0, statusLabel);
        layout.getChildren().addAll(pinField, confirmBtn);
        return layout;
    }

    @Override
    public Parent getRoot() { return null; } // Not used for this specific helper class
}