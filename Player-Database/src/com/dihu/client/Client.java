/*
    AUTHOR: MAHIR LABIB DIHAN
    GUI IDEA: https://www.youtube.com/watch?v=HV7DtH3J2PU&t=16s
*/

/*

LOGIN FORM: First of all login form will be shown to the user.
            To enter into the player database system user have to enter valid club name and password.
            When user hit the login button the client will connect to server using the ip address and port of the server.
            The the club name and password given by user will go to the server. Server will validate the credentials.
            If the club name and password is valid then server will sent the corresponding club object to the client.
            Also the auction player list will be sent to client as well.
            And the user will prompt to the main menu. If the credentials are invalid then error will be shown to user.
            Once the user has made it to the main menu, he can now do operations on the players of that club.

MAIN MENU:  There are 4 options in the menu. Players, Club, Transfer and Log out.

PLAYERS:    In the players menu all the players of the club will be shown.
            Under  the players list there is feature of filtering the list according to different attributes like player name,country,position,salary etc.
            On clicking a players name, player's card with full details will be show to the user.

CLUB:       In the club menu there is some info of the club like total yearly salary,country wise player count.

TRANSFER:   In the transfer menu users can change the player list of the club by selling/buying.
SELL:       To sell a player user need to choose from available players in the club and set a price tag for that player(Price must be a valid double number).
            Once the user hit the sell button price will be set in the Player object. The club name and the player object will be sent to the server.
            Server will remove the player from that club in the database and will add the player in auction player list. Then server will send the changed club to the client.
            Also server will send the new auction player list to every client.
BUY:        To buy a player user needs to go to buy menu. There is the list of players on auction. User needs to choose a player from that.
            Then the player's details will be shown. Under that players price will be shown. Users need to click the price to buy player.
            Once the user do this the club name and the player object will be sent to server like before.
            And server will remove the player form auction and add the player to that club and send the changed club to the client.
            The new auction player list to every client as well.

LOG OUT:    If the user wishes get out of the the database system he needs to click log out button.

*/
package com.dihu.client;

import com.dihu.client.fx.controllers.*;
import com.dihu.util.Club;
import com.dihu.util.NetworkUtil;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
            primaryStage.getIcons().add(new Image(getClass().getResource("fx/assets/images/icon.png").toExternalForm()));
            root.requestFocus();

            primaryStage.focusedProperty().addListener(new ChangeListener<Boolean>()
            {
                @Override
                public void changed(ObservableValue<? extends Boolean> ov, Boolean onHidden, Boolean onShown)
                {
                    primaryStage.getScene().getRoot().requestFocus();
                }
            });

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
