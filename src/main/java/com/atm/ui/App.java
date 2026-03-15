package com.atm.ui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import com.atm.logic.*;
import com.atm.data.*;

public class App extends Application{
	private Stage window;
	Bank myBank = new Bank();
	FileHandler fh = new FileHandler();
	@Override
    public void start(Stage primaryStage) {
		this.window = primaryStage;
		fh.loadAccounts(myBank);
		
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 1408, 896);

        primaryStage.setTitle("Atm-Sim");
        primaryStage.setScene(scene);
        showLogin();
    }
	
	public void showLogin() {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
	    grid.setVgap(10);
		
		Label idLabel = new Label("Account ID:");
		TextField idField = new TextField();
		Label pinLabel = new Label("Pin:");
		PasswordField pinField = new PasswordField();
		Label statusLabel = new Label("Welcome to ATM");
		
		Button loginbtn = new Button("Login");
		loginbtn.setOnAction(e -> {
			Account acc = myBank.loginCheck(idField.getText(), pinField.getText());
			if (acc != null) {
				showDashboard(acc);
				
		    } 
			else {
		        statusLabel.setText("Access Denied: Invalid ID or PIN.");
		        statusLabel.setStyle("-fx-text-fill: red;");
		}});
		
		Button signupbtn = new Button("Signup");
		signupbtn.setOnAction(e -> showSignup());
		
		grid.add(statusLabel, 0, 0);
		grid.add(idLabel, 0, 1);
		grid.add(idField, 0, 2);
		grid.add(pinLabel, 0, 3);
		grid.add(pinField, 0, 4);
		grid.add(loginbtn, 0, 5);
		grid.add(signupbtn, 0, 6);
		Scene loginScene = new Scene(grid, 300, 400);
	    window.setScene(loginScene);
	    window.show();
	}
	
	public void showSignup() {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
	    grid.setVgap(10);
	    Label statusLabel = new Label("");
	    Label pinLabel = new Label("Create Pin:");
		PasswordField pinField = new PasswordField();
	    Label startDeposit = new Label("Initial Deposit:");
	    TextField depositField = new TextField();
	    
	    Button createAcc = new Button("Create Account");
	    createAcc.setOnAction(e -> {
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
    		myBank.addAccount(acc);
    		fh.saveAccounts(myBank.getAllAccounts());
    		showSuccess(acc.getID());
	    	
	    });
	    grid.add(statusLabel, 0, 0);
	    grid.add(pinLabel, 0, 1);
	    grid.add(pinField, 0, 2);
	    grid.add(startDeposit, 0, 3);
	    grid.add(depositField, 0, 4);
	    grid.add(createAcc, 0, 5);
	    Scene signupScene = new Scene(grid, 300, 400);
	    window.setScene(signupScene);
	    window.show();
	}
	
	public void showSuccess(String id) {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
	    grid.setVgap(10);
	    
	    Label success = new Label("Account Created successfully!");
	    success.setStyle("-fx-text-fill: green;");
	    Label userid = new Label("Your Account id is \"" + id + "\"");
	    userid.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
	    Button confirm = new Button("Confirm");
	    confirm.setOnAction(e -> showLogin());
	    
	    grid.add(success, 0, 0);
	    grid.add(userid, 0, 1);
	    grid.add(confirm, 0, 2);
	    Scene successScene = new Scene(grid, 300, 400);
	    window.setScene(successScene);
	    window.show();
	}
	
	public void showDashboard(Account acc) {
		Label welcome = new Label("test ui 123 123 12 12 1");
	    Scene dashScene = new Scene(new StackPane(welcome), 300, 400);
	    window.setScene(dashScene);
	}

    public static void main(String[] args) {
        launch(args);
    }
}
