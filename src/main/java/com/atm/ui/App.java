package com.atm.ui;

import javafx.application.Application;
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
	private int screenWidth = 1200;
	private int screenHeight = 800;
	private int smallscreenWidth = 300;
	private int smallscreenHeight = 400;
	private AccountUpdateView updateView = new AccountUpdateView(this);
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
	
	public void logout() {
		this.currentAccount = null;
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
		DashboardView dashboard = new DashboardView(this);
	  
	    Scene dashBoardScene = new Scene(dashboard.getRoot(), screenWidth, screenHeight);

	    window.setScene(dashBoardScene);
	    window.show();
	    window.centerOnScreen();

	    this.mainLayout = (BorderPane) dashBoardScene.getRoot();
	}
	
	public PieChart getPieChart() {
	    return this.pieChart;
	}
	
	public void calculateCashFlow() {
		if(currentAccount == null) {
			return;
		}
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
	public void balanceView() {
		mainLayout.setCenter(new BalanceView(this).getRoot());
	}
	
	public void  withdrawView() {
		mainLayout.setCenter(new WithdrawView(this).getRoot());
	}
	
	public void depositView() {
		mainLayout.setCenter(new DepositView(this).getRoot());
	}
	
	public void transferView() {
		mainLayout.setCenter(new TransferView(this).getRoot());
	}
	
	public void settingsView() {
		mainLayout.setCenter(new SettingsView(this).getRoot());
	}
	
	void resetManagerView(String scene) {
	    if (scene.equals("name")) {
	        mainLayout.setCenter(updateView.getResetNameView());
	    } else if (scene.equals("pin")) {
	        mainLayout.setCenter(updateView.getResetPinView());
	    }
	}
	
}
