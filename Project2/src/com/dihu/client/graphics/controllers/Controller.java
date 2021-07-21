package com.dihu.client.graphics.controllers;

import com.dihu.client.Client;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;


public class Controller {
    protected Client client;
    private double x, y;

    public void setClient(Client client) {
        this.client = client;
    }

    @FXML
    public void drag(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setX(mouseEvent.getScreenX() - x);
        stage.setY(mouseEvent.getScreenY() - y);
    }

    @FXML
    public void press(MouseEvent mouseEvent) {
        x = mouseEvent.getSceneX();
        y = mouseEvent.getSceneY();
    }

    @FXML
    public void close(MouseEvent mouseEvent) throws Exception {
        if(!client.getUi().getCurrentScene().getFileName().equals("LoginForm")){
            client.getNetworkUtil().write(client.getClub().getName());
            client.getNetworkUtil().closeConnection();
        }
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
        Platform.exit();
        System.exit(0);
    }

    @FXML
    public void minimize(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        client.getUi().back();
        client.updateScene();
    }

    public void next(ActionEvent actionEvent) throws Exception {
        JFXButton b = (JFXButton) actionEvent.getSource();
        client.getUi().next(Integer.parseInt(b.getId()));
        client.updateScene();
    }

    public void init() {

    }
}
