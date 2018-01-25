package main.java.com.xjf.repository;

import java.util.Date;

public class User {
    private String tUserid;

    private String tUsername;

    private String tPassword;

    private Date tCreatedate;

    private Integer tSex;

    public String gettUserid() {
        return tUserid;
    }

    public void settUserid(String tUserid) {
        this.tUserid = tUserid == null ? null : tUserid.trim();
    }

    public String gettUsername() {
        return tUsername;
    }

    public void settUsername(String tUsername) {
        this.tUsername = tUsername == null ? null : tUsername.trim();
    }

    public String gettPassword() {
        return tPassword;
    }

    public void settPassword(String tPassword) {
        this.tPassword = tPassword == null ? null : tPassword.trim();
    }

    public Date gettCreatedate() {
        return tCreatedate;
    }

    public void settCreatedate(Date tCreatedate) {
        this.tCreatedate = tCreatedate;
    }

    public Integer gettSex() {
        return tSex;
    }

    public void settSex(Integer tSex) {
        this.tSex = tSex;
    }
}