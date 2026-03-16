package com.atm.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import com.atm.logic.*;
import com.atm.data.*;
import com.atm.util.FontsLoader;

public class App extends Application{
	private Stage window;
	private BorderPane mainLayout;
	private Account currentAccount;
	private int screenWidth = 1408;
	private int screenHeight = 896;
	private int smallscreenWidth = 300;
	private int smallscreenHeight = 400;
	Bank myBank = new Bank();
	FileHandler fh = new FileHandler();
	@Override
    public void start(Stage primaryStage) {
		FontsLoader.loadFonts();
		this.window = primaryStage;
		fh.loadAccounts(myBank);
		
        StackPane root = new StackPane();
        Scene scene = new Scene(root, screenWidth, screenHeight);

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
		
		idField.setOnKeyPressed(event -> {
			if(event.getCode().equals(KeyCode.ENTER)) {
				pinField.requestFocus();
			}
		});
		
		Button loginbtn = new Button("Login");
		loginbtn.setOnAction(e -> {
			Account acc = myBank.loginCheck(idField.getText(), pinField.getText());
			if (acc != null) {
				this.currentAccount = acc;
				showDashboard();
				
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
		Scene loginScene = new Scene(grid, smallscreenWidth, smallscreenHeight);
	    window.setScene(loginScene);
	    window.show();
	}
	
	public void showSignup() {
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
    		myBank.addAccount(acc);
    		fh.saveAccounts(myBank.getAllAccounts());
    		showSuccess(acc.getID());
	    	
	    });
	    grid.add(statusLabel, 0, 0);
	    grid.add(nameLabel, 0, 1);
	    grid.add(nameField, 0, 2);
	    grid.add(pinLabel, 0, 3);
	    grid.add(pinField, 0, 4);
	    grid.add(startDeposit, 0, 5);
	    grid.add(depositField, 0, 6);
	    grid.add(createAcc, 0, 7);
	    Scene signupScene = new Scene(grid, smallscreenWidth, smallscreenHeight);
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
	    Scene successScene = new Scene(grid, smallscreenWidth, smallscreenHeight);
	    window.setScene(successScene);
	    window.show();
	}
	
	public void showDashboard() {
	    this.mainLayout = new BorderPane();
	    HBox headBar = new HBox();
	    headBar.setPadding(new Insets(10));
	    headBar.setStyle("-fx-background-color: #81A6C6; "
	    		+ "-fx-border-color: #81A6C6; "
	    		+ "-fx-border-width: 0 0 1 0;");
	    
	    Label atmTitleLabel = new Label("ATM Simulator");
	    atmTitleLabel.setStyle("-fx-font-family: 'Roboto'; "
	    		+ "-fx-font-weight: bold;"
	    		+ "-fx-font-size: 40px;");
	    headBar.getChildren().add(atmTitleLabel);
	    mainLayout.setTop(headBar);
	    
	    
	    VBox leftBar = new VBox(20);
	    leftBar.setPadding(new Insets(10));
	    leftBar.setMinWidth(300);
	    leftBar.setStyle("-fx-background-color: #AACDDC; "
	    		+ "-fx-border-color: #AACDDC; "
	    		+ "-fx-border-width: 0 1 0 0;");
	    
	    VBox welcomeGroup = new VBox(2);
	    Label welcomeLabel = new Label("Welcome back!");
	    welcomeLabel.setStyle("-fx-font-family: 'Roboto'; "
	    		+ "-fx-text-fill: black; "
	    		+ "-fx-font-size: 34px");
	    
	    Label userLabel = new Label(currentAccount.getName());
	    userLabel.setStyle("-fx-font-family: 'Roboto'; "
	    		+ "-fx-text-fill: black; "
	    		+ "-fx-font-size: 23px");
	    userLabel.setMaxWidth(300);
	    userLabel.setEllipsisString("...");
	    userLabel.setTextOverrun(OverrunStyle.ELLIPSIS);
	    welcomeGroup.getChildren().addAll(welcomeLabel, userLabel);
	    
	    Button checkBalanceBtn = new Button("Balance");
	    Button withdrawBtn = new Button("Withdraw");
	    Button depositBtn = new Button("Deposit");
	    Button transferBtn = new Button("Transfer");
	    Button settingsBtn = new Button("Settings");
	    checkBalanceBtn.setMaxWidth(Double.MAX_VALUE);
	    withdrawBtn.setMaxWidth(Double.MAX_VALUE);
	    depositBtn.setMaxWidth(Double.MAX_VALUE);
	    transferBtn.setMaxWidth(Double.MAX_VALUE);
	    settingsBtn.setMaxWidth(Double.MAX_VALUE);
	    
	    String buttonStyle = "-fx-font-family: 'Inter'; "
	    		+ "-fx-font-size: 20px; "
	    		+ "-fx-padding: 15;";
	    
	    checkBalanceBtn.setStyle(buttonStyle);
	    withdrawBtn.setStyle(buttonStyle);
	    depositBtn.setStyle(buttonStyle);
	    transferBtn.setStyle(buttonStyle);
	    settingsBtn.setStyle(buttonStyle);

	    leftBar.getChildren().addAll(welcomeGroup, checkBalanceBtn, withdrawBtn, depositBtn, transferBtn, settingsBtn);
	    checkBalanceBtn.setOnAction(e -> {
	    	mainLayout.setCenter(balanceView());
	    });
	    withdrawBtn.setOnAction(e -> {
	    	mainLayout.setCenter(withdrawView());
	    });
	    depositBtn.setOnAction(e -> {
	    	mainLayout.setCenter(depositView());
	    });
	    transferBtn.setOnAction(e -> {
	    	mainLayout.setCenter(transferView());
	    });
	    
	    mainLayout.setLeft(leftBar);

	    VBox rightBar = new VBox(20);
	    rightBar.setPadding(new Insets(10));
	    rightBar.setMinWidth(200);
	    rightBar.setStyle("-fx-border-color: black; "
	    		+ "-fx-border-width: 0 0 0 1;");
	    
	    rightBar.getChildren().add(new Label("Account Summary"));
	    mainLayout.setRight(rightBar);
	    
	    GridPane centerGrid = new GridPane();
	    centerGrid.setHgap(20);
	    centerGrid.setPadding(new Insets(20));
	    centerGrid.setAlignment(Pos.CENTER);
	    
	    VBox gainChart = new VBox(new Label("Money Gain Chart Placeholder"));
	    VBox goneChart = new VBox(new Label("Money Gone Chart Placeholder"));
	    
	    centerGrid.add(gainChart, 0, 0);
	    centerGrid.add(goneChart, 1, 0);
	    mainLayout.setCenter(centerGrid);
	    
	    Scene dashBoardScene = new Scene(mainLayout, screenWidth, screenHeight);
	    window.setScene(dashBoardScene);
	    window.show();
	}
	
	private void executeTransaction(String TransactionType, Double amount) {
		if (TransactionType.equals("Withdraw")) {
	        this.currentAccount.withdraw(amount);
	    } 
		else if (TransactionType.equals("Deposit")) {
			this.currentAccount.deposit(amount);
	    }
		fh.saveAccounts(myBank.getAllAccounts());
	}

	private void executeTransaction(String TransactionType, Account receiver, Double amount) {
		if (TransactionType.equals("Transfer") && receiver != null) {
			this.currentAccount.transfer(receiver, amount);
		}
		fh.saveAccounts(myBank.getAllAccounts());
	}
	
	private Node pinConfirmation(String TransactionType, Double amount) {
		VBox layout = new VBox(15);
	    layout.setAlignment(Pos.CENTER);
	    Label statusLabel = new Label("");
	    Label header = new Label("Input PIN to proceed");
	    header.setStyle("-fx-font-family: 'Inter'; "
	    		+ "-fx-font-size: 22px; "
	    		+ "-fx-font-weight: bold;");
	    
	    Label subHeader = new Label(TransactionType + " $" + amount + "?");
	    subHeader.setStyle("-fx-font-family: 'Inter'; "
	    		+ "-fx-font-size: 14px; "
	    		+ "-fx-font-weight: bold;");

	    PasswordField pinField = new PasswordField();
	    pinField.setMaxWidth(200);

	    Button confirmBtn = new Button("Authorize");
	    confirmBtn.setStyle("-fx-background-color: #00A950; -fx-text-fill: white;");

	    confirmBtn.setOnAction(e -> {
	        if(currentAccount != null && currentAccount.validatePin(pinField.getText())) {
	        	executeTransaction(TransactionType, amount);
	        	showDashboard();
	        }
	        else {
	        	statusLabel.setText("Invalid PIN!");
	            statusLabel.setStyle("-fx-text-fill: red;");
	        }
	    });

	    layout.getChildren().addAll(statusLabel, header, subHeader, pinField, confirmBtn);
	    return layout;
	}
	
	private Node pinConfirmation(String TransactionType, String targetID, Double amount) {
		Account receiver = myBank.getAccountId(targetID);
		VBox layout = new VBox(15);
	    layout.setAlignment(Pos.CENTER);
	    Label statusLabel = new Label("");
	    Label header = new Label("Input PIN to proceed");
	    header.setStyle("-fx-font-family: 'Inter'; "
	    		+ "-fx-font-size: 22px; "
	    		+ "-fx-font-weight: bold;");
	    
	    Label subHeader = new Label(TransactionType + " $" + amount + "?");
	    subHeader.setStyle("-fx-font-family: 'Inter'; "
	    		+ "-fx-font-size: 14px; "
	    		+ "-fx-font-weight: bold;");

	    PasswordField pinField = new PasswordField();
	    pinField.setMaxWidth(200);

	    Button confirmBtn = new Button("Authorize");
	    confirmBtn.setStyle("-fx-background-color: #00A950; "
	    		+ "-fx-text-fill: white;");

	    confirmBtn.setOnAction(e -> {
	        if(currentAccount != null && currentAccount.validatePin(pinField.getText())) {
	        	executeTransaction(TransactionType, receiver, amount);
	        	showDashboard();
	        }
	        else {
	        	statusLabel.setText("Invalid PIN!");
	            statusLabel.setStyle("-fx-text-fill: red;");
	        }
	    });

	    layout.getChildren().addAll(statusLabel, header, subHeader, pinField, confirmBtn);
	    return layout;
	}
	
	private Node balanceView() {
		VBox layout = new VBox(20);
	    layout.setPadding(new Insets(50));
	    layout.setAlignment(Pos.CENTER);
	    
	    layout.setStyle("-fx-background-color: #FFFFFF; "
	    		+ "-fx-background-radius: 20; "
	    		+ "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 10);");
	    layout.setMaxSize(500, 300);

	    Label titleLabel = new Label("Available Balance");
	    titleLabel.setStyle("-fx-font-family: 'Inter'; "
	    		+ "-fx-font-size: 18px; "
	    		+ "-fx-text-fill: #555555;");

	    double currentBalance = currentAccount.getBalance();
	    Label amountLabel = new Label("$" + String.format("%.2f", currentBalance));

	    amountLabel.setStyle("-fx-font-family: 'Roboto'; "
	    		+ "-fx-font-size: 60px; -fx-font-weight: bold; "
	    		+ "-fx-text-fill: #2C3E50;");

	    Label accIdLabel = new Label("Account ID: " + currentAccount.getID());
	    accIdLabel.setStyle("-fx-font-family: 'Inter'; "
	    		+ "-fx-font-size: 14px; "
	    		+ "-fx-text-fill: #999999;");

	    Button backBtn = new Button("Return to Dashboard");
	    backBtn.setStyle("-fx-background-color: #81A6C6; "
	    		+ "-fx-text-fill: white; "
	    		+ "-fx-padding: 10 20;");
	    
	    backBtn.setOnAction(e -> showDashboard());

	    layout.getChildren().addAll(titleLabel, amountLabel, accIdLabel, backBtn);
	    
	    return layout;
	}
	private Node withdrawView() {
		VBox layout = new VBox(20);
	    layout.setPadding(new Insets(30));
	    
	    Label title = new Label("Withdraw Cash");
	    title.setStyle("-fx-font-family: 'Inter'; "
	    		+ "-fx-font-size: 24px;");
	    
	    TextField amountField = new TextField();
	    amountField.setPromptText("Enter amount...");
	    
	    Label statusLabel = new Label("");
	    
	    Button confirmBtn = new Button("Confirm Withdrawal");
	    confirmBtn.setOnAction(e -> {
	    	String stringAmount = amountField.getText().trim();

	    	if(stringAmount.isEmpty()) {
		    	statusLabel.setText("Error: Please enter a valid number.");
	            statusLabel.setStyle("-fx-text-fill: red;");
	            return;
	    	}
            try {
	    		Double amount = Double.parseDouble(stringAmount);
	    		if(amount <= 0) {
	    			statusLabel.setText("Error: Amount must be greater than 0.");
	                statusLabel.setStyle("-fx-text-fill: red;");
	                return;
	    		}
	    		if (amount > currentAccount.getBalance()) {
	                statusLabel.setText("Error: Insufficient funds.");
	                statusLabel.setStyle("-fx-text-fill: red;");
	                return;
	            }
	    		mainLayout.setCenter(pinConfirmation("Withdraw", amount));
	    	}
	    	
	    	catch(NumberFormatException ex) {
	    		statusLabel.setText("Error: Please enter a valid number.");
	            statusLabel.setStyle("-fx-text-fill: red;");
	    	}
		    
	    });
	    
	    layout.getChildren().addAll(title, statusLabel, amountField, confirmBtn);
	    return layout;
	}
	
	private Node depositView() {
		VBox layout = new VBox(20);
	    layout.setPadding(new Insets(30));
	    
	    Label title = new Label("Deposit Cash");
	    title.setStyle("-fx-font-family: 'Inter'; "
	    		+ "-fx-font-size: 24px;");
	    
	    TextField amountField = new TextField();
	    amountField.setPromptText("Enter amount...");
	    
	    Label statusLabel = new Label("");
	    
	    Button confirmBtn = new Button("Confirm Deposition");
	    confirmBtn.setOnAction(e -> {
	    	String stringAmount = amountField.getText().trim();

	    	if(stringAmount.isEmpty()) {
		    	statusLabel.setText("Error: Please enter a valid number.");
	            statusLabel.setStyle("-fx-text-fill: red;");
	            return;
	    	}
            try {
	    		Double amount = Double.parseDouble(stringAmount);
	    		if(amount <= 0) {
	    			statusLabel.setText("Error: Amount must be greater than 0.");
	                statusLabel.setStyle("-fx-text-fill: red;");
	                return;
	    		}
	    		mainLayout.setCenter(pinConfirmation("Deposit", amount));
	    	}
	    	
	    	catch(NumberFormatException ex) {
	    		statusLabel.setText("Error: Please enter a valid number.");
	            statusLabel.setStyle("-fx-text-fill: red;");
	    	}
		    
	    });
	    
	    layout.getChildren().addAll(title, statusLabel, amountField, confirmBtn);
	    return layout;
	}
	
	private Node transferView() {
		VBox layout = new VBox(20);
	    layout.setPadding(new Insets(30));
	    
	    Label statusLabel = new Label("");
	    Label title = new Label("Transfer Cash");
	    title.setStyle("-fx-font-family: 'Inter'; "
	    		+ "-fx-font-size: 24px;");
	    
	    TextField amountField = new TextField();
	    amountField.setPromptText("Enter amount...");
	    
	    Label recipiantLabel = new Label("Recipiant's ID");
	    TextField recipiantField = new TextField();
	    recipiantField.setPromptText("Enter recipiant's ID");
	    
	    Button confirmBtn = new Button("Confirm Transfer");
	    confirmBtn.setOnAction(e -> {
	    	String stringAmount = amountField.getText().trim();
	    	String targetID = recipiantField.getText().trim();
	    	Account recipiant = myBank.getAccountId(targetID);
	    	if(recipiant == null) {
	    		statusLabel.setText("Error: Account not found with this id.");
	            statusLabel.setStyle("-fx-text-fill: red;");
	            return;
	    	}
	    	if(!recipiant.getID().equals(targetID)) {
	    		statusLabel.setText("Error: Account not found with this id.");
	            statusLabel.setStyle("-fx-text-fill: red;");
	            return;
	    	}
	    	if(recipiant.equals(currentAccount)) {
	    		statusLabel.setText("Error: Cannot transfer money to yourself.");
	            statusLabel.setStyle("-fx-text-fill: red;");
	            return;
	    	}
	    	
	    	if(targetID.isEmpty()){
	    		statusLabel.setText("Error: Please enter a valid id.");
	            statusLabel.setStyle("-fx-text-fill: red;");
	            return;
	    	}
	    	
	    	if(stringAmount.isEmpty()) {
		    	statusLabel.setText("Error: Please enter a valid number.");
	            statusLabel.setStyle("-fx-text-fill: red;");
	            return;
	    	}
            try {
	    		Double amount = Double.parseDouble(stringAmount);
	    		if(amount <= 0) {
	    			statusLabel.setText("Error: Amount must be greater than 0.");
	                statusLabel.setStyle("-fx-text-fill: red;");
	                return;
	    		}
	    		if (amount > currentAccount.getBalance()) {
	                statusLabel.setText("Error: Insufficient funds.");
	                statusLabel.setStyle("-fx-text-fill: red;");
	                return;
	            }
	    		mainLayout.setCenter(pinConfirmation("Transfer", targetID, amount));
	    	}
	    	
	    	catch(NumberFormatException ex) {
	    		statusLabel.setText("Error: Please enter a valid number.");
	            statusLabel.setStyle("-fx-text-fill: red;");
	    	}
		    
	    });
	    
	    layout.getChildren().addAll(title, statusLabel, recipiantLabel, recipiantField, amountField, confirmBtn);
	    return layout;
	}
}
