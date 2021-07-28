package com.dihu.client.fx.controllers;

import com.dihu.util.Player;
import com.jfoenix.controls.JFXButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class PlayerCard extends Controller {
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
            client.getNetworkUtil().write(p);
            clickSound(null);
        } catch (Exception e) {
            errorLabel.setText("Must be a double");
        }
    }

    public void buy(ActionEvent actionEvent) {
        try{
            client.getNetworkUtil().write(p);
            clickSound(null);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void cancel(ActionEvent actionEvent) {
        back(null);
    }

    public void setPrice(double price) {
        try {
            buyButton.setText(String.format("%.1f", price) + " $");
            buyButton.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
                hoverSound(null);
                buyButton.setText("BUY");
            });
            buyButton.addEventHandler(MouseEvent.MOUSE_EXITED, e -> buyButton.setText(String.format("%.1f", p.getPrice()) + " $"));
        } catch (Exception e) {
            System.out.println("Not buy menu");
        }
    }

    public void setPlayer(Player p) {
        this.p = p;
        name.setText(p.getName().toUpperCase());
        face.setImage(new Image(getClass().getResource("../assets/images/player/" + p.getName() + ".png").toExternalForm()));
        country.setImage(new Image(getClass().getResource("../assets/images/country/" + p.getCountry() + ".png").toExternalForm()));
        club.setImage(new Image(getClass().getResource("../assets/images/club/" + p.getClub() + ".png").toExternalForm()));
        age.setText(p.getAge() + " years");
        height.setText(p.getHeight() + " meters");
        position.setText(p.getPosition());
        weeklySalary.setText(p.getWeeklySalary() + " $");
        number.setText(Integer.toString(p.getNumber()));

        // Sell price validation
        try {
            priceEntry.textProperty().addListener(new ChangeListener<Object>() {
                @Override
                public void changed(ObservableValue<? extends Object> observableValue, Object o, Object t1) {
                    errorLabel.setText("");
                    try {
                        p.setPrice(Double.parseDouble(priceEntry.getText()));
                        if (p.getPrice() < 0) {
                            errorLabel.setText("Must be a Positive double");
                        }
                    } catch (Exception e) {
                        if (!priceEntry.getText().equals(""))
                            errorLabel.setText("Must be a double");
                    }
                }
            });
        }catch(Exception e){
            System.out.println("Not sell menu");
        }
    }
}
