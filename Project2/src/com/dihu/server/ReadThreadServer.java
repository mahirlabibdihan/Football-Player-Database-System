package com.dihu.server;

import com.dihu.classes.Club;
import com.dihu.classes.Player;
import com.dihu.util.LoginDTO;
import com.dihu.util.NetworkUtil;
import javafx.util.Pair;

import java.io.IOException;

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
            networkUtil.write(server.getDatabase().getOnSell());
            while (true) {
                Object o = networkUtil.read();
                System.out.println("Server Received");
                if (o != null) {
                    if (o instanceof LoginDTO) {
                        LoginDTO loginDTO = (LoginDTO) o;
                        try{
                            if(server.getClubMap().get(loginDTO.getClubName()).equalsIgnoreCase(loginDTO.getPassword())){
                                Club c = server.getDatabase().searchClubByName(loginDTO.getClubName());
                                networkUtil.write(c);
                            }else{
                                loginDTO.setStatus("Incorrect password");
                                networkUtil.write(null);
                                //System.out.println("Incorrect password");
                            }
                        }catch(Exception e){
                            loginDTO.setStatus("No such club!");
                            networkUtil.write(null);
                        }
                    }
                    else if(o instanceof Pair){
                        Pair pr = (Pair) o;
                        String club = (String) pr.getKey();
                        Club c = server.getDatabase().searchClubByName(club);
                        Player player = (Player) pr.getValue();

                        System.out.println("NAME: "+player);
                        if(c.searchPlayerByName(player.getName())==null){
                            Player p = server.getDatabase().searchPlayerByName(player.getName());
                            p.setClub(c.getName());
                            c.addPlayer(p);
                            server.getDatabase().getOnSell().remove(p);
                        }
                        else{
                            Player p = c.searchPlayerByName(player.getName());
                            System.out.println("FOUND: "+p.getName());
                            c.removePlayer(p);
                            System.out.println("Removed: "+p.getName());
                            server.getDatabase().addPlayerOnSell(player);
                            System.out.println("Auctioned: "+p.getName());
                        }

                        for(int i=0;i<server.getClientList().size();i++){
                            try{
                                System.out.println(c.getPlayerList().size());
                                // server.getClientList().get(i).write(c);
                                server.getClientList().get(i).write(server.getDatabase().getOnSell());
                            }catch(Exception e){
                                System.out.println(server.getClientList().size());
                                server.getClientList().remove(i);
                                i--;
                                System.out.println("REMOVED: "+server.getClientList().size());
                            }
                        }
                        System.out.println("Sold");
                    }
                }else{
                    System.out.println("Server reading error: NULL POINTER");
                }
            }
        } catch (Exception e) {
            System.out.println("Server reading error: "+e);
        } finally {
            try {
                networkUtil.closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}



