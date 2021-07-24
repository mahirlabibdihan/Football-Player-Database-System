package com.dihu.server;

import com.dihu.util.*;
import javafx.util.Pair;
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
                    if (o instanceof Pair) {
                        Pair pr = (Pair) o;
                        String club = (String) pr.getKey();
                        Club c = server.getDatabase().searchClubByName(club);
                        Player player = (Player) pr.getValue();

                        if (c.searchPlayerByName(player.getName()) == null) {   // Player doesn't exists in the club . That means  the club is trying to buy this player
                            Player p = server.getDatabase().searchPlayerByNameInAuction(player.getName());
                            if(p==null) {
                                server.getClientMap().get(clubName).write("Player is sold out");
                            }
                            else{
                                p.setClub(c.getName());
                                p.setPrice(0);
                                c.addPlayer(p);
                                server.getDatabase().getAuctionPlayerList().remove(p);
                                server.getClientMap().get(clubName).write(c);
                            }
                        } else {    // The club is trying to sell this player
                            Player p = c.searchPlayerByName(player.getName());
                            c.removePlayer(p);
                            server.getDatabase().addPlayerForAuction(player);
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



