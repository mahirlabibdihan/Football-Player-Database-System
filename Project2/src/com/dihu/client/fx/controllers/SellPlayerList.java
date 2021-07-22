package com.dihu.client.fx.controllers;

import com.dihu.util.Club;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class SellPlayerList extends Controller {
    @FXML
    private AnchorPane playerListPane;

    @Override
    public void init() {
        Club c = client.getClub();
        client.getPlayerListHandler().setPlayerList(c.getPlayerList());
        playerListPane.getChildren().add(client.getPlayerListHandler().getPlayerListUi());
    }
}
