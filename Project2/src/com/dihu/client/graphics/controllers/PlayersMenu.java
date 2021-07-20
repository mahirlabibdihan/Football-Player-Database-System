package com.dihu.client.graphics.controllers;

import com.dihu.util.Club;
import com.dihu.util.Player;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
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
    public TextField SEARCH_FIELD;
    @FXML
    private AnchorPane playerListPane;
    @FXML
    private HBox filterInput;

    private SpinnerValueFactory<String> filterValueFactory;
    @FXML
    private Spinner filterSpinner;

    @FXML
    public void initialize() {
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
                String currentFilter = (String) filterSpinner.getValue();
                if(currentFilter.equals("NAME")){
                    filterInput.getChildren().clear();
                    TextField nameInput = new TextField();
                    nameInput.setPromptText("Enter player's name");
                    nameInput.setId("nameInputField");
                    filterInput.getChildren().add(nameInput);
                    filterInput.setAlignment(Pos.CENTER);
                }else if(currentFilter.equals("COUNTRY")){
                    filterInput.getChildren().clear();
                    TextField nameInput = new TextField();
                    nameInput.setPromptText("Enter country's name");
                    nameInput.setId("nameInputField");
                    filterInput.getChildren().add(nameInput);
                    filterInput.setAlignment(Pos.CENTER);
                }else if(currentFilter.equals("POSITION")){
                    filterInput.getChildren().clear();
                    Label l = new Label("POSITION");
                    l.setId("positionLabel");
                    l.setStyle( "-fx-pref-width: 110;"+
                                "-fx-text-fill: white;" +
                                "-fx-font-size: 16;" +
                                "-fx-font-weight: bold;");

                    Spinner positionSpinner=new Spinner();
                    positionSpinner.setId("positionSpinner");
                    SpinnerValueFactory<String> positionValueFactory;
                    positionValueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<String>(
                            FXCollections.observableArrayList(
                                    "GOALKEEPER",
                                        "DEFENDER",
                                        "MIDFIELDER",
                                        "FORWARD"
                            )
                    );
                    positionSpinner.setValueFactory(positionValueFactory);
                    positionSpinner.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_LEFT_HORIZONTAL);

                    filterInput.setSpacing(0);
                    filterInput.setAlignment(Pos.CENTER_LEFT);
                    filterInput.getChildren().addAll(l,positionSpinner);
                }else if(currentFilter.equals("SALARY")){
                    filterInput.getChildren().clear();
                    TextField from = new TextField();
                    from.setPromptText("From");
                    from.setId("from");

                    TextField to = new TextField();
                    to.setPromptText("To");
                    to.setId("to");

                    filterInput.getChildren().addAll(from,to);
                    filterInput.setSpacing(20);
                }else{
                    filterInput.getChildren().clear();
                }
            }
        });
    }

    public void reset(ActionEvent actionEvent){
        filterValueFactory.setValue("");
        errorLabel.setText("");
        client.getPlayerListHandler().setPlayerList(client.getClub().getPlayerList());
        if(client.getPlayerListHandler().getPlayerList().size()>0) {
            playerListPane.getChildren().add(client.getPlayerListHandler().getPlayerListUi());
        }
        else {

        }
    }

    public void search(ActionEvent actionEvent) {
        errorLabel.setText("");
        playerListPane.getChildren().clear();
        SEARCH_FIELD = (TextField)filterInput.lookup("#nameInputField");

        if(SEARCH_FIELD!=null){
            System.out.println("Found");
            System.out.println(SEARCH_FIELD.getText());
        }
        String option = (String) filterSpinner.getValue();
        System.out.println(option);
        if(option.equals("NAME")) {
            String name = SEARCH_FIELD.getText();
            Player p = client.getClub().searchPlayerByName(name);

            if(p!=null) {
                List<Player> playerList =  new ArrayList<>();
                playerList.add(p);
                client.getPlayerListHandler().setPlayerList(playerList);
                playerListPane.getChildren().add(client.getPlayerListHandler().getPlayerListUi());
            }
            else {
                errorLabel.setText("No such player with this name");
            }
        }
        else if(option.equals("COUNTRY")){
            String country = SEARCH_FIELD.getText();
            client.getPlayerListHandler().setPlayerList(client.getClub().searchPlayerByCountry(country));
            if(client.getPlayerListHandler().getPlayerList().size()>0) {
                playerListPane.getChildren().add(client.getPlayerListHandler().getPlayerListUi());
            }
            else {
                errorLabel.setText("No such player with this country");
            }
        }
        else if(option.equals("POSITION")){
            Spinner positionSpinner = (Spinner)filterInput.lookup("#positionSpinner");
            String position = (String)positionSpinner.getValue();
            client.getPlayerListHandler().setPlayerList(client.getClub().searchPlayerByPosition(position));
            if(client.getPlayerListHandler().getPlayerList().size()>0) {
                playerListPane.getChildren().add(client.getPlayerListHandler().getPlayerListUi());
            }
            else {
                errorLabel.setText("No such player with this position");
            }
        }
        else if(option.equals("SALARY")){
            TextField from = (TextField)filterInput.lookup("#from");
            TextField to = (TextField)filterInput.lookup("#to");
            try{
                double start = Double.parseDouble(from.getText());
                double end = Double.parseDouble(to.getText());
                if(start<0||end<0){
                    errorLabel.setText("Must be a Positive double");
                    return;
                };
                client.getPlayerListHandler().setPlayerList(client.getClub().searchPlayerBySalaryRange(start, end));

                if(client.getPlayerListHandler().getPlayerList().size()>0) {
                    playerListPane.getChildren().add(client.getPlayerListHandler().getPlayerListUi());
                }
                else {
                    errorLabel.setText("No such player with this salary range");
                }
            }catch(Exception e){
                errorLabel.setText("Must be a double");
            }
        }
        else if(option.equals("MAX SALARY")){
            try{
                client.getPlayerListHandler().setPlayerList(client.getClub().getMaxSalaryPlayer());
                playerListPane.getChildren().add(client.getPlayerListHandler().getPlayerListUi());
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }
        else if(option.equals("MAX AGE")){
            try{
                client.getPlayerListHandler().setPlayerList(client.getClub().getMaxAgePlayer());
                playerListPane.getChildren().add(client.getPlayerListHandler().getPlayerListUi());
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }
        else if(option.equals("MAX HEIGHT")){
            try{
                client.getPlayerListHandler().setPlayerList(client.getClub().getMaxHeightPlayer());
                playerListPane.getChildren().add(client.getPlayerListHandler().getPlayerListUi());
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }else{
            client.getPlayerListHandler().setPlayerList(client.getClub().getPlayerList());
            if(client.getPlayerListHandler().getPlayerList().size()>0) {
                playerListPane.getChildren().add(client.getPlayerListHandler().getPlayerListUi());
            }
            else {
                System.out.println("No player in the club");
            }
        }
    }

    @Override
    public void init(){
        Club c = client.getClub();
        client.getPlayerListHandler().setPlayerList(c.getPlayerList());
        playerListPane.getChildren().add(client.getPlayerListHandler().getPlayerListUi());
    }
}
