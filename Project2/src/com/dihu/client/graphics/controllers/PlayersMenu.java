package com.dihu.client.graphics.controllers;

import com.dihu.util.Player;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

public class PlayersMenu extends Controller{
    @FXML
    private Label errorLabel;
    @FXML
    private AnchorPane playerListPane;
    @FXML
    private HBox filterInput;
    @FXML
    private Spinner filterSpinner;

    private SpinnerValueFactory<String> filterValueFactory;
    private List<Player> searchPlayerByCountry(String country) {
        List<Player> searchedPlayers = new ArrayList<>();
        for (Player p : client.getClub().getPlayerList()) {
            if (p.getCountry().toLowerCase().contains(country.toLowerCase())) {
                searchedPlayers.add(p);
            }
        }
        return searchedPlayers;
    }
    private List<Player> searchPlayerByName(String name) {
        List<Player> searchedPlayers = new ArrayList<>();
        for (Player p : client.getClub().getPlayerList()) {
            if (p.getName().toLowerCase().contains(name.toLowerCase())) {
                searchedPlayers.add(p);
            }
        }
        return searchedPlayers;
    }
    private void nameFieldListener(TextField nameInput){
        errorLabel.setText("");
        playerListPane.getChildren().clear();
        String name = nameInput.getText();
        client.getPlayerListHandler().setPlayerList(searchPlayerByName(name));
        if (client.getPlayerListHandler().getPlayerList().size() > 0) {
            playerListPane.getChildren().add(client.getPlayerListHandler().getPlayerListUi());
        } else {
            errorLabel.setText("No such player with this name");
        }
    }
    private void nameFieldSet(){
        filterInput.getChildren().clear();
        TextField nameInput = new TextField();
        nameInput.setPromptText("Enter player's name");
        nameInput.setId("nameInputField");
        filterInput.getChildren().add(nameInput);
        filterInput.setAlignment(Pos.CENTER);

        nameInput.textProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<? extends Object> observableValue, Object o, Object t1) {
                nameFieldListener(nameInput);
            }
        });
    }
    private void countryFieldListener(TextField countryInput){
        errorLabel.setText("");
        playerListPane.getChildren().clear();
        String country = countryInput.getText();
        client.getPlayerListHandler().setPlayerList(searchPlayerByCountry(country));
        if (client.getPlayerListHandler().getPlayerList().size() > 0) {
            playerListPane.getChildren().add(client.getPlayerListHandler().getPlayerListUi());
        } else {
            errorLabel.setText("No such player with this country");
        }
    }
    private void countryFieldSet(){
        filterInput.getChildren().clear();
        TextField countryInput = new TextField();
        countryInput.setPromptText("Enter country's name");
        countryInput.setId("nameInputField");
        filterInput.getChildren().add(countryInput);
        filterInput.setAlignment(Pos.CENTER);
        countryInput.textProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<? extends Object> observableValue, Object o, Object t1) {
                countryFieldListener(countryInput);
            }
        });
    }
    private void positionSpinnerListener(Spinner positionSpinner){
        String position = (String) positionSpinner.getValue();

        errorLabel.setText("");
        playerListPane.getChildren().clear();
        client.getPlayerListHandler().setPlayerList(client.getClub().searchPlayerByPosition(position));

        if (client.getPlayerListHandler().getPlayerList().size() > 0) {
            playerListPane.getChildren().add(client.getPlayerListHandler().getPlayerListUi());
        } else if (!position.equals("")) {
            errorLabel.setText("No such player with this position");
        } else{
            client.getPlayerListHandler().setPlayerList(client.getClub().getPlayerList());
            if (client.getPlayerListHandler().getPlayerList().size() > 0) {
                playerListPane.getChildren().add(client.getPlayerListHandler().getPlayerListUi());
            }
        }
    }
    private void positionSpinnerSet(){
        Label l = new Label("POSITION");
        l.setId("positionLabel");

        Spinner positionSpinner = new Spinner();
        positionSpinner.setId("positionSpinner");
        SpinnerValueFactory<String> positionValueFactory;
        positionValueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<String>(
                FXCollections.observableArrayList(
                        "",
                        "GOALKEEPER",
                        "DEFENDER",
                        "MIDFIELDER",
                        "FORWARD"
                )
        );
        positionSpinner.setValueFactory(positionValueFactory);
        positionSpinner.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_LEFT_HORIZONTAL);

        filterInput.getChildren().clear();
        filterInput.setSpacing(0);
        filterInput.setAlignment(Pos.CENTER_LEFT);
        filterInput.getChildren().addAll(l, positionSpinner);

        positionSpinner.valueProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<? extends Object> observableValue, Object o, Object t1) {
                positionSpinnerListener(positionSpinner);
            }
        });
    }
    private void salaryFieldListener(TextField from,TextField to){
        errorLabel.setText("");
        playerListPane.getChildren().clear();
        double start, end;
        try {
            start = Double.parseDouble(from.getText());
        } catch (Exception e) {
            if (from.getText().equals("")) {
                start = 0;
            } else {
                errorLabel.setText("Must be a double");
                return;
            }
        }
        try {
            end = Double.parseDouble(to.getText());
        } catch (Exception e) {
            if (to.getText().equals("")) {
                end = Double.MAX_VALUE;
            } else {
                errorLabel.setText("Must be a double");
                return;
            }

        }
        if (start < 0 || end < 0) {
            errorLabel.setText("Must be a Positive double");
            return;
        }

        client.getPlayerListHandler().setPlayerList(client.getClub().searchPlayerBySalaryRange(start, end));
        if (client.getPlayerListHandler().getPlayerList().size() > 0) {
            playerListPane.getChildren().add(client.getPlayerListHandler().getPlayerListUi());
        } else {
            errorLabel.setText("No such player with this salary range");
        }
    }
    private void salaryFieldSet() {
        TextField from = new TextField();
        from.setPromptText("From");
        from.setId("from");

        TextField to = new TextField();
        to.setPromptText("To");
        to.setId("to");

        filterInput.getChildren().clear();
        filterInput.getChildren().addAll(from, to);
        filterInput.setSpacing(20);

        from.textProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<? extends Object> observableValue, Object o, Object t1) {
                salaryFieldListener(from,to);
            }
        });

        to.textProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<? extends Object> observableValue, Object o, Object t1) {
                salaryFieldListener(from,to);
            }
        });
    }
    private void filterSpinnerListener(){
        reset();
        String currentFilter = (String) filterSpinner.getValue();
        if (currentFilter.equals("NAME")) {
            nameFieldSet();
        }
        else if (currentFilter.equals("COUNTRY")) {
            countryFieldSet();
        }
        else if (currentFilter.equals("POSITION")) {
            positionSpinnerSet();
        }
        else if (currentFilter.equals("SALARY")) {
            salaryFieldSet();
        }
        else if (currentFilter.equals("MAX SALARY")) {
            playerListPane.getChildren().clear();
            try {
                client.getPlayerListHandler().setPlayerList(client.getClub().getMaxSalaryPlayer());
                playerListPane.getChildren().add(client.getPlayerListHandler().getPlayerListUi());
            } catch (Exception e) {
                errorLabel.setText("No player in the club");
            }
        }
        else if (currentFilter.equals("MAX AGE")) {
            playerListPane.getChildren().clear();
            try {
                client.getPlayerListHandler().setPlayerList(client.getClub().getMaxAgePlayer());
                playerListPane.getChildren().add(client.getPlayerListHandler().getPlayerListUi());
            } catch (Exception e) {
                errorLabel.setText("No player in the club");
            }
        }
        else if (currentFilter.equals("MAX HEIGHT")) {
            playerListPane.getChildren().clear();
            try {
                client.getPlayerListHandler().setPlayerList(client.getClub().getMaxHeightPlayer());
                playerListPane.getChildren().add(client.getPlayerListHandler().getPlayerListUi());
            } catch (Exception e) {
                errorLabel.setText("No player in the club");
            }
        }
    }
    private void reset(){
        errorLabel.setText("");
        filterInput.getChildren().clear();
        playerListPane.getChildren().clear();
        client.getPlayerListHandler().setPlayerList(client.getClub().getPlayerList());
        if (client.getPlayerListHandler().getPlayerList().size() > 0) {
            playerListPane.getChildren().add(client.getPlayerListHandler().getPlayerListUi());
        }else{
            errorLabel.setText("No player in the club");
        }
    }
    @Override
    public void init() {
        reset();
        filterValueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<String>(
                FXCollections.observableArrayList(
                        "",
                        "NAME",
                        "COUNTRY",
                        "POSITION",
                        "SALARY",
                        "MAX SALARY",
                        "MAX AGE",
                        "MAX HEIGHT"
                )
        );
        filterSpinner.setValueFactory(filterValueFactory);
        filterSpinner.valueProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<? extends Object> observableValue, Object o, Object t1) {
                filterSpinnerListener();
            }
        });
    }
}
