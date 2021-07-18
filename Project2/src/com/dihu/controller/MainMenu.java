package com.dihu.controller;

import com.dihu.client.Client;
import com.dihu.controller.Controller;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainMenu extends Controller {
    private static Map<String, String> sceneMap = new HashMap<>();

    @FXML
    private ImageView badge;
    public MainMenu(){
        sceneMap.put("MAIN_MENU","../Scene/MainMenu.fxml");
        sceneMap.put("SEARCH_PLAYERS","../Scene/PlayersMenu.fxml");
        sceneMap.put("SEARCH_CLUBS","../Scene/ClubMenu.fxml");
        sceneMap.put("ADD_PLAYER","../Scene/TransferMenu.fxml");
        sceneMap.put("LIST_PLAYER","../Scene/PlayersList.fxml");
    }
    public void exit(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Scene/LoginForm.fxml"));
        Parent root = loader.load();

        Controller controller = (Controller)loader.getController();
        controller.setClient(client);

        client.getNetworkUtil().closeConnection();

        ((Button)actionEvent.getSource()).getScene().setRoot(root);
        root.requestFocus();
    }
    public void setScene(ActionEvent actionEvent) throws Exception {
        Button b=(Button)actionEvent.getSource();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(sceneMap.get(b.getId())));
        Parent root = loader.load();

        Controller controller = (Controller)loader.getController();
        controller.setClient(client);


        ((Button)actionEvent.getSource()).getScene().setRoot(root);
    }

    @Override
    public void setClient(Client client) {
        this.client = client;
        client.setFileName("MainMenu.fxml");
        badge.setImage(new Image(getClass().getResource("../images/Club/"+client.getClub().getName()+".png").toExternalForm()));
    }
}
