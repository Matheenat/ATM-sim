package com.atm.ui;

import java.util.List;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import com.atm.logic.*;
import com.atm.data.*;
import com.atm.util.FontsLoader;

public class App extends Application{
	private Stage window;
	private Account currentAccount;
	private int screenWidth = 1000;
	private int screenHeight = 700;
	private int smallscreenWidth = 300;
	private int smallscreenHeight = 400;
	private PinConfirmationView pinConfirmationView = new PinConfirmationView(this);
	public PinConfirmationView getPinView() {
		return pinConfirmationView;
	}
	
	public TransactionService txService;
	private PieChart pieChart = new PieChart();
	private BorderPane mainLayout;
	public BorderPane getMainLayout() {
		return this.mainLayout;
	}
	private Bank myBank = new Bank();
	public Bank getBank() {
		return myBank;
	}
	FileHandler fh = new FileHandler();
	public FileHandler getFH() {
		return fh;
	}
	public void setCurrentAccount(Account currentAccount) {
		this.currentAccount = currentAccount;
	}
	public Account getCurrentAccount() {
		return this.currentAccount;
	}
	@Override
    public void start(Stage primaryStage) {
		FontsLoader.loadFonts();
		this.window = primaryStage;
		fh.loadAccounts(myBank);
		
        StackPane root = new StackPane();
        Scene scene = new Scene(root, screenWidth, screenHeight);

        primaryStage.setTitle("Atm-Sim");
        primaryStage.setScene(scene);
        this.txService = new TransactionService(this);
        showLogin();
    }
	
	public void showLogin() {
		LoginView loginview = new LoginView(this);
		Scene loginScene = new Scene(loginview.getRoot(), smallscreenWidth, smallscreenHeight);
	    window.setScene(loginScene);
	    window.show();
	}
	
	public void showSignup() {
		SignupView signupview = new SignupView(this);
	    Scene signupScene = new Scene(signupview.getRoot(), smallscreenWidth, smallscreenHeight);
	    window.setScene(signupScene);
	    window.show();
	}
	
	public void showSuccess(String id) {
		ShowSuccess showsuccess = new ShowSuccess(this, id);
	    Scene successScene = new Scene(showsuccess.getRoot(), smallscreenWidth, smallscreenHeight);
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
	    		+ "-fx-border-color: #93b4c2; "
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
	    	withdrawView();
	    });
	    depositBtn.setOnAction(e -> {
	    	depositView();
	    });
	    transferBtn.setOnAction(e -> {
	    	transferView();
	    });
	    settingsBtn.setOnAction(e -> {
	    	mainLayout.setCenter(settingsView());
	    });
	    
	    mainLayout.setLeft(leftBar);

	    VBox rightBar = new VBox(20);
	    rightBar.setPadding(new Insets(10));
	    rightBar.setMinWidth(250);
	    rightBar.setStyle("-fx-background-color: #E6F0F5; "
	    		+ "-fx-border-color: #AACDDC; "
	    		+ "-fx-border-width: 0 0 0 1;");
	    ScrollPane scrollHistory = new ScrollPane(rightBar);
	    scrollHistory.setFitToWidth(true);
	    scrollHistory.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
	    
	    rightBar.getChildren().add(new Label("Account Summary"));
	    
	    List<String> history = currentAccount.getTransactionHistory();
	    for(int i = history.size() - 1; i >= 0; i--) {
	    	String line = history.get(i);
	    	Label currentLine = new Label(line);
	    	currentLine.setStyle("-fx-background-color: white; -fx-padding: 10; "
                    + "-fx-background-radius: 5; -fx-border-color: #DDD;");
	    	currentLine.setMaxWidth(Double.MAX_VALUE);
	    	rightBar.getChildren().add(currentLine);
	    }
	    
	    mainLayout.setRight(scrollHistory);
	    
	    
	    GridPane centerGrid = new GridPane();
	    centerGrid.setHgap(20);
	    centerGrid.setPadding(new Insets(20));
	    centerGrid.setAlignment(Pos.CENTER);
	    
	    centerGrid.add(pieChart, 0, 0);
	    calculateCashFlow();
	    
	    mainLayout.setCenter(centerGrid);
	    
	    Scene dashBoardScene = new Scene(mainLayout, screenWidth, screenHeight);
	    window.setScene(dashBoardScene);
	    window.show();
	    window.centerOnScreen();
	}
	private void calculateCashFlow() {
		CashFlowData data = new CashFlowData(currentAccount);
		updateDashboardChart(data.getTotalWithdraw(), data.getTotalDeposit(), data.getTotalTransfer(), data.getTotalReceived());
	}
	private void updateDashboardChart(Double withdraw, Double deposit, Double transfer, Double receive) {
		Double totalIn = deposit + receive;
		Double totalOut = withdraw + transfer;
		
		PieChart.Data inSlice = new PieChart.Data("Money in", totalIn);
		PieChart.Data outSlice = new PieChart.Data("Money out", totalOut);
		
		pieChart.getData().clear();
	    pieChart.getData().addAll(inSlice, outSlice);
	    
	    setupToolTips(withdraw, deposit, transfer, receive, inSlice, outSlice);
	}
	private void setupToolTips(Double withdraw, Double deposit, Double transfer, Double receive, PieChart.Data inSlice, PieChart.Data outSlice) {
		String inInfo = String.format("Deposits: $%.2f\nReceived: $%.2f", deposit, receive);
	    applyToolTip(inSlice, inInfo);

	    String outInfo = String.format("Withdrawals: $%.2f\nTransfers: $%.2f", withdraw, transfer);
	    applyToolTip(outSlice, outInfo);
	}
	private void applyToolTip(PieChart.Data data, String message) {
		Node node = data.getNode();
	    Tooltip tt = new Tooltip(message);
	   
	    tt.setShowDelay(javafx.util.Duration.ZERO);
	    
	    Tooltip.install(node, tt);

	    node.setOnMouseEntered(e -> node.setOpacity(0.8));
	    node.setOnMouseExited(e -> node.setOpacity(1.0));
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
	private void  withdrawView() {
		mainLayout.setCenter(new WithdrawView(this).getRoot());
	}
	
	private void depositView() {
		mainLayout.setCenter(new DepositView(this).getRoot());
	}
	
	private void transferView() {
		mainLayout.setCenter(new TransferView(this).getRoot());
	}
	
	private Node settingsView() {
		VBox layout = new VBox(20);
		HBox nameSetting = new HBox(20);
		HBox pinSetting = new HBox(20);
	    layout.setPadding(new Insets(30));
	    
	    Label title = new Label("Settings");
	    title.setStyle("-fx-font-family: 'Inter'; "
	    		+ "-fx-font-weight: bold;"
	    		+ "-fx-font-size: 40px;");
	    
	    Label nameLabel = new Label("Name: " + currentAccount.getName());
	    nameLabel.setStyle("-fx-font-family: 'Inter'; "
	    		+ "-fx-font-weight: bold;"
	    		+ "-fx-font-size: 24px;");
	    Button resetName = new Button("Reset Name");
	    resetName.setOnAction(e -> {
	    	mainLayout.setCenter(pinConfirmationView.getResetView("name"));
	    });
	    nameSetting.getChildren().addAll(nameLabel, resetName);
	    
	    Label pinLabel = new Label("Pin: **********");
	    pinLabel.setStyle("-fx-font-family: 'Inter'; "
	    		+ "-fx-font-weight: bold;"
	    		+ "-fx-font-size: 24px;");
	    Button resetPin = new Button("Reset Pin");
	    resetPin.setOnAction(e -> {
	    	mainLayout.setCenter(pinConfirmationView.getResetView("pin"));
	    });
	    pinSetting.getChildren().addAll(pinLabel, resetPin);
	    Button backBtn = new Button("Return to Dashboard");
	    backBtn.setOnAction(e -> showDashboard());
	    layout.getChildren().addAll(title, nameSetting, pinSetting, backBtn);
	    return layout;
	}
	
	void resetManagerView(String scene) {
		if(scene.equals("name")) {
			mainLayout.setCenter(resetNameView());
		}
		if(scene.equals("pin")) {
			mainLayout.setCenter(resetPinView());
		}
	}
	private Node resetNameView() {
		VBox layout = new VBox(20);
		layout.setPadding(new Insets(50));
		layout.setAlignment(Pos.CENTER);
		
		layout.setStyle("-fx-background-color: #FFFFFF; "
	    		+ "-fx-background-radius: 20; "
	    		+ "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 10);");
	    layout.setMaxSize(500, 300);
	    
	    Label statusLabel = new Label("");
	    Label titleLabel = new Label("Input new name");
	    titleLabel.setStyle("-fx-font-family: 'Inter'; "
	    		+ "-fx-font-size: 18px; "
	    		+ "-fx-text-fill: #555555;");
	    
		TextField newNameField = new TextField();
		newNameField.setPromptText("Input new name...");
		
		Button confirm = new Button("Confirm");
		confirm.setOnAction(e -> {
			String newName = newNameField.getText().trim();
			if(newName.isEmpty()) {
				statusLabel.setText("Error: Your new name cannot be empty");
	            statusLabel.setStyle("-fx-text-fill: red;");
	            return;
			}
			if(newName.equals(currentAccount.getName())){
				statusLabel.setText("Error: Your new name cannot be same as the current one");
	            statusLabel.setStyle("-fx-text-fill: red;");
	            return;
			}
			if(newName.length() > 20) {
				statusLabel.setText("Error: Your new name cannot be longer than 20 characters");
	            statusLabel.setStyle("-fx-text-fill: red;");
	            return;
			}
			currentAccount.setName(newName);
			fh.saveAccounts(myBank.getAllAccounts());
			showDashboard();
		});
	    layout.getChildren().addAll(statusLabel, titleLabel, newNameField, confirm);
		return layout;
	}
	
	private Node resetPinView() {
		VBox layout = new VBox(20);
		layout.setPadding(new Insets(50));
		layout.setAlignment(Pos.CENTER);
		
		layout.setStyle("-fx-background-color: #FFFFFF; "
	    		+ "-fx-background-radius: 20; "
	    		+ "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 10);");
	    layout.setMaxSize(500, 300);
	    
	    Label statusLabel = new Label("");
	    Label titleLabel = new Label("Input new pin");
	    titleLabel.setStyle("-fx-font-family: 'Inter'; "
	    		+ "-fx-font-size: 18px; "
	    		+ "-fx-text-fill: #555555;");
	    
		PasswordField newPinField = new PasswordField();
		newPinField.setPromptText("Input new pin...");
		
		Button confirm = new Button("Confirm");
		confirm.setOnAction(e -> {
			String newPin = newPinField.getText().trim();
			if(newPin.isEmpty()) {
				statusLabel.setText("Error: Your new pin cannot be empty");
	            statusLabel.setStyle("-fx-text-fill: red;");
	            return;
			}
			if(newPin.equals(currentAccount.getPin())){
				statusLabel.setText("Error: Your new pin cannot be same as the current one");
	            statusLabel.setStyle("-fx-text-fill: red;");
	            return;
			}
			if(newPin.length() < 6) {
				statusLabel.setText("Error: Your new pin must be atleast 6 characters");
	            statusLabel.setStyle("-fx-text-fill: red;");
	            return;
			}
			mainLayout.setCenter(confirmPinView(newPin));
		});
	    layout.getChildren().addAll(statusLabel, titleLabel, newPinField, confirm);
		return layout;
	}
	
	private Node confirmPinView(String pin) {
		VBox layout = new VBox(20);
		layout.setPadding(new Insets(50));
		layout.setAlignment(Pos.CENTER);
		
		layout.setStyle("-fx-background-color: #FFFFFF; "
	    		+ "-fx-background-radius: 20; "
	    		+ "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 10);");
	    layout.setMaxSize(500, 300);
	    
	    Label statusLabel = new Label("");
	    Label titleLabel = new Label("Confirm your new pin");
	    titleLabel.setStyle("-fx-font-family: 'Inter'; "
	    		+ "-fx-font-size: 18px; "
	    		+ "-fx-text-fill: #555555;");
	    
		PasswordField newPinField = new PasswordField();
		newPinField.setPromptText("Confirm your pin");
		
		Button confirm = new Button("Confirm");
		confirm.setOnAction(e -> {
			String newPin = newPinField.getText().trim();
			if(newPin.isEmpty()) {
				statusLabel.setText("Error: Your new pin cannot be empty");
	            statusLabel.setStyle("-fx-text-fill: red;");
	            return;
			}
			if(newPin.equals(currentAccount.getPin())){
				statusLabel.setText("Error: Your new pin cannot be same as the current one");
	            statusLabel.setStyle("-fx-text-fill: red;");
	            return;
			}
			if(newPin.length() < 6) {
				statusLabel.setText("Error: Your new pin must be atleast 6 characters");
	            statusLabel.setStyle("-fx-text-fill: red;");
	            return;
			}
			if(!newPin.equals(pin)) {
				statusLabel.setText("Error: Please type your new pin again");
	            statusLabel.setStyle("-fx-text-fill: red;");
	            return;
			}
			currentAccount.setPin(newPin);
			fh.saveAccounts(myBank.getAllAccounts());
			showDashboard();
		});
	    layout.getChildren().addAll(statusLabel, titleLabel, newPinField, confirm);
		return layout;
	}
}
