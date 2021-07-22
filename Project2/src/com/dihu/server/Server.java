package com.dihu.server;

import com.dihu.server.data.Database;
import com.dihu.util.*;
import java.net.*;
import java.util.*;

public class Server {
    private Map<String,NetworkUtil> clientList;
    private Map<String, String> clubMap;
    private Database database;

    Server() {
        init();
        try {
            ServerSocket serverSocket = new ServerSocket(33333);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                serve(clientSocket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void login(LoginDTO loginDTO){

    }
    private void serve(Socket clientSocket) {
        try{
            NetworkUtil networkUtil = new NetworkUtil(clientSocket);
            // Taking clubname and password from client
            Object o = networkUtil.read();
            if (o != null) {
                if (o instanceof LoginDTO) {
                    LoginDTO loginDTO = (LoginDTO) o;
                    try {
                        if (clubMap.get(loginDTO.getClubName()).equals(loginDTO.getPassword())) {     // This line will create exception if there is no club with this name
                            Club c = database.searchClubByName(loginDTO.getClubName());
                            if(clientList.containsKey(c.getName())){    // All the clients of the server should be different clubs
                                networkUtil.write("You are already logged in");
                                networkUtil.closeConnection();
                            }else{     // Successful login
                                clientList.put(c.getName(),networkUtil);
                                networkUtil.write(c);
                                networkUtil.write(database.getAuctionPlayerList());
                                new ReadThreadServer(this, networkUtil);
                                System.out.println("Client Connected");
                            }
                        } else {    // Password doesn't match with the one in the map
                            networkUtil.write("Incorrect password");
                            networkUtil.closeConnection();
                        }
                    } catch (Exception e) {
                        networkUtil.write("No club with this name");
                        networkUtil.closeConnection();
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    private void init() {
        // Player database
        database = new Database();
        try{
            database.readFromFile();
        }catch(Exception e){
            e.printStackTrace();
        }

        // Connected client list
        clientList = new HashMap<>();

        // Club name and password map
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
    public static void main(String[] args) { new Server(); }
}
