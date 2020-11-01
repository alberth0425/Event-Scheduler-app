package src.entities;

import java.util.List;

public class Speaker extends User{
    private static List<Integer> allSpeakers;

    public Speaker(String username, String password){
        super(username, password);
        allSpeakers.add(this.id);
    }

    public List<Integer> getAllSpeakers(){
        return allSpeakers;
    }


}
