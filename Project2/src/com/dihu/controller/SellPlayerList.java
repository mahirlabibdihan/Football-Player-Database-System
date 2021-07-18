package com.dihu.controller;

import com.dihu.classes.Club;
import com.dihu.client.Client;
import com.dihu.classes.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SellPlayerList extends Controller {
    private List<Player> playerList;
    @FXML
    private AnchorPane playerListPane;

    public void printPlayers(List<Player> searchedPlayers){
        VBox list = new VBox();
        AnchorPane.setLeftAnchor(list, 5.0);
        list.setSpacing(20);
        for(Player p:searchedPlayers){
            Button b = new Button(p.getName());

            b.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    try {
                        Player p = client.getClub().searchPlayerByName(((Button)e.getSource()).getText());
                        Player.player = p;
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../scene/SellMenu.fxml"));
                        Parent root = loader.load();
                        PlayerDetails controller = (PlayerDetails)loader.getController();
                        controller.setClient(client);
                        controller.setPrevFXML("../Scene/SellPlayerList.fxml");
                        client.getScene().setRoot(root);
                        root.requestFocus();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });


            //b.setPrefWidth(280);
            b.setId("sellPlayerButton");

            ImageView country = new ImageView();
            country.setImage(new Image(getClass().getResource("../images/Player/"+p.getName()+".png").toExternalForm()));
            country.setFitHeight(50);
            country.setPreserveRatio(true);

            HBox row = new HBox(country,b);
            row.setPrefHeight(45);
            row.setAlignment(Pos.CENTER_LEFT);
            row.setSpacing(5);

            list.getChildren().add(row);
        }
        playerListPane.getChildren().add(list);
    }

    @FXML
    public void back(MouseEvent mouseEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../scene/TransferMenu.fxml"));
        Parent root = loader.load();

        Controller controller = (Controller)loader.getController();
        controller.setClient(client);

        ((Node)mouseEvent.getSource()).getScene().setRoot(root);
    }

    @Override
    public void setClient(Client client) {
        this.client = client;
        client.setFileName("SellPlayerList.fxml");
        Club c = client.getClub();
        try{
            playerList= c.getPlayerList();
            printPlayers(playerList);
        }catch(Exception e){
            System.err.println(e);
        }
    }
}
