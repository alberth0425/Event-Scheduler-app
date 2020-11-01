package src.entities;

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

}
