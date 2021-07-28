package com.dihu.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Club implements Serializable {
    private String name;    // Name of the club
    private String password;
    private List<Player> playerList;    // List of players under a club

    public Club() {
        playerList = new ArrayList();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void addPlayer(Player p) {
        playerList.add(p);     // Adding player to the list
    }

    public void removePlayer(Player p){
        playerList.remove(p);
    }

    public List<Player> getMaxSalaryPlayer() {
        List<Player> searchedPlayers = new ArrayList();
        double max = 0;

        // Finding the max salary
        for (Player p : playerList) {
            max = Math.max(p.getWeeklySalary(), max);
        }

        // Searching for the players with max salary
        for (Player p : playerList) {
            if (p.getWeeklySalary() == max) {
                searchedPlayers.add(p);
            }
        }
        return searchedPlayers;
    }

    public List<Player> getMaxHeightPlayer() {
        List<Player> searchedPlayers = new ArrayList();
        double max = 0;

        // Finding the max height
        for (Player p : playerList) {
            max = Math.max(p.getHeight(), max);
        }

        // Searching for the players with max height
        for (Player p : playerList) {
            if (p.getHeight() == max) {
                searchedPlayers.add(p);
            }
        }
        return searchedPlayers;
    }

    public List<Player> getMaxAgePlayer() {
        List<Player> searchedPlayers = new ArrayList();
        int max = 0;
        // Finding the max age
        for (Player p : playerList) {
            max = Math.max(p.getAge(), max);
        }

        // Searching for the players with max age
        for (Player p : playerList) {
            if (p.getAge() == max) {
                searchedPlayers.add(p);
            }
        }
        return searchedPlayers;
    }

    public double getTotalYearlySalary() {
        double total = 0;

        // Adding the weekly salary of all players
        for (Player p : playerList) {
            total += p.getWeeklySalary();
        }
        return 365 * total / 7;     // Total yearly salary
    }

    public void getCountryWisePlayerCount(Map<String, Integer> count){
        for (Player p : playerList) {
            if (count.containsKey(p.getCountry())) {
                count.put(p.getCountry(), count.get(p.getCountry()) + 1);
            } else {
                count.put(p.getCountry(), 1);
            }
        }
    }

    public List<Player> searchPlayerBySalaryRange(double start, double end) {
            List<Player> searchedPlayers=new ArrayList<>();
            for (Player p : playerList) {
                if (p.getWeeklySalary() >= start && p.getWeeklySalary() <= end) {
                    searchedPlayers.add(p);
                }
            }
        return searchedPlayers;
    }

    public List<Player> searchPlayerByPosition(String position) {
            List<Player> searchedPlayers=new ArrayList<>();
            for (Player p : playerList) {
                if (p.getPosition().equalsIgnoreCase(position)) {
                    searchedPlayers.add(p);
                }
            }
            return searchedPlayers;
    }

    public List<Player> searchPlayerByCountry(String country) {
        List<Player> searchedPlayers=new ArrayList<>();
                for (Player p : playerList) {
                    if (p.getCountry().equalsIgnoreCase(country)) {
                        searchedPlayers.add(p);
                    }
                }
        return searchedPlayers;
        }

    public Player searchPlayerByName(String name) {
            for (Player p : playerList) {
                if (p.getName().equalsIgnoreCase(name)) {
                    return p;
                }
            }
        return null;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
