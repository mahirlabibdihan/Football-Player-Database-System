package com.dihu.server;

import com.dihu.classes.Club;
import com.dihu.util.NetworkUtil;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {
    private List<NetworkUtil> clientList;
    public Map<String, String> clubMap;
    private Database database;

    Server() throws Exception {
        clientList = new ArrayList<>();
        init();
        try {
            ServerSocket serverSocket = new ServerSocket(33333);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                serve(clientSocket);
            }
        } catch (Exception e) {
            System.out.println("Server starts:" + e);
        }
    }
    public void serve(Socket clientSocket) throws IOException {
        NetworkUtil networkUtil = new NetworkUtil(clientSocket);
        clientList.add(networkUtil);
        new ReadThreadServer(this, networkUtil);
        System.out.println("Client Connected");
    }
    public void init() throws Exception {
        database = new Database();
        database.readFromFile();

        clubMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);;
        for(Club c:database.getClubList()){
            clubMap.put(c.getName(), "admin");
        }
    }
    public List<NetworkUtil> getClientList(){
        return clientList;
    }
    public Database getDatabase(){
        return database;
    }

    public Map<String, String> getClubMap() {
        return clubMap;
    }

    public static void main(String[] args) throws Exception {
//        String musicFile = "src/com/dihu/bg.mp3";     // For example
//
//        Media sound = new Media(new File(musicFile).toURI().toString());
//        MediaPlayer mediaPlayer = new MediaPlayer(sound);
//        mediaPlayer.play();
//        mediaPlayer.setAutoPlay(true);
        new Server();
    }
}
