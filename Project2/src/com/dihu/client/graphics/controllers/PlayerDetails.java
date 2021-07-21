package com.dihu.client.graphics.controllers;

import com.dihu.util.Player;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;

public class PlayerDetails extends Controller {
    private Player p;

    @FXML
    private JFXButton buyButton;

    @FXML
    private ImageView face, club, country;

    @FXML
    private Label name, number, position, age, height, weeklySalary, errorLabel;

    @FXML
    private TextField priceEntry;

    public void sell(ActionEvent actionEvent) {
        try {
            p.setPrice(Double.parseDouble(priceEntry.getText()));
            if (p.getPrice() < 0) {
                errorLabel.setText("Must be a Positive double");
                return;
            }
            client.getNetworkUtil().write(new Pair<>(client.getClub().getName(), p));
        } catch (Exception e) {
            errorLabel.setText("Must be a double");
        }
    }

    public void buy(ActionEvent actionEvent) throws Exception {
        client.getNetworkUtil().write(new Pair<>(client.getClub().getName(), p));
    }

    public void cancel(ActionEvent mouseEvent) throws Exception {
        back(null);
    }

    public void setPrice(double price) {
        try {
            buyButton.setText(String.format("%.1f", price) + " $");
            buyButton.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> buyButton.setText("BUY"));
            buyButton.addEventHandler(MouseEvent.MOUSE_EXITED, e -> buyButton.setText(String.format("%.1f", p.getPrice()) + " $"));
        } catch (Exception e) {

        }
    }

    public void setPlayer(Player p) {
        this.p = p;
        name.setText(p.getName().toUpperCase());
        face.setImage(new Image(getClass().getResource("../assets/images/player/" + p.getName() + ".png").toExternalForm()));
        country.setImage(new Image(getClass().getResource("../assets/images/country/" + p.getCountry() + ".png").toExternalForm()));
        club.setImage(new Image(getClass().getResource("../assets/images/club/" + p.getClub() + ".png").toExternalForm()));  // Bug: p.getClub()
        age.setText(p.getAge() + " years");
        height.setText(p.getHeight() + " meters");
        position.setText(p.getPosition());
        weeklySalary.setText(p.getWeeklySalary() + " $");
        number.setText(Integer.toString(p.getNumber()));
    }
}
