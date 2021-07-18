package com.dihu.controller;

import com.dihu.classes.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;

import java.io.IOException;

public class PlayerDetails extends Controller {

    private Player p;

    @FXML
    private Label errorLabel;

    @FXML
    private Button buyButton;

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

    @FXML
    private TextField priceEntry;


    public void sell(ActionEvent actionEvent) throws Exception {
        try {p.setPrice(Double.parseDouble(priceEntry.getText()));
        System.out.println(p.getPrice());
        client.getNetworkUtil().write(new Pair<>(client.getClub().getName(),p));
        client.getClub().removePlayer(client.getClub().searchPlayerByName(p.getName()));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../scene/SellPlayerList.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        controller.setClient(client);
        client.getScene().setRoot(root);
        } catch (Exception e) {
            errorLabel.setText("Invalid input");
        }
    }

    public void buy(ActionEvent actionEvent) throws IOException {
        client.getNetworkUtil().write(new Pair<>(client.getClub().getName(),p));
        p.setClub(client.getClub().getName());
        client.getClub().addPlayer(p);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Scene/MainMenu.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        controller.setClient(client);
        client.getScene().setRoot(root);
    }

    public void cancel(ActionEvent mouseEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(prevFXML));
        Parent root = loader.load();

        Controller controller = (Controller)loader.getController();
        controller.setClient(client);

        ((Node)mouseEvent.getSource()).getScene().setRoot(root);
    }
    @FXML
    public void initialize(){
        p = Player.player;
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

        try{
            buyButton.setText(String.format("%.1f",p.getPrice())+" $");
            buyButton.addEventHandler(MouseEvent.MOUSE_ENTERED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            buyButton.setText("BUY");
                        }
                    });
            buyButton.addEventHandler(MouseEvent.MOUSE_EXITED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            buyButton.setText(String.format("%.1f",p.getPrice())+" $");
                        }
                    });
        }catch(Exception e){

        }


    }

}
