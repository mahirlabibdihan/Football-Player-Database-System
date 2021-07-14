package com.dihu.controller;

import com.dihu.classes.Player;
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

public class PlayerSell extends Controller {
    Player player;
    @FXML
    private Label player_name;

    @FXML
    private TextField price_entry;

    @FXML
    private Button sell_button;

    public void sell(ActionEvent actionEvent){
        try {
            player.setPrice(Double.parseDouble(price_entry.getText()));
            client.getNetworkUtil().write(new Pair<>(client.getClub(),player));
            client.getClub().removePlayer(client.getClub().searchPlayerByName(player.getName()));
            System.out.println("Send");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Scene/PlayersList.fxml"));
            Parent root = loader.load();
            Controller controller = loader.getController();
            controller.setClient(client);
            client.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println("ERROR");
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

    @FXML
    public void backButton(ActionEvent mouseEvent) throws Exception {
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
}
