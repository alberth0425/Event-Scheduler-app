package src.entities;

import java.util.Map;

public class User {

    protected int id;
    protected String password;
    protected static int numUser;
    protected String username;

    public User(String username, String password){
        this.username = username;
        this.password = password;
        numUser += 1;
        this.id = numUser;
    }

    public void setPassword(String newPassword){
        this.password = newPassword;
    }

    public String getPassword(){
        return password;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String newUsername){
        this.username = newUsername;
    }

    public int getId(){
        return id;
    }


}
