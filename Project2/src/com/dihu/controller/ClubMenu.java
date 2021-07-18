package com.dihu.controller;

import com.dihu.classes.Player;
import com.dihu.client.Client;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClubMenu extends Controller
{
    @FXML
    public ComboBox<String> SEARCH_OPTION;
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

    public ClubMenu(){

        labels = new ArrayList<>();
        buttons = new ArrayList<Button>();
        SEARCH_OPTION = new ComboBox<>();
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
                        Label l = new Label(String.format("%f",total));

                        l.setPrefHeight(60);
                        l.setPrefWidth(250);
                        l.setStyle( "-fx-font-size: 28;"+
                                    "-fx-text-fill: #e4c184;"+
                                    "-fx-font-family: \"Bauhaus 93\";");

                        ImageView dollar = new ImageView();
                        dollar.setImage(new Image(getClass().getResource("../Images/Dollar.png").toExternalForm()));
                        dollar.setFitWidth(60);
                        dollar.setPreserveRatio(true);

                        HBox row = new HBox(dollar,l);
                        row.setAlignment(Pos.CENTER_LEFT);
                        AnchorPane.setLeftAnchor(row, 50.0);
                        AnchorPane.setTopAnchor(row, 20.0);
                        anchorPane.getChildren().add(row);
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
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


                            l1.setPrefWidth(130);
                            l1.setPrefHeight(height);
                            l1.setStyle("-fx-font-size: 22;"+
                                        "-fx-text-fill: #3498db"
                            );

                            l2.setPrefWidth(100);
                            l2.setPrefHeight(height);
                            l2.setAlignment(Pos.CENTER);
                            l2.setStyle("-fx-font-size: 22;"+
                                        "-fx-text-fill: #2ecc71");

                            ImageView country = new ImageView();
                            country.setImage(new Image(getClass().getResource("../images/Country/"+m.getKey()+".png").toExternalForm()));
                            country.setFitWidth(50);
                            country.setPreserveRatio(true);

                            HBox row = new HBox(country,l1,l2);
                            row.setAlignment(Pos.CENTER_LEFT);
                            row.setSpacing(15);
                            AnchorPane.setLeftAnchor(row, 60.0);
                            AnchorPane.setTopAnchor(row, i*(height+20.0));
                            anchorPane.getChildren().add(row);
                            i++;
                        }
                    }
                    else {
                        System.out.println("No player in the database");
                    }

                }else if(currentFilter.equals("")){

                }
            }
        });
    }
    @Override
    public void setClient(Client client){
        this.client = client;
        client.setFileName("ClubMenu.fxml");
    }
}
