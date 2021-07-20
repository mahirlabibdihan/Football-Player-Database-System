package com.dihu.server.data;

import com.dihu.util.Club;
import com.dihu.util.Player;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private final String INPUT_FILE_NAME = "src/com/dihu/server/data/players.txt";
    private final String OUTPUT_FILE_NAME = "src/com/dihu/server/data/players.txt";
    private List<Club> clubList;
    private List<Player> onSell;

    public Database(){
        onSell = new ArrayList<Player>();
    }

    public List<Club> getClubList(){
        return clubList;
    }
    public void writeToFile() throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME));
        for (Club c : clubList) {
            for (Player p : c.getPlayerList()) {
                bw.write(
                        p.getName() + ',' +
                                p.getCountry() + ',' +
                                p.getAge() + ',' +
                                p.getHeight() + ',' +
                                p.getClub() + ',' +
                                p.getPosition() + ',' +
                                p.getNumber() + ',' +
                                p.getWeeklySalary() + '\n'
                );
            }
        }
        bw.close();
    }

    public void readFromFile() throws Exception {
        clubList = new ArrayList();
        BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_NAME));
        while (true) {
            String line = br.readLine();
            if (line == null) break;
            addPlayerFromString(line);
        }
        br.close();
    }

    // Getting player information from every line of the input file
    public void addPlayerFromString(String line) {
        String[] tokens = line.split(",");
        Player p = new Player(
                tokens[0],
                tokens[1],
                Integer.parseInt(tokens[2]),
                Double.parseDouble(tokens[3]),
                tokens[4],
                tokens[5],
                Integer.parseInt(tokens[6]),
                Double.parseDouble(tokens[7])
        );
        addPlayer(p);
    }

    public void addPlayer(Player p) {
        // Searching the clublist for the club of the player
        for (int i = 0; i < clubList.size(); i++) {
            if (clubList.get(i).getName().equalsIgnoreCase(p.getClub())) {
                clubList.get(i).addPlayer(p);   // Add the player to the club
                return;
            }
        }

        // If the club is not already added in the list , add the club in the clublist
        Club c = new Club();
        c.setName(p.getClub());
        c.addPlayer(p); // Add the player to the club
        clubList.add(c);
    }

    public Club searchClubByName(String name){
        for(Club c:clubList){
            if(c.getName().equalsIgnoreCase(name)){
                return c;
            }
        }
        return null;
    }

    public Player searchPlayerByName(String name) {
        for (Player p : onSell) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }
    public void addPlayerOnSell(Player p){
        onSell.add(p);
    }
    public List<Player> getOnSell(){
        return onSell;
    }
}
