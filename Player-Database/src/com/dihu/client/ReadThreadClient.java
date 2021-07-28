package com.dihu.client;

import com.dihu.util.*;
import javafx.application.Platform;
import javafx.scene.control.Label;
import java.util.List;

public class ReadThreadClient implements Runnable {
    private final Thread thr;
    private final Client client;

    public ReadThreadClient(Client client) {
        this.client = client;
        this.thr = new Thread(this,"Read Thread Client");
        thr.start();
    }

    public void run() {
        try {
            while (true) {
                Object o = client.getNetworkUtil().read();
                if (o != null) {
                    // Client's club
                    if (o instanceof Club) {
                        Club c = (Club) o;
                        client.setClub(c);
                        if(!client.getUi().getCurrentScene().getFileName().equals("ClubMenu")) {
                            client.getUi().next();
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        client.updateScene();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }

                    // Auction player list
                    else if(o instanceof List){
                            List<Player> onSell = (List<Player>)o;
                            for(int i=0;i<onSell.size();i++){
                                if(onSell.get(i).getClub().equals(client.getClub().getName())){
                                    onSell.remove(i--);
                                }
                            }
                            client.getPlayerListHandler().setAuctionPlayerList(onSell);
                            if(client.getUi().getCurrentScene().getFileName().equals("AuctionPlayerList")){
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            client.updateScene();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                    }
                    // Showing error
                    else if(o instanceof String){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Label l = (Label)client.getScene().getRoot().lookup("#errorLabel");
                                l.setText((String)o);
                            }
                        });
                        if(client.getUi().getCurrentScene().getFileName().equals("LoginForm")) {
                            client.getNetworkUtil().closeConnection();
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Connection to server lost");
        } finally {
            try {
                client.getNetworkUtil().closeConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}



