package com.dihu.controller;

import com.dihu.classes.Club;
import com.dihu.classes.Player;
import com.dihu.client.Client;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuctionPlayerList extends Controller{
    private List<Player> playerList;
    @FXML
    private AnchorPane playerListPane;
    private Map<String,Player> buttons;

    public AuctionPlayerList() {
        buttons = new HashMap<>();
    }

    public void printPlayers(List<Player> searchedPlayers){
        float height= 45;
        playerListPane.setPrefHeight(searchedPlayers.size()*(height+20)+20);
        for(int i=0;i<searchedPlayers.size();i++){
            Button b = new Button(searchedPlayers.get(i).getName());

            b.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    try {
                        Player p = buttons.get(((Button)e.getSource()).getId());
                        client.getNetworkUtil().write(new Pair<>(client.getClub(),p));
                        p.setClub(client.getClub().getName());
                        client.getClub().addPlayer(p);
                        System.out.println("Send");
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Scene/MainMenu.fxml"));
                        Parent root = loader.load();
                        Controller controller = loader.getController();
                        controller.setClient(client);
                        client.getScene().setRoot(root);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });


            AnchorPane.setLeftAnchor(b, 60.0);
            AnchorPane.setRightAnchor(b, 40.0);
            AnchorPane.setTopAnchor(b, 20.0 + i*(height+20));
            b.setPrefHeight(height);
            b.setId("player_button"+i);
            buttons.put(b.getId(),searchedPlayers.get(i));
            playerListPane.getChildren().add(b);
        }
    }

    @FXML
    public void back(MouseEvent mouseEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Scene/MainMenu.fxml"));
        Parent root = loader.load();

        Controller controller = (Controller)loader.getController();
        controller.setClient(client);

        ((Node)mouseEvent.getSource()).getScene().setRoot(root);
    }

    @Override
    public void setClient(Client client) {
        this.client = client;
        try{
            playerList= client.getOnSell();
            printPlayers(playerList);
        }catch(Exception e){
            System.err.println(e);
        }
    }
}
