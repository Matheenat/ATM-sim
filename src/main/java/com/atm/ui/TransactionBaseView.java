package com.atm.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public abstract class TransactionBaseView extends BaseView {
    protected VBox layout;
    protected Label titleLabel;
    protected Label statusLabel;
    protected TextField amountField;
    protected Button confirmBtn;
    protected Button backBtn;

    public TransactionBaseView(App app, String titleText, String buttonText) {
        super(app);
        initUI(titleText, buttonText);
    }

    private void initUI(String titleText, String buttonText) {
        layout = new VBox(20);
        layout.setPadding(new Insets(30));
        layout.setAlignment(Pos.CENTER);

        titleLabel = new Label(titleText);
        titleLabel.setStyle("-fx-font-family: 'Inter'; "
        		+ "-fx-font-size: 24px;");

        statusLabel = new Label("");
        
        amountField = new TextField();
        amountField.setPromptText("Enter amount...");
        amountField.setMaxWidth(300);

        confirmBtn = new Button(buttonText);
        confirmBtn.setStyle("-fx-background-color: #00A950; "
        		+ "-fx-text-fill: white;");

        backBtn = new Button("Return to Dashboard");
        backBtn.setOnAction(e -> app.showDashboard());

        layout.getChildren().addAll(titleLabel, statusLabel, amountField, confirmBtn, backBtn);
  
        confirmBtn.setOnAction(e -> handleAction());
    }

    protected abstract void handleAction();

    protected void showError(String message) {
        statusLabel.setText("Error: " + message);
        statusLabel.setStyle("-fx-text-fill: red;");
    }

    @Override
    public Parent getRoot() {
        return layout;
    }
}