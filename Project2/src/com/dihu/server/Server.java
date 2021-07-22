package com.dihu.server;

import com.dihu.server.data.Database;
import com.dihu.util.*;
import java.net.*;
import java.util.*;

public class Server {
    private Map<String,NetworkUtil> clientList;
    private Map<String, String> clubMap;
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
    private void serve(Socket clientSocket) throws Exception {
        NetworkUtil networkUtil = new NetworkUtil(clientSocket);
        Object o = networkUtil.read();
        if (o != null) {
            if (o instanceof LoginDTO) {
                LoginDTO loginDTO = (LoginDTO) o;
                try {
                    if (clubMap.get(loginDTO.getClubName()).equals(loginDTO.getPassword())) {
                        Club c = database.searchClubByName(loginDTO.getClubName());
                        if(clientList.containsKey(c.getName())){
                            networkUtil.write("You are already logged in");
                            networkUtil.closeConnection();
                        }else{
                            clientList.put(c.getName(),networkUtil);
                            networkUtil.write(c);
                            networkUtil.write(database.getOnSell());
                            new ReadThreadServer(this, networkUtil);
                            System.out.println("Client Connected");
                        }
                    } else {
                        networkUtil.write("Incorrect password");
                        networkUtil.closeConnection();
                    }
                } catch (Exception e) {
                    networkUtil.write("No club with this name");
                    networkUtil.closeConnection();
                }
            }
        }
    }
    private void init() throws Exception {
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
    private Map<String, String> getClubMap() {
        return clubMap;
    }
    public static void main(String[] args) throws Exception { new Server(); }
}
