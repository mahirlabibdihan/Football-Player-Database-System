/*

            Server will read players data from a text file and store it to the database according to the club.
            Every club has a playerlist and all the players of the database will be divided into them.
            Server needs to be started at a specific port.
            And password will be set for every club. Default password is "admin".
            Club name and password will be mapped together using a ConcurrentHashMap to validate credentials.
            Server will also contain a map of club name and its corresponding networkUtil.
            Once a client connects to the server with valid credentials, server will create a networkUtil for that and map that to that client's club name.
            And will start a read thread to receive data from that client.
            For each client, server will create a network util and a read thread.
            If client got disconnected from server, server will remove that client from clientMap.

 */

package com.dihu.server;

import com.dihu.server.data.Database;
import com.dihu.util.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private Map<String,NetworkUtil> clientMap;
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
    private void login(LoginDTO loginDTO,NetworkUtil networkUtil) throws Exception{
        try {
            Club c = database.searchClubByName(loginDTO.getClubName());
            if (c.getPassword().equals(loginDTO.getPassword())) {     // This line will create exception if there is no club with this name
                if(clientMap.containsKey(c.getName())){    // All the clients of the server should be different clubs
                    networkUtil.write("You are already logged in");
                    networkUtil.closeConnection();
                }else{     // Successful login
                    clientMap.put(c.getName(),networkUtil);

                    // Sending club to client
                    networkUtil.write(c);

                    // Sending auctionPlayerList to client
                    networkUtil.write(database.getAuctionPlayerList());
                    new ReadThreadServer(this, c.getName());
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
    private void serve(Socket clientSocket) {
        try{
            NetworkUtil networkUtil = new NetworkUtil(clientSocket);
            // Taking clubname and password from client
            Object o = networkUtil.read();
            if (o != null) {
                if (o instanceof LoginDTO) {
                    LoginDTO loginDTO = (LoginDTO) o;
                    login(loginDTO,networkUtil);
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

        // Club password set
        for(Club c:database.getClubList()){
            c.setPassword("admin");
        }

        // Connected clients
        clientMap = new ConcurrentHashMap<>();  // For multi-threading
    }
    public Map<String,NetworkUtil> getClientMap(){
        return clientMap;
    }
    public Database getDatabase(){
        return database;
    }
    public static void main(String[] args) { new Server(); }
}
