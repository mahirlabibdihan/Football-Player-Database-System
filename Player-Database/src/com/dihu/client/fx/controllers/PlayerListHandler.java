package com.dihu.client.fx.controllers;

import com.dihu.util.Player;
import com.dihu.client.Client;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class PlayerListHandler extends Controller implements EventHandler<ActionEvent> {
    private List<Player> playerList, auctionPlayerList;

    public PlayerListHandler(Client client) {
        this.client = client;
    }

    public VBox getPlayerListUi() {
        VBox list = new VBox();
        AnchorPane.setLeftAnchor(list, 10.0);
        list.setSpacing(20);
        for (Player p : playerList) {
            ImageView country = new ImageView();
            country.setImage(new Image(getClass().getResource("../assets/images/face/" + p.getName() + ".png").toExternalForm()));
            country.setFitHeight(45);
            country.setPreserveRatio(true);

            JFXButton playerButton = new JFXButton(p.getName());
            playerButton.getStyleClass().add("btn-1");
            playerButton.setOnAction(this);

            HBox row = new HBox(country, playerButton);
            row.setPrefHeight(45);
            row.setAlignment(Pos.CENTER_LEFT);
            row.setSpacing(5);
            list.getChildren().add(row);
        }
        return list;
    }

    @Override
    public void handle(ActionEvent event) {
        try {
            client.getUi().next();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../scenes/" + client.getUi().getCurrentScene().getFileName() + ".fxml"));
            Parent root = loader.load();
            PlayerCard controller = loader.getController();
            Player p = searchPlayerByName(((JFXButton) event.getSource()).getText());
            controller.setClient(client);
            controller.setPlayer(p);
            controller.setPrice(p.getPrice());
            client.getScene().setRoot(root);
            root.requestFocus();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Player searchPlayerByName(String name) {
        for (Player p : playerList) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    public List<Player> getAuctionPlayerList() {
        return auctionPlayerList;
    }

    public void setAuctionPlayerList(List<Player> auctionPlayerList) {
        System.out.println("Auction player list set");
        this.auctionPlayerList = auctionPlayerList;
    }

}
