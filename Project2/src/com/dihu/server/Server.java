package com.dihu.server;

import com.dihu.server.data.Database;
import com.dihu.util.*;
import java.net.*;
import java.util.*;

public class Server {
    private Map<String,NetworkUtil> clientList;
    public Map<String, String> clubMap;
    private Database database;

    Server() throws Exception {
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
    public void serve(Socket clientSocket) throws Exception {
        NetworkUtil networkUtil = new NetworkUtil(clientSocket);
        Object o = networkUtil.read();
        if (o != null) {
            if (o instanceof LoginDTO) {
                LoginDTO loginDTO = (LoginDTO) o;
                try {
                    if (clubMap.get(loginDTO.getClubName()).equals(loginDTO.getPassword())) {
                        Club c = database.searchClubByName(loginDTO.getClubName());
                        clientList.put(c.getName(),networkUtil);
                        networkUtil.write(c);
                        networkUtil.write(database.getOnSell());
                    } else {
                        networkUtil.write(null);
                    }
                } catch (Exception e) {
                    networkUtil.write(null);
                }
            }
        }
        new ReadThreadServer(this, networkUtil);
        System.out.println("Client Connected");
    }
    public void init() throws Exception {
        database = new Database();
        database.readFromFile();

        clientList = new HashMap<>();
        clubMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);;
        for(Club c:database.getClubList()){
            clubMap.put(c.getName(), "admin");
        }
    }
    public Map<String,NetworkUtil> getClientList(){
        return clientList;
    }
    public Database getDatabase(){
        return database;
    }
    public Map<String, String> getClubMap() {
        return clubMap;
    }
    public static void main(String[] args) throws Exception {
        new Server();
    }
}
