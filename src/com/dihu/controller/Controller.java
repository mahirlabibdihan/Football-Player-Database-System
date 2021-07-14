package com.dihu.controller;

import com.dihu.client.Client;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Controller {
    protected  Client client;
    public void setClient(Client client) {
        this.client = client;
    }
    private double x,y;
    @FXML
    public void drag(MouseEvent mouseEvent) {
        Stage stage=(Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.setX(mouseEvent.getScreenX()-x);
        stage.setY(mouseEvent.getScreenY()-y);
    }

    @FXML
    public void press(MouseEvent mouseEvent) {
        x = mouseEvent.getSceneX();
        y = mouseEvent.getSceneY();
    }

    @FXML
    public void close(MouseEvent mouseEvent) {
        Stage stage=(Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.close();
        Platform.exit();
        System.exit(0);
    }

    @FXML
    public void minimize(MouseEvent mouseEvent) {
        Stage stage=(Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
}
