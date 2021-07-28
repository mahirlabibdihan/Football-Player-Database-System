package com.dihu.client.fx.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import java.util.HashMap;
import java.util.Map;

public class ClubMenu extends Controller {
    private SpinnerValueFactory<String> filterValueFactory;

    @FXML
    public Label errorLabel;

    @FXML
    private Spinner clubSpinner;

    @FXML
    private AnchorPane anchorPane;

    private void showTotalYearlySalary() {
        double total = client.getClub().getTotalYearlySalary();
        Label l = new Label(String.format("%f", total));
        l.setId("salary-label");

        ImageView dollar = new ImageView();
        dollar.setImage(new Image(getClass().getResource("../assets/images/Dollar.png").toExternalForm()));
        dollar.setFitWidth(60);
        dollar.setPreserveRatio(true);

        HBox row = new HBox(dollar, l);
        row.setAlignment(Pos.CENTER_LEFT);
        AnchorPane.setLeftAnchor(row, 40.0);
        AnchorPane.setTopAnchor(row, 20.0);
        anchorPane.getChildren().add(row);
    }

    private void showCountryWisePlayerCount() {
        Map<String, Integer> count = new HashMap<>();
        client.getClub().getCountryWisePlayerCount(count);
        if (count.size() > 0) {
            VBox list = new VBox();
            AnchorPane.setLeftAnchor(list, 60.0);
            list.setSpacing(20);
            for (Map.Entry<String, Integer> m : count.entrySet()) {
                Label l1 = new Label(m.getKey());
                l1.setId("country-label");

                Label l2 = new Label(Integer.toString(m.getValue()));
                l2.setId("player-count-label");

                ImageView country = new ImageView();
                country.setImage(new Image(getClass().getResource("../assets/images/Country/" + m.getKey() + ".png").toExternalForm()));
                country.setFitWidth(50);
                country.setPreserveRatio(true);

                HBox row = new HBox(country, l1, l2);
                row.setAlignment(Pos.CENTER_LEFT);
                row.setSpacing(15);
                list.getChildren().add(row);
            }
            anchorPane.getChildren().add(list);
        } else {
            System.out.println("No player in the database");
        }
    }

    private void showChangePasswordMenu()  {
        anchorPane.getChildren().clear();
        errorLabel.setText("");

        PasswordField currentPassword = new PasswordField();
        currentPassword.setPromptText("Current password");
        currentPassword.getStyleClass().add("text-field-1");

        PasswordField newPassword = new PasswordField();
        newPassword.setPromptText("New password");
        newPassword.getStyleClass().add("text-field-1");

        PasswordField confirmNewPassword = new PasswordField();
        confirmNewPassword.setPromptText("Retype new password");
        confirmNewPassword.getStyleClass().add("text-field-1");

        // Checking retyped password
        confirmNewPassword.textProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<? extends Object> observableValue, Object o, Object t1) {
                if(newPassword.getText().equals(confirmNewPassword.getText())) {
                    errorLabel.setText("");
                }else{
                    errorLabel.setTextFill(Paint.valueOf("#FF0000"));
                    errorLabel.setText("Passwords do not match");
                }
            }
        });

        JFXButton resetButton = new JFXButton("RESET");
        resetButton.getStyleClass().add("btn-2");
        resetButton.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> hoverSound(null));
        resetButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent actionEvent) {
                clickSound(null);
                showChangePasswordMenu();
            }
        });

        JFXButton changeButton = new JFXButton("SAVE");
        changeButton.getStyleClass().add("btn-2");
        changeButton.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> hoverSound(null));
        changeButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    // Validating inputs
                    if(client.getClub().getPassword().equals(currentPassword.getText())) {
                        if(newPassword.getText().equals(confirmNewPassword.getText())){
                            clickSound(null);
                            client.getNetworkUtil().write(newPassword.getText());   // Setting password
                            currentPassword.setText("");
                            newPassword.setText("");
                            confirmNewPassword.setText("");
                            errorLabel.setTextFill(Paint.valueOf("#00FF00"));
                            errorLabel.setText("Password changed successfully");
                        }
                    }else{
                        errorLabel.setTextFill(Paint.valueOf("#FF0000"));
                        errorLabel.setText("Incorrect password");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        HBox row = new HBox(resetButton,changeButton);
        row.setSpacing(20);

        VBox list = new VBox(currentPassword,newPassword,confirmNewPassword,row);
        AnchorPane.setLeftAnchor(list, 58.0);
        AnchorPane.setRightAnchor(list, 40.0);
        list.setSpacing(20);

        anchorPane.getChildren().add(list);
    }

    @FXML
    public void initialize() {
        filterValueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<String>(
                FXCollections.observableArrayList(
                        "",
                        "TOTAL YEARLY SALARY",
                        "COUNTRY WISE PLAYER COUNT",
                        "CHANGE PASSWORD"
                )
        );
        clubSpinner.setValueFactory(filterValueFactory);
        clubSpinner.valueProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<? extends Object> observableValue, Object o, Object t1) {
                clickSound(null);
                String currentFilter = (String) clubSpinner.getValue();
                errorLabel.setText("");
                anchorPane.getChildren().clear();
                if (currentFilter.equals("TOTAL YEARLY SALARY")) {
                    showTotalYearlySalary();
                } else if (currentFilter.equals("COUNTRY WISE PLAYER COUNT")) {
                    showCountryWisePlayerCount();
                } else if(currentFilter.equals("CHANGE PASSWORD")){
                    showChangePasswordMenu();
                }
            }
        });
    }
}
