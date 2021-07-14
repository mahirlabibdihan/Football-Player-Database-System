package com.dihu.controller;

import com.dihu.classes.Player;
import com.dihu.client.Client;
import com.dihu.server.Server;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Auction extends Controller{
    @FXML
    private AnchorPane playerListPane;
    private Server server;
    private List<Player> onSell;
    private List<Label> labels;

    public Auction(){
        labels = new ArrayList<Label>();
    }
    public void printOnSell(){
        float height= 45;
        playerListPane.setPrefHeight(onSell.size()*(height+20)+20);
        for(int i=0;i<onSell.size();i++){
            Button b = new Button(onSell.get(i).getName());


            AnchorPane.setLeftAnchor(b, 60.0);
            AnchorPane.setRightAnchor(b, 40.0);
            AnchorPane.setTopAnchor(b, 20.0 + i*(height+20));
            b.setPrefHeight(height);
            b.setId("player_button"+i);
            playerListPane.getChildren().add(b);
        }
    }

    public void setServer(Server server) {
        this.server = server;
        onSell= server.getDatabase().getOnSell();
        printOnSell();
    }
}
