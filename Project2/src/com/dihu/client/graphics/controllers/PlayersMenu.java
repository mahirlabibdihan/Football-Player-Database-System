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

public class PlayersMenu extends Controller {
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
                errorLabel.setText("");
                playerListPane.getChildren().clear();
                client.getPlayerListHandler().setPlayerList(client.getClub().getPlayerList());
                if (client.getPlayerListHandler().getPlayerList().size() > 0) {
                    playerListPane.getChildren().add(client.getPlayerListHandler().getPlayerListUi());
                } else {

                }
                filterInput.getChildren().clear();
                String currentFilter = (String) filterSpinner.getValue();
                if (currentFilter.equals("NAME")) {
                    filterInput.getChildren().clear();
                    TextField nameInput = new TextField();
                    nameInput.setPromptText("Enter player's name");
                    nameInput.setId("nameInputField");
                    filterInput.getChildren().add(nameInput);
                    filterInput.setAlignment(Pos.CENTER);


                    nameInput.textProperty().addListener(new ChangeListener<Object>() {
                        public List<Player> searchPlayerByName(String name) {
                            List<Player> searchedPlayers = new ArrayList<>();
                            for (Player p : client.getClub().getPlayerList()) {
                                if (p.getName().toLowerCase().contains(name.toLowerCase())) {
                                    searchedPlayers.add(p);
                                }
                            }
                            return searchedPlayers;
                        }

                        @Override
                        public void changed(ObservableValue<? extends Object> observableValue, Object o, Object t1) {
                            System.out.println("changed");
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
                    });
                } else if (currentFilter.equals("COUNTRY")) {
                    filterInput.getChildren().clear();
                    TextField nameInput = new TextField();
                    nameInput.setPromptText("Enter country's name");
                    nameInput.setId("nameInputField");
                    filterInput.getChildren().add(nameInput);
                    filterInput.setAlignment(Pos.CENTER);

                    nameInput.textProperty().addListener(new ChangeListener<Object>() {
                        public List<Player> searchPlayerByCountry(String country) {
                            List<Player> searchedPlayers = new ArrayList<>();
                            for (Player p : client.getClub().getPlayerList()) {
                                if (p.getCountry().toLowerCase().contains(country.toLowerCase())) {
                                    searchedPlayers.add(p);
                                }
                            }
                            return searchedPlayers;
                        }

                        @Override
                        public void changed(ObservableValue<? extends Object> observableValue, Object o, Object t1) {
                            System.out.println("changed");
                            errorLabel.setText("");
                            playerListPane.getChildren().clear();
                            String country = nameInput.getText();
                            client.getPlayerListHandler().setPlayerList(searchPlayerByCountry(country));
                            if (client.getPlayerListHandler().getPlayerList().size() > 0) {
                                playerListPane.getChildren().add(client.getPlayerListHandler().getPlayerListUi());
                            } else {
                                errorLabel.setText("No such player with this country");
                            }

                        }
                    });
                } else if (currentFilter.equals("POSITION")) {

                    filterInput.getChildren().clear();
                    Label l = new Label("POSITION");
                    l.setId("positionLabel");
                    l.setStyle("-fx-pref-width: 110;" +
                            "-fx-text-fill: white;" +
                            "-fx-font-size: 16;" +
                            "-fx-font-weight: bold;");

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

                    filterInput.setSpacing(0);
                    filterInput.setAlignment(Pos.CENTER_LEFT);
                    filterInput.getChildren().addAll(l, positionSpinner);

                    /**********/
                    positionSpinner.valueProperty().addListener(new ChangeListener<Object>() {
                        @Override
                        public void changed(ObservableValue<? extends Object> observableValue, Object o, Object t1) {
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
                    });
                    /*********/
                } else if (currentFilter.equals("SALARY")) {
                    filterInput.getChildren().clear();
                    TextField from = new TextField();
                    from.setPromptText("From");
                    from.setId("from");

                    TextField to = new TextField();
                    to.setPromptText("To");
                    to.setId("to");

                    from.textProperty().addListener(new ChangeListener<Object>() {
                        @Override
                        public void changed(ObservableValue<? extends Object> observableValue, Object o, Object t1) {
                            System.out.println("changed");
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
                    });

                    to.textProperty().addListener(new ChangeListener<Object>() {
                        @Override
                        public void changed(ObservableValue<? extends Object> observableValue, Object o, Object t1) {
                            System.out.println("changed");
                            errorLabel.setText("");
                            playerListPane.getChildren().clear();
                            double start, end;
                            try {
                                start = Double.parseDouble(from.getText());
                            } catch (Exception e) {
                                if (from.getText().equals("")) {
                                    start = Double.MIN_VALUE;
                                    System.out.println("Set Min");
                                } else {
                                    System.out.println("Error start");
                                    errorLabel.setText("Must be a double");
                                    return;
                                }
                            }
                            try {
                                end = Double.parseDouble(to.getText());
                            } catch (Exception e) {
                                if (to.getText().equals("")) {
                                    System.out.println("Set Max");
                                    end = Double.MAX_VALUE;
                                } else {
                                    System.out.println("Error end");
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
                    });

                    filterInput.getChildren().addAll(from, to);
                    filterInput.setSpacing(20);
                } else if (!currentFilter.equals("")) {

                    /**********/
                    playerListPane.getChildren().clear();
                    if (currentFilter.equals("MAX SALARY")) {
                        try {
                            client.getPlayerListHandler().setPlayerList(client.getClub().getMaxSalaryPlayer());
                            playerListPane.getChildren().add(client.getPlayerListHandler().getPlayerListUi());
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    } else if (currentFilter.equals("MAX AGE")) {
                        try {
                            client.getPlayerListHandler().setPlayerList(client.getClub().getMaxAgePlayer());
                            playerListPane.getChildren().add(client.getPlayerListHandler().getPlayerListUi());
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    } else if (currentFilter.equals("MAX HEIGHT")) {
                        try {
                            client.getPlayerListHandler().setPlayerList(client.getClub().getMaxHeightPlayer());
                            playerListPane.getChildren().add(client.getPlayerListHandler().getPlayerListUi());
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                    /**********/
                }
            }
        });
    }
    @Override
    public void init() {
        Club c = client.getClub();
        client.getPlayerListHandler().setPlayerList(c.getPlayerList());
        playerListPane.getChildren().add(client.getPlayerListHandler().getPlayerListUi());
    }
}
