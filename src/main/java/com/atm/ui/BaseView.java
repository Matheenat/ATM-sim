package com.atm.ui;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;

public abstract class BaseView {
	protected App app;
	public BaseView(App app) {
		this.app = app;
	}
	protected GridPane createBaseGrid() {
		GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        return grid;
	}
	public abstract Parent getRoot();
}
