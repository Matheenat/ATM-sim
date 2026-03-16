package com.atm.ui;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class ShowSuccess extends BaseView{
	private String id;
	public ShowSuccess(App app, String id) {
		super(app);
		this.id = id;
	}
	@Override
	public Parent getRoot() {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
	    grid.setVgap(10);
	    
	    Label success = new Label("Account Created successfully!");
	    success.setStyle("-fx-text-fill: green;");
	    Label userid = new Label("Your Account id is \"" + id + "\"");
	    userid.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
	    Button confirm = new Button("Confirm");
	    confirm.setOnAction(e -> app.showLogin());
	    
	    grid.add(success, 0, 0);
	    grid.add(userid, 0, 1);
	    grid.add(confirm, 0, 2);
	    return grid;
	}
}
