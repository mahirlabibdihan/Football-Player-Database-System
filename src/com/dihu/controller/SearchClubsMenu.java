package com.dihu.controller;

import com.dihu.IO.Console;
import com.dihu.classes.Player;
import com.dihu.controller.Controller;
import com.dihu.controller.PlayerDetails;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchClubsMenu extends Controller
{
    @FXML
    public ComboBox<String> SEARCH_OPTION;
    private String[] options = {"Name","Country","Position","Salary"};

    private int i;
    @FXML
    private AnchorPane playerSearchPane;

    private List<Button> buttons;
    private List<Label> labels;

    private SpinnerValueFactory<String> filterValueFactory;
    @FXML
    private Spinner filterSpinner;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private AnchorPane anchorPane;


    public SearchClubsMenu(){
        labels = new ArrayList<>();
        buttons = new ArrayList<Button>();
        SEARCH_OPTION = new ComboBox<>();
        i=0;
    }
    public void printPlayers(List<Player> searchedPlayers){
        for(int i=0;i<searchedPlayers.size();i++){
            Button b = new Button(SEARCH_OPTION.getValue());
            b.setText(searchedPlayers.get(i).getName());
            b.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("PlayerDetails.fxml"));
                        //((PlayerDetails)loader.getController()).setPlayer(Database.searchPlayerByName(((Button)e.getSource()).getText()));
                        Player p = client.getClub().searchPlayerByName(((Button)e.getSource()).getText());
                        Player.player = p;
                        Parent root = loader.load();

                        PlayerDetails controller = loader.getController();
                        controller.setClient(client);

                        ((Button)e.getSource()).getScene().setRoot(root);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });
            float height= Math.min((float) (540.0/searchedPlayers.size()),50);
            AnchorPane.setLeftAnchor(b, 20.0);
            AnchorPane.setRightAnchor(b, 20.0);
            AnchorPane.setTopAnchor(b, 90.0 + i*(1.2*height));
            b.setPrefHeight(height);
            b.setId("player-button");
            b.setStyle("-fx-font-size: "+Math.min(22,height-10)+"px;");
            buttons.add(b);
            playerSearchPane.getChildren().add(b);
        }
    }
    public void setSearchOption(ActionEvent actionEvent){
            for(Button b:buttons){
                playerSearchPane.getChildren().remove(b);
            }
            buttons.clear();
        for(Label l:labels){
            playerSearchPane.getChildren().remove(l);
        }
        labels.clear();
            String option = SEARCH_OPTION.getValue();
            if(option.equals("Maximum Salary")){
                try{
                    List<Player> searchedPlayers = client.getClub().getMaxSalaryPlayer();
                    Console.printSuccess("Club Found");
                    printPlayers(searchedPlayers);
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
            }
            else if(option.equals("Maximum Age")){
                try{
                    List<Player> searchedPlayers = client.getClub().getMaxAgePlayer();
                    Console.printSuccess("Club Found");
                    printPlayers(searchedPlayers);
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
            }
            else if(option.equals("Maximum Height")){
                try{
                    List<Player> searchedPlayers = client.getClub().getMaxHeightPlayer();
                    Console.printSuccess("Club Found");
                    printPlayers(searchedPlayers);
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
            }
            else if(option.equals("Total Yearly Salary")){
                try{
                    double total = client.getClub().getTotalYearlySalary();
                    Console.printSuccess("Club Found");
                    Label l = new Label(String.format("%f",total));

                    float height= (float) 50;
                    AnchorPane.setLeftAnchor(l, 20.0);
                    AnchorPane.setRightAnchor(l, 20.0);
                    AnchorPane.setTopAnchor(l, 90.0);
                    l.setPrefHeight(height);
                    l.setStyle("-fx-font-size: 24;" );
                    l.setAlignment(Pos.CENTER);
                    playerSearchPane.getChildren().add(l);
                    labels.add(l);
                }
                catch(Exception e)
                {
                    Console.printError("No such club with this name");
                }
            }
            else if(option.equals("Country Wise Player Count")){
                Map<String,Integer> count = new HashMap<>();
                client.getClub().getCountryWisePlayerCount(count);
                if(count.size()>0) {
                    // Printing header

                    // Printing country wise player count
                    int i=0;
                    for (Map.Entry<String, Integer> m : count.entrySet()) {
                        Label l1 = new Label(m.getKey());
                        Label l2 = new Label(Integer.toString(m.getValue()));

                        float height= Math.min((float) (540.0/count.size()),50);
                        AnchorPane.setLeftAnchor(l1, 20.0);
                        AnchorPane.setTopAnchor(l1, 90.0 + i*(height+2));
                        l1.setPrefWidth(270);
                        l1.setPrefHeight(height);
                        l1.setAlignment(Pos.CENTER);
                        labels.add(l1);
                        l1.setStyle("-fx-font-size: "+Math.min(22,height-10)+"px;");
                        playerSearchPane.getChildren().add(l1);

                        AnchorPane.setLeftAnchor(l2, 310.0);
                        AnchorPane.setRightAnchor(l2, 20.0);
                        AnchorPane.setTopAnchor(l2, 90.0 + i*(1.2*height));
                        l2.setPrefHeight(height);
                        l2.setAlignment(Pos.CENTER);
                        l2.setStyle("-fx-font-size: "+Math.min(22,height-10)+"px;");
                        playerSearchPane.getChildren().add(l2);
                        labels.add(l2);
                        i++;
                    }

                }
                else Console.printError("No player in the database");
            }
    }

    @FXML
    public void back(MouseEvent mouseEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Scene/MainMenu.fxml"));
        Parent root = loader.load();

        Controller controller = (Controller)loader.getController();
        controller.setClient(client);

        ((Node)mouseEvent.getSource()).getScene().setRoot(root);
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
        filterSpinner.setValueFactory(filterValueFactory);
        filterSpinner.valueProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<? extends Object> observableValue, Object o, Object t1) {
                String currentFilter = (String) filterSpinner.getValue();
                anchorPane.getChildren().clear();
                scrollPane.setPrefHeight(90);
                anchorPane.setPrefHeight(88);
                if(currentFilter.equals("TOTAL YEARLY SALARY")){
                    try{
                        double total = client.getClub().getTotalYearlySalary();
                        Console.printSuccess("Club Found");
                        Label l = new Label(String.format("%f",total));

                        float height= (float) 50;
                        AnchorPane.setLeftAnchor(l, 60.0);
                        AnchorPane.setTopAnchor(l, 20.0);
                        l.setPrefHeight(height);
                        l.setPrefWidth(280);
                        l.setStyle("-fx-font-size: 24;" );
                        l.setAlignment(Pos.CENTER);
                        anchorPane.getChildren().add(l);
                    }
                    catch(Exception e)
                    {
                        Console.printError("No such club with this name");
                    }
                }else if(currentFilter.equals("COUNTRY WISE PLAYER COUNT")){

                    Map<String,Integer> count = new HashMap<>();
                    client.getClub().getCountryWisePlayerCount(count);
                    scrollPane.setPrefHeight(280);
                    float height= 65;
                    if(count.size()>0) {
                        anchorPane.setPrefHeight(count.size()*(height+20));
                        int i=0;
                        for (Map.Entry<String, Integer> m : count.entrySet()) {
                            Label l1 = new Label(m.getKey());
                            Label l2 = new Label(Integer.toString(m.getValue()));


                            l1.setPrefWidth(200);
                            l1.setPrefHeight(height);
                            l1.setStyle("-fx-font-size: 24;" );

                            l2.setPrefWidth(100);
                            l2.setPrefHeight(height);
                            l2.setAlignment(Pos.CENTER);
                            l2.setStyle("-fx-font-size: 24;" );

                            HBox row = new HBox(l1,l2);
                            AnchorPane.setLeftAnchor(row, 70.0);
                            AnchorPane.setTopAnchor(row, i*(height+20.0));
                            anchorPane.getChildren().add(row);
                            i++;
                        }
                    }
                    else Console.printError("No player in the database");

                }else if(currentFilter.equals("")){

                }
            }
        });
    }
}
