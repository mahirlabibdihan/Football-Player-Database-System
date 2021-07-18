package com.dihu.client;

import com.dihu.classes.Club;
import com.dihu.classes.Player;
import com.dihu.controller.Controller;
import com.dihu.util.NetworkUtil;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.List;

public class ReadThreadClient implements Runnable {
    private final Thread thr;
    private final Client client;
    private final NetworkUtil networkUtil;

    public ReadThreadClient(Client client, NetworkUtil networkUtil) {
        this.client = client;
        this.networkUtil = networkUtil;
        this.thr = new Thread(this,"Read Thread Client");
        thr.start();
    }

    public void run() {
        try {
            while (true) {
                Object o = networkUtil.read();
                if (o != null) {
                    if (o instanceof Club) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                    try {
                                        Club c = (Club) o;
                                        client.setClub(c);
                                        client.showMainMenu();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                            }
                        });
                    }
                    else if(o instanceof List){
                        client.setOnSell((List<Player>)o);
                        if(client.getFileName().equals("AuctionPlayerList.fxml")){
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../Scene/AuctionPlayerList.fxml"));
                                    try {
                                        Parent root = loader.load();
                                        Controller controller = (Controller)loader.getController();
                                        controller.setClient(client);
                                        client.getScene().setRoot(root);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                        }
                        System.out.println(client.getOnSell().size());
                    }
                } else{
                    System.out.println("ERROR");
                    //if (o instanceof Club)
                    {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Label l = (Label)client.getScene().getRoot().lookup("#errorLabel");
                                l.setText("Invalid credentials");
                            }
                        });
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Client Reading Error: "+e);
        } finally {
            try {
                networkUtil.closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}



