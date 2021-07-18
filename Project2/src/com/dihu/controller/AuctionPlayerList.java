package com.dihu.controller;

import com.dihu.classes.Player;
import com.dihu.client.Client;
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
import javafx.util.Pair;

import java.io.IOException;
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

    public Player searchPlayerByName(List<Player> list,String name) {
        for (Player p : list) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }

    public void printPlayers(List<Player> searchedPlayers){
        float height= 45;

        playerListPane.setPrefHeight(searchedPlayers.size()*(height+20)+20);
        VBox list = new VBox();
        AnchorPane.setLeftAnchor(list, 5.0);
        AnchorPane.setRightAnchor(list, 5.0);
        list.setSpacing(20);

        int i=0;
        for(Player p:searchedPlayers){
            if(p.getClub().equals(client.getClub().getName())) continue;

            Button playerButton = new Button(p.getName());
            Button buyButton = new Button(String.format("%.1f",p.getPrice())+"$");

            buyButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    try {
                        Player p = buttons.get(((Button)e.getSource()).getId());
                        client.getNetworkUtil().write(new Pair<>(client.getClub().getName(),p));
                        p.setClub(client.getClub().getName());
                        client.getClub().addPlayer(p);
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

            playerButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Scene/PlayerDetails.fxml"));
                        Player p = searchPlayerByName(client.getOnSell(),((Button)e.getSource()).getText());
                        Player.player = p;
                        Parent root = loader.load();
                        PlayerDetails controller = loader.getController();
                        controller.setClient(client);
                        controller.setPrevFXML("../Scene/AuctionPlayerList.fxml");
                        ((Button)e.getSource()).getScene().setRoot(root);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });

            buyButton.setPrefWidth(110);
            buyButton.setId("player_button"+i);
            buyButton.setStyle(     "-fx-border-radius: 0 50 50 0;"+
                                    "-fx-border-color: #2ecc71;"+
                                    "-fx-border-width: 2;"
                                        );
            buttons.put(buyButton.getId(),searchedPlayers.get(i));
            buyButton.addEventHandler(MouseEvent.MOUSE_ENTERED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            buyButton.setText("BUY");
                            buyButton.setStyle(
                                    "-fx-background-radius: 0 50 50 0;"+
                                    "-fx-background-color: #2ecc71;"
                            );
                        }
                    });

            buyButton.addEventHandler(MouseEvent.MOUSE_EXITED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            buyButton.setText(String.format("%.1f",p.getPrice())+"$");
                            buyButton.setStyle(
                                    "-fx-border-radius: 0 50 50 0;"+
                                    "-fx-border-color: #2ecc71;"+
                                    "-fx-border-width: 2;"
                            );
                        }
                    });


            playerButton.setId("buyPlayer");
            playerButton.setPrefWidth(190);
            playerButton.setStyle(  "-fx-border-width: 2;"+
                                    "-fx-border-radius: 50 0 0 50;"+
                                    "-fx-border-color: #2ecc71;");

            ImageView country = new ImageView();
            country.setImage(new Image(getClass().getResource("../images/Player/"+p.getName()+".png").toExternalForm()));
            country.setFitHeight(45);
            country.setPreserveRatio(true);

            HBox row = new HBox(country,playerButton,buyButton);
            row.setPrefHeight(45);
            row.setAlignment(Pos.CENTER_LEFT);
            row.setSpacing(10);

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
        client.setFileName("AuctionPlayerList.fxml");
        try{
            playerList= client.getOnSell();
            printPlayers(playerList);
        }catch(Exception e){
            System.err.println(e);
        }
    }
}
