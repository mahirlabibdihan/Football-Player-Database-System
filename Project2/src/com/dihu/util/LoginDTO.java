package com.dihu.util;

import java.io.Serializable;

public class LoginDTO implements Serializable {
    private String clubName;
    private String password;

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
