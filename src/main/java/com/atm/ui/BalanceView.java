package com.atm.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class BalanceView extends BaseView {

    public BalanceView(App app) {
        super(app);
    }

    @Override
    public Parent getRoot() {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(50));
        layout.setAlignment(Pos.CENTER);

        layout.setStyle("-fx-background-color: #FFFFFF; "
                + "-fx-background-radius: 20; "
                + "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 10);");
        layout.setMaxSize(500, 300);

        Label titleLabel = new Label("Available Balance");
        titleLabel.setStyle("-fx-font-family: 'Inter'; -fx-font-size: 18px; -fx-text-fill: #555555;");

        double currentBalance = app.getCurrentAccount().getBalance();
        Label amountLabel = new Label("$" + String.format("%.2f", currentBalance));
        amountLabel.setStyle("-fx-font-family: 'Roboto'; -fx-font-size: 60px; "
                + "-fx-font-weight: bold; -fx-text-fill: #2C3E50;");

        Label accIdLabel = new Label("Account ID: " + app.getCurrentAccount().getID());
        accIdLabel.setStyle("-fx-font-family: 'Inter'; -fx-font-size: 14px; -fx-text-fill: #999999;");

        Button backBtn = new Button("Return to Dashboard");
        backBtn.setStyle("-fx-background-color: #81A6C6; -fx-text-fill: white; -fx-padding: 10 20;");
        backBtn.setOnAction(e -> app.showDashboard());

        layout.getChildren().addAll(titleLabel, amountLabel, accIdLabel, backBtn);
        
        return layout;
    }
}