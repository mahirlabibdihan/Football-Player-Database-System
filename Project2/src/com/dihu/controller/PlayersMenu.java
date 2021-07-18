package com.dihu.controller;
import com.dihu.classes.Club;
import com.dihu.classes.Player;
import com.dihu.client.Client;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayersMenu extends Controller {
    @FXML
    private Label errorLabel;
    @FXML
    public ComboBox<String> SEARCH_OPTION;
    @FXML
    public TextField SEARCH_FIELD;


    private int i;
    @FXML
    private AnchorPane playerListPane;

    @FXML
    private HBox filterInput;

    private SpinnerValueFactory<String> filterValueFactory;
    @FXML
    private Spinner filterSpinner;
    private List<Player> playerList;
    private Map<Button,Player> playerMap;
    private List<Button> buttons;
    public PlayersMenu(){
        playerMap = new HashMap<>();
        buttons = new ArrayList<Button>();
        SEARCH_OPTION = new ComboBox<>();
        i=0;
    }

    @FXML
    public void back(MouseEvent mouseEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Scene/MainMenu.fxml"));
        Parent root = loader.load();

        Controller controller = (Controller)loader.getController();
        controller.setClient(client);

        ((Node)mouseEvent.getSource()).getScene().setRoot(root);
    }


    public void printPlayers(List<Player> searchedPlayers){
        VBox list = new VBox();
        AnchorPane.setLeftAnchor(list, 5.0);
        list.setSpacing(20);
        for(Player p:searchedPlayers){
            Button b = new Button(p.getName());

            b.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Scene/PlayerDetails.fxml"));
                        Player p = client.getClub().searchPlayerByName(((Button)e.getSource()).getText());
                        Player.player = p;
                        Parent root = loader.load();
                        PlayerDetails controller = loader.getController();
                        controller.setClient(client);
                        controller.setPrevFXML("../Scene/PlayersMenu.fxml");
                        ((Button)e.getSource()).getScene().setRoot(root);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });

            b.setPrefWidth(280);
            b.setId("player-button");

            ImageView country = new ImageView();
            country.setImage(new Image(getClass().getResource("../images/Player/"+p.getName()+".png").toExternalForm()));
            country.setFitHeight(45);
            country.setPreserveRatio(true);

            HBox row = new HBox(country,b);
            row.setPrefHeight(45);
            row.setAlignment(Pos.CENTER_LEFT);
            row.setSpacing(5);

            list.getChildren().add(row);
        }
        playerListPane.getChildren().add(list);
    }
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
                    Label l = new Label();
                    l.setText("POSITION");
                    l.setId("positionLabel");
                    l.setPrefWidth(110);
                    l.setStyle("-fx-text-fill: white");
                    l.setPadding(new Insets(0,0,0,0));

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
        List<Player> searchedPlayers = client.getClub().getPlayerList();
        if(searchedPlayers.size()>0) {
            printPlayers(searchedPlayers);
        }
        else {
            System.out.println("No player in the club");
        }

    }
    public void search(ActionEvent actionEvent) {
        errorLabel.setText("");
        playerListPane.getChildren().clear();
        playerMap.clear();
        SEARCH_FIELD = (TextField)filterInput.lookup("#nameInputField");

        if(SEARCH_FIELD!=null){
            System.out.println("Found");
            System.out.println(SEARCH_FIELD.getText());
        }
        String option = (String) filterSpinner.getValue();
        System.out.println(option);
        if(option.equals("NAME")) {
            System.out.println("Pressed");
            String name = SEARCH_FIELD.getText();
            Player p = client.getClub().searchPlayerByName(name);
            List<Player> searchedPlayers =  new ArrayList<>();

            if(p!=null) {
                searchedPlayers.add(p);
                printPlayers(searchedPlayers);
            }
            else {
                errorLabel.setText("No such player with this name");
            }
        }
        else if(option.equals("COUNTRY")){
            String country = SEARCH_FIELD.getText();
            List<Player> searchedPlayers = client.getClub().searchPlayerByCountry(country);
            if(searchedPlayers.size()>0) {
                printPlayers(searchedPlayers);
            }
            else {
                errorLabel.setText("No such player with this country");
            }
        }
        else if(option.equals("POSITION")){
            Spinner positionSpinner = (Spinner)filterInput.lookup("#positionSpinner");
            String position = (String)positionSpinner.getValue();
            List<Player> searchedPlayers =  client.getClub().searchPlayerByPosition(position);
            if(searchedPlayers.size()>0) {
                printPlayers(searchedPlayers);
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
                List<Player> searchedPlayers = client.getClub().searchPlayerBySalaryRange(start, end);

                if(searchedPlayers.size()>0) {
                    printPlayers(searchedPlayers);
                }
                else {
                    errorLabel.setText("No such player with this salary range");
                }
            }catch(Exception e){
                errorLabel.setText("Invalid input");
            }
        }
        else if(option.equals("MAX SALARY")){
            try{
                List<Player> searchedPlayers = client.getClub().getMaxSalaryPlayer();
                printPlayers(searchedPlayers);
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }
        else if(option.equals("MAX AGE")){
            try{
                List<Player> searchedPlayers = client.getClub().getMaxAgePlayer();
                printPlayers(searchedPlayers);
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }
        else if(option.equals("MAX HEIGHT")){
            try{
                List<Player> searchedPlayers = client.getClub().getMaxHeightPlayer();
                printPlayers(searchedPlayers);
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }else{
            List<Player> searchedPlayers = client.getClub().getPlayerList();
            if(searchedPlayers.size()>0) {
                printPlayers(searchedPlayers);
            }
            else {
                System.out.println("No player in the club");
            }
        }
    }
    @Override
    public void setClient(Client client) {
        this.client = client;
        client.setFileName("PlayersMenu.fxml");
        Club c = client.getClub();
        try{
            playerList= c.getPlayerList();
            printPlayers(playerList);
        }catch(Exception e){
            System.err.println(e);
        }
    }
}
