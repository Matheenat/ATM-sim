package com.atm.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class AccountUpdateView extends BaseView {

    public AccountUpdateView(App app) {
        super(app);
    }
    private VBox createBaseLayout(String title) {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(50));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 20; "
                      + "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 10);");
        layout.setMaxSize(500, 300);

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-family: 'Inter'; -fx-font-size: 18px; -fx-text-fill: #555555;");
        layout.getChildren().add(titleLabel);
        
        return layout;
    }

    public Parent getResetNameView() {
        VBox layout = createBaseLayout("Input new name");
        Label statusLabel = new Label("");
        TextField nameField = new TextField();
        nameField.setPromptText("Input new name...");
        Button confirm = new Button("Confirm");

        confirm.setOnAction(e -> {
            String newName = nameField.getText().trim();
            if (newName.isEmpty()) { showError(statusLabel, "Name cannot be empty"); return; }
            if (newName.equals(app.getCurrentAccount().getName())) { showError(statusLabel, "Cannot be the same as current name"); return; }
            if (newName.length() > 20) { showError(statusLabel, "Too long (max 20 chars)"); return; }

            app.getCurrentAccount().setName(newName);
            app.getFH().saveAccounts(app.getBank().getAllAccounts());
            app.showDashboard();
        });

        layout.getChildren().add(0, statusLabel);
        layout.getChildren().addAll(nameField, confirm);
        return layout;
    }

    public Parent getResetPinView() {
        return getPinEntryView("Input new pin", null);
    }

    private Parent getPinEntryView(String title, String pinToMatch) {
        VBox layout = createBaseLayout(title);
        Label statusLabel = new Label("");
        PasswordField pinField = new PasswordField();
        pinField.setPromptText(title + "...");
        Button confirm = new Button("Confirm");

        confirm.setOnAction(e -> {
            String input = pinField.getText().trim();
            if (input.isEmpty()) { showError(statusLabel, "Field cannot be empty"); return; }
            if (input.length() < 6) { showError(statusLabel, "PIN must be at least 6 characters"); return; }
            
            if (pinToMatch == null) {
                if (input.equals(app.getCurrentAccount().getPin())) { 
                    showError(statusLabel, "Cannot be same as current PIN"); 
                    return; 
                }
                app.getMainLayout().setCenter(getPinEntryView("Confirm your new pin", input));
            } else {
            	
                if (!input.equals(pinToMatch)) { 
                    showError(statusLabel, "PINs do not match"); 
                    return; 
                }
                app.getCurrentAccount().setPin(input);
                app.getFH().saveAccounts(app.getBank().getAllAccounts());
                app.showDashboard();
            }
        });

        layout.getChildren().add(0, statusLabel);
        layout.getChildren().addAll(pinField, confirm);
        return layout;
    }

    private void showError(Label label, String msg) {
        label.setText("Error: " + msg);
        label.setStyle("-fx-text-fill: red;");
    }

    @Override
    public Parent getRoot() { return null; }
}