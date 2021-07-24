package com.dihu.server;

import com.dihu.util.*;
import java.util.Map;

public class ReadThreadServer implements Runnable {
    private final Thread thr;
    private String clubName;
    private Server server;

    public ReadThreadServer(Server server, String clubName) {
        this.server = server;
        this.clubName = clubName;
        this.thr = new Thread(this);
        thr.start();
    }

    public void run() {
        try {
            while (true) {
                Object o = server.getClientMap().get(clubName).read();
                if (o != null) {
                    // BUY/SELL
                    if (o instanceof Player) {
                        Player p = (Player) o;
                        Club c = server.getDatabase().searchClubByName(p.getClub());

                        if (c.searchPlayerByName(p.getName()) == null) {   // Player doesn't exists in the club . That means  the club is trying to buy this player
                            Player p1 = server.getDatabase().searchPlayerByNameInAuction(p.getName());
                            if(p1==null) {
                                server.getClientMap().get(clubName).write("Player is sold out");
                            }
                            else{
                                p1.setClub(c.getName());
                                p1.setPrice(0);
                                c.addPlayer(p1);
                                server.getDatabase().getAuctionPlayerList().remove(p1);
                                server.getClientMap().get(clubName).write(c);
                            }
                        } else {    // The club is trying to sell this player
                            Player p2 = c.searchPlayerByName(p.getName());
                            c.removePlayer(p2);
                            server.getDatabase().addPlayerForAuction(p);
                            server.getClientMap().get(clubName).write(c);
                        }

                        // Updating the auctionPlayerList for every client
                        for(Map.Entry<String, NetworkUtil> m : server.getClientMap().entrySet()){
                            m.getValue().write(server.getDatabase().getAuctionPlayerList());
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Client left");
        } finally {
            try {
                server.getClientMap().get(clubName).closeConnection();
                server.getClientMap().remove(clubName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}



