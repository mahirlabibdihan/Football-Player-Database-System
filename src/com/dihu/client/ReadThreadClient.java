package com.dihu.client;

import com.dihu.classes.Club;
import com.dihu.classes.Player;
import com.dihu.util.NetworkUtil;
import javafx.application.Platform;

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
                        System.out.println(client.getOnSell().size());
                    }
                } else{
                    client.setLoginStatus(-1);
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



