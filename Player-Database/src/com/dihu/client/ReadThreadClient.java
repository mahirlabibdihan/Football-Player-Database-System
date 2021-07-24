package com.dihu.client;

import com.dihu.util.*;
import javafx.application.Platform;
import javafx.scene.control.Label;
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
                    // Client's club
                    if (o instanceof Club) {
                        Club c = (Club) o;
                        client.setClub(c);
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

                    // Auction player list
                    else if(o instanceof List){
                            List<Player> onSell = (List<Player>)o;
                            for(int i=0;i<onSell.size();i++){
                                System.out.println(onSell.get(i).getName());
                                System.out.println(onSell.get(i).getClub()+" "+client.getClub().getName());
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
                    else if(o instanceof String){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Label l = (Label)client.getScene().getRoot().lookup("#errorLabel");
                                l.setText((String)o);
                            }
                        });
                        if(client.getUi().getCurrentScene().getFileName().equals("LoginForm"))networkUtil.closeConnection();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Connection to server lost");
        } finally {
            try {
                networkUtil.closeConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}



