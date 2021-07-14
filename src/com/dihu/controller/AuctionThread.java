package com.dihu.controller;

import com.dihu.controller.Auction;
import com.dihu.server.Server;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class AuctionThread implements Runnable{
    private Auction auction;
    private Server server;
    private Scene scene;
    public AuctionThread(Server server, Auction auction, Scene scene) {
        this.auction = auction;
        this.server = server;
        this.scene = scene;
        new Thread(this).start();
    }

    @Override
    public void run() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        scene.setRoot(root);
    }
}
