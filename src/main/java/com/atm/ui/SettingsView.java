package com.atm.ui;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SettingsView extends BaseView {

    public SettingsView(App app) {
        super(app);
    }

    @Override
    public Parent getRoot() {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(30));
        
        Label title = new Label("Settings");
        title.setStyle("-fx-font-family: 'Inter'; "
        		+ "-fx-font-weight: bold; "
        		+ "-fx-font-size: 40px;");
        
        HBox nameSetting = new HBox(20);
        Label nameLabel = new Label("Name: " + app.getCurrentAccount().getName());
        nameLabel.setStyle("-fx-font-family: 'Inter'; "
        		+ "-fx-font-weight: bold; "
        		+ "-fx-font-size: 24px;");
        
        Button resetName = new Button("Reset Name");
        resetName.setOnAction(e -> {
            app.getMainLayout().setCenter(app.getPinView().getResetView("name"));
        });
        nameSetting.getChildren().addAll(nameLabel, resetName);
 
        HBox pinSetting = new HBox(20);
        Label pinLabel = new Label("Pin: **********");
        pinLabel.setStyle("-fx-font-family: 'Inter'; "
        		+ "-fx-font-weight: bold; "
        		+ "-fx-font-size: 24px;");
        
        Button resetPin = new Button("Reset Pin");
        resetPin.setOnAction(e -> {
            app.getMainLayout().setCenter(app.getPinView().getResetView("pin"));
        });
        pinSetting.getChildren().addAll(pinLabel, resetPin);

        Button backBtn = new Button("Return to Dashboard");
        backBtn.setOnAction(e -> app.showDashboard());
        
        layout.getChildren().addAll(title, nameSetting, pinSetting, backBtn);
        return layout;
    }
}