/*
    AUTHOR: MAHIR LABIB DIHAN
    GUI IDEA: https://www.youtube.com/watch?v=HV7DtH3J2PU&t=16s
 */
package com.dihu.client;

import com.dihu.classes.Player;
import com.dihu.controller.LoginForm;
import com.dihu.classes.Club;
import com.dihu.controller.MainMenu;
import com.dihu.util.NetworkUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.File;
import java.io.IOException;
import java.util.List;

public class Client extends Application {
    private List<Player> onSell;
    private Club club;
    private NetworkUtil networkUtil;
    private Scene scene;
    private Stage stage;
    public String fileName;
    int loginStatus;


    public Client(){
        loginStatus = 0;
    }
    public String getFileName() { return fileName; }
    public void setFileName(String fileName){this.fileName = fileName;}
    public void setClub(Club club){
        this.club = club;
    }
    public Club getClub(){
        return club;
    }
    public NetworkUtil getNetworkUtil(){
        return networkUtil;
    }
    public Scene getScene(){
        return scene; }
    public void setOnSell(List<Player> onSell){
        this.onSell = onSell;
    }
    public List<Player> getOnSell(){
        return onSell;
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        showLoginForm();
    }
    public void addCss(Scene scene){
        String application = this.getClass().getResource("../CSS/Application.css").toExternalForm();
        String loginForm = this.getClass().getResource("../CSS/LoginForm.css").toExternalForm();
        String mainMenu = this.getClass().getResource("../CSS/MainMenu.css").toExternalForm();
        String searchPlayersMenu = this.getClass().getResource("../CSS/PlayersMenu.css").toExternalForm();
        String playerList = this.getClass().getResource("../CSS/PlayerList.css").toExternalForm();
        scene.getStylesheets().addAll(application,loginForm,mainMenu,searchPlayersMenu,playerList);
    }

    public void showLoginForm() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../scene/LoginForm.fxml"));
        Parent root = loader.load();

        LoginForm controller = loader.getController();
        controller.setClient(this);


        // Scene
        scene = new Scene(root, 600, 750);
        scene.setFill(Color.TRANSPARENT);
        addCss(scene);


        // Stage
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("Player Database");
        stage.setScene(scene);
        stage.show();

        root.requestFocus();
    }
    public void connectToServer() throws Exception {
        String serverAddress = "127.0.0.1";
        int serverPort = 33333;
        networkUtil = new NetworkUtil(serverAddress, serverPort);
        new ReadThreadClient(this,networkUtil);
    }
    public void showMainMenu() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Scene/MainMenu.fxml"));
        Parent root = loader.load();
        MainMenu controller = loader.getController();
        controller.setClient(this);
        scene.setRoot(root);
    }
    public void  showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(msg);
        alert.setHeaderText(msg);
        alert.setContentText(msg);
        alert.showAndWait();
    }
    public void setLoginStatus(int l){
        loginStatus = l;
    }
    public int getLoginStatus(){
        return loginStatus;
    }
    public static void main(String[] args) throws Exception {
        launch(args);
    }
    public Player searchPlayerByName(String name) {
        for (Player p : onSell) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }
}
