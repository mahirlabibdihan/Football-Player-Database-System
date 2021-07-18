package com.dihu.controller;

import com.dihu.classes.Player;
import com.dihu.client.Client;
import com.dihu.controller.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;

import java.io.IOException;

public class SellMenu extends Controller {
    Player player;
    @FXML
    private Label errorLabel;
    @FXML
    private Label player_name;

    @FXML
    private TextField price_entry;

    @FXML
    private Button sell_button;

    public SellMenu(){

    }
    public void sell(ActionEvent actionEvent){
        try {
            player.setPrice(Double.parseDouble(price_entry.getText()));
            System.out.println(player.getPrice());
            client.getNetworkUtil().write(new Pair<>(client.getClub().getName(),player));
            client.getClub().removePlayer(client.getClub().searchPlayerByName(player.getName()));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Scene/PlayersList.fxml"));
            Parent root = loader.load();
            Controller controller = loader.getController();
            controller.setClient(client);
            client.getScene().setRoot(root);
        } catch (Exception e) {
            errorLabel.setText("Invalid input");
        }
    }

    @FXML
    public void back(ActionEvent mouseEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Scene/MainMenu.fxml"));
        Parent root = loader.load();

        Controller controller = (Controller)loader.getController();
        controller.setClient(client);

        ((Button)mouseEvent.getSource()).getScene().setRoot(root);
    }

    public void setPlayerName(String name){
        player_name.setText(name);
        player = Player.player;
    }

    @Override
    public void setClient(Client client){
        this.client = client;
        client.setFileName("SellMenu.fxml");
    }
}
