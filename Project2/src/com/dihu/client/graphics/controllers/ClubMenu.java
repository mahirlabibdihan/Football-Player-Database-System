package com.dihu.client.graphics.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

public class ClubMenu extends Controller {
    private SpinnerValueFactory<String> filterValueFactory;

    @FXML
    private Spinner clubSpinner;

    @FXML
    private AnchorPane anchorPane;

    public void showTotalYearlySalary() {
        double total = client.getClub().getTotalYearlySalary();
        Label l = new Label(String.format("%f", total));

        l.setStyle(
                "-fx-pref-height: 60;" +
                        "-fx-pref-width: 250;" +
                        "-fx-font-size: 28;" +
                        "-fx-text-fill: #e4c184;" +
                        "-fx-font-family: \"Berlin sans fb demi\";"
        );

        ImageView dollar = new ImageView();
        dollar.setImage(new Image(getClass().getResource("../assets/images/Dollar.png").toExternalForm()));
        dollar.setFitWidth(60);
        dollar.setPreserveRatio(true);

        HBox row = new HBox(dollar, l);
        row.setAlignment(Pos.CENTER_LEFT);
        AnchorPane.setLeftAnchor(row, 50.0);
        AnchorPane.setTopAnchor(row, 20.0);
        anchorPane.getChildren().add(row);
    }

    public void showCountryWisePlayerCount() {
        Map<String, Integer> count = new HashMap<>();
        client.getClub().getCountryWisePlayerCount(count);
        if (count.size() > 0) {
            VBox list = new VBox();
            AnchorPane.setLeftAnchor(list, 60.0);
            list.setSpacing(20);
            for (Map.Entry<String, Integer> m : count.entrySet()) {
                Label l1 = new Label(m.getKey());
                Label l2 = new Label(Integer.toString(m.getValue()));

                l1.setStyle(
                        "-fx-pref-height: 65;" +
                                "-fx-pref-width: 130;" +
                                "-fx-font-size: 22;" +
                                "-fx-text-fill: #3498db;" +
                                "-fx-font-family: \"Berlin sans fb demi\";"
                );

                l2.setStyle(
                        "-fx-alignment: center;" +
                                "-fx-pref-height: 65;" +
                                "-fx-pref-width: 100;" +
                                "-fx-font-size: 22;" +
                                "-fx-text-fill: #2ecc71"
                );

                ImageView country = new ImageView();
                country.setImage(new Image(getClass().getResource("../assets/images/Country/" + m.getKey() + ".png").toExternalForm()));
                country.setFitWidth(50);
                country.setPreserveRatio(true);

                HBox row = new HBox(country, l1, l2);
                row.setAlignment(Pos.CENTER_LEFT);
                row.setSpacing(15);
                list.getChildren().add(row);
            }
            anchorPane.getChildren().add(list);
        } else {
            System.out.println("No player in the database");
        }
    }

    @FXML
    public void initialize() {
        filterValueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<String>(
                FXCollections.observableArrayList(
                        "",
                        "TOTAL YEARLY SALARY",
                        "COUNTRY WISE PLAYER COUNT"
                )
        );
        clubSpinner.setValueFactory(filterValueFactory);
        clubSpinner.valueProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<? extends Object> observableValue, Object o, Object t1) {
                String currentFilter = (String) clubSpinner.getValue();
                anchorPane.getChildren().clear();
                if (currentFilter.equals("TOTAL YEARLY SALARY")) {
                    showTotalYearlySalary();
                } else if (currentFilter.equals("COUNTRY WISE PLAYER COUNT")) {
                    showCountryWisePlayerCount();
                }
            }
        });
    }
}
