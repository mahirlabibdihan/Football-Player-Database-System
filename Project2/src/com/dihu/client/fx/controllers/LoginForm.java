package com.dihu.client.fx.controllers;

import com.dihu.util.LoginDTO;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginForm extends Controller {
    @FXML
    private Label errorLabel;

    @FXML
    private TextField clubNameEntry, passwordEntry;

    @FXML
    public void login(ActionEvent actionEvent) {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setClubName(clubNameEntry.getText());
        loginDTO.setPassword(passwordEntry.getText());

        try {
            client.connectToServer();
            client.getNetworkUtil().write(loginDTO);
        } catch (Exception e) {
            errorLabel.setText("Can't connect to server");
        }
    }

    @FXML
    public void exit(ActionEvent actionEvent) {
        Stage stage = (Stage) ((JFXButton) actionEvent.getSource()).getScene().getWindow();
        stage.close();
        Platform.exit();
        System.exit(0);
    }

}
