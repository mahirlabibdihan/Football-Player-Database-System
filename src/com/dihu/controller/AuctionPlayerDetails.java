package com.dihu.controller;

import com.dihu.classes.Player;
import com.dihu.controller.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.util.Pair;

public class AuctionPlayerDetails extends Controller {
    Player p;
    @FXML
    private Label name;

    @FXML
    private Label country;

    @FXML
    private Label age;

    @FXML
    private Label height;

    @FXML
    private Label club;

    @FXML
    private Label position;

    @FXML
    private Label number;

    @FXML
    private Label weeklySalary;

    public void buy(ActionEvent actionEvent) throws Exception {
        client.getNetworkUtil().write(new Pair<>(client.getClub(),p));
        p.setClub(client.getClub().getName());
        client.getClub().addPlayer(p);
        System.out.println("Send");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        controller.setClient(client);
        client.getScene().setRoot(root);
    }
    public void back(ActionEvent actionEvent) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        client.getScene().setRoot(root);
    }
    @FXML
    public void initialize(){
        p = Player.player;
        name.setText(p.getName());
        country.setText(p.getCountry());
        age.setText(Integer.toString(p.getAge()));
        height.setText(Double.toString(p.getHeight()));
        club.setText(p.getClub());
        position.setText(p.getPosition());
        number.setText(Integer.toString(p.getNumber()));
        weeklySalary.setText(Double.toString(p.getWeeklySalary()));
    }

}
