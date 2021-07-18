package com.dihu.controller;
import com.dihu.classes.Player;
import com.dihu.client.Client;
import javafx.event.ActionEvent;
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

public class TransferMenu extends Controller {
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
        client.setFileName("TransferMenu.fxml");
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
