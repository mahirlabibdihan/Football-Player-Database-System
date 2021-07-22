/*
    AUTHOR: MAHIR LABIB DIHAN
    GUI IDEA: https://www.youtube.com/watch?v=HV7DtH3J2PU&t=16s
 */
package com.dihu.client;

import com.dihu.client.fx.controllers.*;
import com.dihu.util.Club;
import com.dihu.util.NetworkUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Client extends Application {
    private Club club;
    private Scene scene;
    private UiStructure ui;
    private NetworkUtil networkUtil;
    private PlayerListHandler playerListHandler;

    public Client(){
        ui = new UiStructure();
        playerListHandler = new PlayerListHandler(this);
    }

    public UiStructure getUi() {
        return ui;
    }

    public PlayerListHandler getPlayerListHandler() {
        return playerListHandler;
    }

    public void setClub(Club club){
        this.club = club;
        playerListHandler.setPlayerList(club.getPlayerList());
    }
    public Club getClub(){
        return club;
    }
    public NetworkUtil getNetworkUtil(){
        return networkUtil;
    }
    public Scene getScene(){
        return scene; }
    @Override
    public void start(Stage primaryStage){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fx/scenes/"+ui.getCurrentScene().getFileName()+".fxml"));
            Parent root = loader.load();

            Controller controller = loader.getController();
            controller.setClient(this);

            scene = new Scene(root, 600, 750);
            scene.setFill(Color.TRANSPARENT);

            primaryStage.initStyle(StageStyle.TRANSPARENT);
            primaryStage.setTitle("Player Database");
            primaryStage.setScene(scene);
            primaryStage.show();
            root.requestFocus();

            primaryStage.focusedProperty().addListener((ov, onHidden, onShown) -> primaryStage.getScene().getRoot().requestFocus());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void connectToServer() {
        String serverAddress = "127.0.0.1";
        int serverPort = 33333;
        try {
            networkUtil = new NetworkUtil(serverAddress, serverPort);
            new ReadThreadClient(this,networkUtil);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void updateScene() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fx/scenes/"+ui.getCurrentScene().getFileName()+".fxml"));
            Parent root = loader.load();
            Controller controller = loader.getController();
            controller.setClient(this);
            controller.init();
            scene.setRoot(root);
            root.requestFocus();
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    public static void main(String[] args){
        launch(args);
    }
}
