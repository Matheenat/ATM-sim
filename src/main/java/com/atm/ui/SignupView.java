package com.atm.ui;

import com.atm.logic.Account;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;

public class SignupView extends BaseView{
	public SignupView(App app) {
		super(app);
	}
	@Override
	public Parent getRoot() {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
	    grid.setVgap(10);
	    Label statusLabel = new Label("");
	    Label nameLabel = new Label("Input name");
	    TextField nameField = new TextField();
	    Label pinLabel = new Label("Create Pin:");
		PasswordField pinField = new PasswordField();
	    Label startDeposit = new Label("Initial Deposit:");
	    TextField depositField = new TextField();
	    
	    nameField.setOnKeyPressed(event -> {
	    	if(event.getCode().equals(KeyCode.ENTER)) {
	    		pinField.requestFocus();
	    	}
	    });
	    
	    pinField.setOnKeyPressed(event -> {
	    	if(event.getCode().equals(KeyCode.ENTER)) {
	    		depositField.requestFocus();
	    	}
	    });
	    
	    Button createAcc = new Button("Create Account");
	    createAcc.setOnAction(e -> {
	    	String name = nameField.getText().trim();
	    	if(name.length() > 20) {
	    		statusLabel.setText("Error: Name cannot be longer than 20 characters");
	            statusLabel.setStyle("-fx-text-fill: red;");
	            return;
	    	}
	    	String pinText = pinField.getText().trim();
	    	if(pinText.isEmpty()) {
	    		statusLabel.setText("Error: PIN cannot be empty!");
	            statusLabel.setStyle("-fx-text-fill: red;");
	            return;
	    	}
	    	if(pinText.length() < 6) {
	    		statusLabel.setText("Error: PIN must be atleast 6 characters!");
	            statusLabel.setStyle("-fx-text-fill: red;");
	            return;
	    	}
	    	Double initDepo;
	    	String depoText = depositField.getText().trim();
	    	if(depoText.isEmpty()) {
	    		initDepo = 0.0;
	    	}
	    	else {
	    		try{
	    			initDepo = Double.parseDouble(depoText);
	    		}
	    		catch(NumberFormatException ex) {
	    			statusLabel.setText("Error: Deposit must be a number!");
	    	        statusLabel.setStyle("-fx-text-fill: red;");
	    	        return; 
	    	    }
	    	}
	    	
	    	Account acc = new Account(pinText, initDepo);
	    	acc.setName(name);
    		app.getBank().addAccount(acc);
    		app.fh.saveAccounts(app.getBank().getAllAccounts());
    		app.showSuccess(acc.getID());
	    	
	    });
	    grid.add(statusLabel, 0, 0);
	    grid.add(nameLabel, 0, 1);
	    grid.add(nameField, 0, 2);
	    grid.add(pinLabel, 0, 3);
	    grid.add(pinField, 0, 4);
	    grid.add(startDeposit, 0, 5);
	    grid.add(depositField, 0, 6);
	    grid.add(createAcc, 0, 7);
	    
	    return grid;
	}
}
