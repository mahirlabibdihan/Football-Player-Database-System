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
import javafx.scene.layout.VBox;

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
                    float height= 65;
                    if(count.size()>0) {
                        int i=0;
                        VBox list = new VBox();
                        AnchorPane.setLeftAnchor(list, 60.0);
                        list.setSpacing(20);
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
                            list.getChildren().add(row);
                            i++;
                        }
                        anchorPane.getChildren().add(list);
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
