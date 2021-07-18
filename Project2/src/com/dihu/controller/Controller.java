package com.dihu.controller;

import com.dihu.client.Client;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Controller {
    protected  Client client;
    protected Controller prevController;
    protected String prevFXML;
    public void setClient(Client client) {
        this.client = client;
        System.out.println("CLIENT SET");
    }
    private double x,y;
    @FXML
    public void drag(MouseEvent mouseEvent) {
        Stage stage=(Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.setX(mouseEvent.getScreenX()-x);
        stage.setY(mouseEvent.getScreenY()-y);
    }

    @FXML
    public void press(MouseEvent mouseEvent) {
        x = mouseEvent.getSceneX();
        y = mouseEvent.getSceneY();
    }

    @FXML
    public void close(MouseEvent mouseEvent) {
        Stage stage=(Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.close();
        Platform.exit();
        System.exit(0);
    }

    @FXML
    public void minimize(MouseEvent mouseEvent) {
        Stage stage=(Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    public void setPrevController(Controller prevController){
        this.prevController = prevController;
    }

    public Controller getPrevController(){
        return prevController;
    }

    public void setPrevFXML(String prevFXML){
        System.out.println(prevFXML);
        this.prevFXML = prevFXML;
    }

    public String getPrevFXML(){
        return prevFXML;
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(prevFXML));
        Parent root = loader.load();

        Controller controller = (Controller)loader.getController();
        controller.setClient(client);

        ((Node)mouseEvent.getSource()).getScene().setRoot(root);
    }
}
