package com.dihu.client.graphics.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class AuctionPlayerList extends Controller {
    @FXML
    private AnchorPane playerListPane;

    @Override
    public void init() {
        client.getPlayerListHandler().setPlayerList(client.getPlayerListHandler().getAuctionPlayerList());
        playerListPane.getChildren().add(client.getPlayerListHandler().getPlayerListUi());
    }
}
