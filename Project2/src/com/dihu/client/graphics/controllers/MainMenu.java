package com.dihu.client.graphics.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MainMenu extends Controller {
    @FXML
    private ImageView badge;

    public void logOut(ActionEvent actionEvent) throws Exception {
        client.getNetworkUtil().closeConnection();
        super.back(null);
    }

    @Override
    public void init() {
        badge.setImage(new Image(getClass().getResource("../assets/images/Club/"+client.getClub().getName()+".png").toExternalForm()));
    }
}
