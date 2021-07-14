package com.dihu.controller;

import com.dihu.controller.Controller;
import com.dihu.util.LoginDTO;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginForm extends Controller {

    @FXML
    private TextField clubNameEntry;

    @FXML
    private TextField passwordEntry;

    @FXML
    private Button loginButton;

    @FXML
    public void login(ActionEvent actionEvent) throws IOException {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setClubName(clubNameEntry.getText());
        loginDTO.setPassword(passwordEntry.getText());

        try {
            client.connectToServer();
            client.getNetworkUtil().write(loginDTO);
        } catch (Exception e) {
            client.getNetworkUtil().closeConnection();
            e.printStackTrace();
        }
    }

    public void exit(ActionEvent actionEvent) {
        Stage stage=(Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        stage.close();
        Platform.exit();
        System.exit(0);
    }
}
