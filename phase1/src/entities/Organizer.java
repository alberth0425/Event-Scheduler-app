package src.entities;

import java.util.List;

public class Organizer extends User{
    private static List<Integer> allOrganizers;

    public Organizer(String username, String password){
        super(username, password);
        allOrganizers.add(this.id);
    }

    public List<Integer> getAllOrganizers(){
        return allOrganizers;
    }
}
