package com.dihu.controller;

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

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddPlayer extends Controller {
    private List<Player> playerList;
    private Map<String,Player> buttons;
    @FXML
    private AnchorPane onSellPane;

    public AddPlayer() {
        buttons = new HashMap<>();
    }

    public void printPlayers(List<Player> onSell){
        for(int i=0;i<onSell.size();i++){
            System.out.println("Print Player");
            Button b = new Button(onSell.get(i).getName());

            b.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("AuctionPlayerDetails.fxml"));
                        Player p = buttons.get(((Button)e.getSource()).getId());
                        Player.player = p;
                        Parent root = loader.load();
                        AuctionPlayerDetails controller = loader.getController();
                        controller.setClient(client);
                        ((Button)e.getSource()).getScene().setRoot(root);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });


            float height= Math.min((float) (600.0/onSell.size()),50);
            AnchorPane.setLeftAnchor(b, 20.0);
            AnchorPane.setRightAnchor(b, 20.0);
            AnchorPane.setTopAnchor(b, 20.0 + i*(1.2*height));
            b.setPrefHeight(height);

            b.setMinHeight(20);
            b.setId("player_button"+i);
            buttons.put(b.getId(),onSell.get(i));
            b.setStyle("-fx-font-size: "+Math.min(22,height-10)+"px;");
            onSellPane.getChildren().add(b);
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
            System.out.println(playerList.size());
            printPlayers(playerList);
        }catch(Exception e){
            System.err.println(e);
        }
    }

    public void buy(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Scene/AuctionPlayerList.fxml"));
        Parent root = loader.load();

        Controller controller = (Controller)loader.getController();
        controller.setClient(client);

        ((Button)actionEvent.getSource()).getScene().setRoot(root);
    }

    public void sell(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Scene/PlayersList.fxml"));
        Parent root = loader.load();

        Controller controller = loader.getController();
        controller.setClient(client);

        ((Button)actionEvent.getSource()).getScene().setRoot(root);
    }
}
