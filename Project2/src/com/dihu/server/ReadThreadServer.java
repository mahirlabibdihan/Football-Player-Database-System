package com.dihu.server;

import com.dihu.util.*;
import javafx.util.Pair;
import java.util.Map;

public class ReadThreadServer implements Runnable {
    private final Thread thr;
    private final NetworkUtil networkUtil;
    private Server server;

    public ReadThreadServer(Server server, NetworkUtil networkUtil) {
        this.server = server;
        this.networkUtil = networkUtil;
        this.thr = new Thread(this);
        thr.start();
    }

    public void run() {
        try {
            while (true) {
                Object o = networkUtil.read();
                if (o != null) {
                    if(o instanceof String){
                        // Client left from server . So removing it from client list .
                        String c = (String) o;
                        if(server.getClientList().containsKey(c))
                        {
                            server.getClientList().remove(c);
                        }
                    }
                    else if (o instanceof Pair) {
                        Pair pr = (Pair) o;
                        String club = (String) pr.getKey();
                        Club c = server.getDatabase().searchClubByName(club);
                        Player player = (Player) pr.getValue();

                        if (c.searchPlayerByName(player.getName()) == null) {   // Player doesn't exists in the club . That means  the club is trying to buy this player
                            Player p = server.getDatabase().searchPlayerByNameInAuction(player.getName());
                            if(p==null) {
                                networkUtil.write("Player is sold out");
                            }
                            else{
                                p.setClub(c.getName());
                                p.setPrice(0);
                                c.addPlayer(p);
                                server.getDatabase().getAuctionPlayerList().remove(p);
                                networkUtil.write(c);
                            }
                        } else {    // The club is trying to sell this player
                            Player p = c.searchPlayerByName(player.getName());
                            c.removePlayer(p);
                            server.getDatabase().addPlayerForAuction(player);
                            networkUtil.write(c);
                        }

                        // Updating the auctionPlayerList for every client
                        for(Map.Entry<String, NetworkUtil> m : server.getClientList().entrySet()){
                            m.getValue().write(server.getDatabase().getAuctionPlayerList());
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Client left");
        } finally {
            try {
                networkUtil.closeConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}



