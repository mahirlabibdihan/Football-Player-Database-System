package com.dihu.controller;

import com.dihu.classes.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class PlayerDetails extends Controller {
    @FXML
    private Label weeklySalary;

    @FXML
    private ImageView face;

    @FXML
    private ImageView club;

    @FXML
    private ImageView country;

    @FXML
    private Label name;

    @FXML
    private Label number;

    @FXML
    private Label position;

    @FXML
    private Label age;

    @FXML
    private Label height;

    public void sell(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../scene/SellMenu.fxml"));
        Parent root = loader.load();

        SellMenu controller = (SellMenu)loader.getController();
        controller.setClient(client);
        controller.setPlayerName(name.getText());
        client.getScene().setRoot(root);
    }

    @FXML
    public void initialize(){
        Player p = Player.player;
        name.setText(p.getName().toUpperCase());
        System.out.println("../images/face/"+p.getName()+".png");
        face.setImage(new Image(getClass().getResource("../images/face/"+p.getName()+".png").toExternalForm()));
        country.setImage(new Image(getClass().getResource("../images/country/"+p.getCountry()+".png").toExternalForm()));
        club.setImage(new Image(getClass().getResource("../images/club/"+p.getClub()+".png").toExternalForm()));

        age.setText(Integer.toString(p.getAge())+" years");
        height.setText(Double.toString(p.getHeight())+" meters");

        position.setText(p.getPosition());
        weeklySalary.setText(Double.toString(p.getWeeklySalary())+" $");

        number.setText(Integer.toString(p.getNumber()));
    }

}
