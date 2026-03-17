package com.atm.ui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.List;

public class DashboardView extends BaseView {

    public DashboardView(App app) {
        super(app);
    }

    @Override
    public Parent getRoot() {
        BorderPane mainLayout = new BorderPane();

        HBox headBar = new HBox();
        headBar.setPadding(new Insets(10));
        headBar.setStyle("-fx-background-color: #81A6C6; "
        		+ "-fx-border-color: #81A6C6; "
        		+ "-fx-border-width: 0 0 1 0;");
        
        Label atmTitleLabel = new Label("ATM Simulator");
        atmTitleLabel.setStyle("-fx-font-family: 'Roboto'; "
        		+ "-fx-font-weight: bold; "
        		+ "-fx-font-size: 40px;");
        
        headBar.getChildren().add(atmTitleLabel);
        mainLayout.setTop(headBar);

        VBox leftBar = new VBox(20);
        leftBar.setPadding(new Insets(10));
        leftBar.setMinWidth(300);
        leftBar.setStyle("-fx-background-color: #AACDDC; -fx-border-color: #93b4c2; -fx-border-width: 0 1 0 0;");

        VBox welcomeGroup = new VBox(2);
        Label welcomeLabel = new Label("Welcome back!");
        welcomeLabel.setStyle("-fx-font-family: 'Roboto'; -fx-font-size: 34px");
        
        Label userLabel = new Label(app.getCurrentAccount().getName());
        userLabel.setStyle("-fx-font-family: 'Roboto'; -fx-font-size: 23px");
        userLabel.setMaxWidth(300);
        userLabel.setEllipsisString("...");
        userLabel.setTextOverrun(OverrunStyle.ELLIPSIS);
        welcomeGroup.getChildren().addAll(welcomeLabel, userLabel);

        Button checkBalanceBtn = createNavButton("Balance");
        Button withdrawBtn = createNavButton("Withdraw");
        Button depositBtn = createNavButton("Deposit");
        Button transferBtn = createNavButton("Transfer");
        Button settingsBtn = createNavButton("Settings");
        Button logoutBtn = createNavButton("Logout");
        Button exitBtn = createNavButton("Exit");

        checkBalanceBtn.setOnAction(e -> app.balanceView());
        withdrawBtn.setOnAction(e -> app.withdrawView());
        depositBtn.setOnAction(e -> app.depositView());
        transferBtn.setOnAction(e -> app.transferView());
        settingsBtn.setOnAction(e -> app.settingsView());
        logoutBtn.setOnAction(e -> app.logout());
        exitBtn.setOnAction(e -> Platform.exit());

        leftBar.getChildren().addAll(welcomeGroup, checkBalanceBtn, withdrawBtn, depositBtn, transferBtn, settingsBtn, logoutBtn, exitBtn);
        mainLayout.setLeft(leftBar);

        VBox historyList = new VBox(20);
        historyList.setPadding(new Insets(10));
        historyList.setMinWidth(250);
        historyList.setStyle("-fx-background-color: #E6F0F5; -fx-border-color: #AACDDC; -fx-border-width: 0 0 0 1;");
        
        historyList.getChildren().add(new Label("Account Summary"));
        
        List<String> history = app.getCurrentAccount().getTransactionHistory();
        for (int i = history.size() - 1; i >= 0; i--) {
            Label currentLine = new Label(history.get(i));
            currentLine.setStyle("-fx-background-color: white; -fx-padding: 10; -fx-background-radius: 5; -fx-border-color: #DDD;");
            currentLine.setMaxWidth(Double.MAX_VALUE);
            historyList.getChildren().add(currentLine);
        }

        ScrollPane scrollHistory = new ScrollPane(historyList);
        scrollHistory.setFitToWidth(true);
        scrollHistory.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mainLayout.setRight(scrollHistory);

        GridPane centerGrid = new GridPane();
        centerGrid.setHgap(20);
        centerGrid.setPadding(new Insets(20));
        centerGrid.setAlignment(Pos.CENTER);
        
        centerGrid.add(app.getPieChart(), 0, 0);
        app.calculateCashFlow();
        
        mainLayout.setCenter(centerGrid);

        return mainLayout;
    }

    private Button createNavButton(String text) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setStyle("-fx-font-family: 'Inter'; -fx-font-size: 20px; -fx-padding: 15;");
        return btn;
    }
}