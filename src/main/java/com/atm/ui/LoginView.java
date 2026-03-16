package com.atm.ui;

import com.atm.logic.Account;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;

public class LoginView extends BaseView{
	public LoginView(App app) {
		super(app);
	}
	@Override
	public Parent getRoot() {
		GridPane grid = createBaseGrid();
		Label idLabel = new Label("Account ID:");
		TextField idField = new TextField();
		Label pinLabel = new Label("Pin:");
		PasswordField pinField = new PasswordField();
		Label statusLabel = new Label("Welcome to ATM");
		
		idField.setOnKeyPressed(event -> {
			if(event.getCode().equals(KeyCode.ENTER)) {
				pinField.requestFocus();
			}
		});
		
		Button loginbtn = new Button("Login");
		loginbtn.setOnAction(e -> {
			Account acc = app.getBank().loginCheck(idField.getText(), pinField.getText());
			if (acc != null) {
				app.setCurrentAccount(acc);
				app.showDashboard();
				
		    } 
			else {
		        statusLabel.setText("Access Denied: Invalid ID or PIN.");
		        statusLabel.setStyle("-fx-text-fill: red;");
			}
		});
		
		Button signupbtn = new Button("Signup");
		signupbtn.setOnAction(e -> app.showSignup());
		
		grid.add(statusLabel, 0, 0);
		grid.add(idLabel, 0, 1);
		grid.add(idField, 0, 2);
		grid.add(pinLabel, 0, 3);
		grid.add(pinField, 0, 4);
		grid.add(loginbtn, 0, 5);
		grid.add(signupbtn, 0, 6);
		
		return grid;
	}
	
}
