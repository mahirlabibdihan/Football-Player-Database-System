package com.dihu.client.fx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MainMenu extends Controller {
    @FXML
    private ImageView badge;

    public void logOut(ActionEvent actionEvent){
        clickSound(null);
        try{
            client.getNetworkUtil().closeConnection();
            super.back(null);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void init() {
        badge.setImage(new Image(getClass().getResource("../assets/images/Club/" + client.getClub().getName() + ".png").toExternalForm()));
    }
}
