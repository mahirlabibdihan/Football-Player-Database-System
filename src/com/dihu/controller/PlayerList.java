package com.dihu.controller;

import com.dihu.classes.Club;
import com.dihu.client.Client;
import com.dihu.classes.Player;
import com.dihu.controller.Controller;
import com.dihu.controller.PlayerDetails;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayerList extends Controller {
    private List<Player> playerList;
    private List<Button> buttons;
    @FXML
    private AnchorPane playerListPane;

    public PlayerList() {
        buttons = new ArrayList<>();
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
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Scene/PlayerSell.fxml"));
                        Parent root = loader.load();
                        Player p = client.getClub().searchPlayerByName(((Button)e.getSource()).getText());
                        Player.player = p;
                        PlayerSell controller = (PlayerSell)loader.getController();
                        controller.setClient(client);
                        System.out.println("Name: "+((Button)e.getSource()).getText());
                        controller.setPlayerName(p.getName());
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
            buttons.add(b);
            b.setId("player-button");
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
        Club c = client.getClub();
        try{
            playerList= c.getPlayerList();
            printPlayers(playerList);
        }catch(Exception e){
            System.err.println(e);
        }
    }
}
