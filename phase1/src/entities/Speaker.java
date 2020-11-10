package src.entities;

import java.util.List;

public class Speaker extends User {
    private static List<Speaker> allSpeakers;

    public Speaker(String username, String password, String firstName, String lastName) {
        super(username, password, firstName, lastName);
        allSpeakers.add(this);
    }

    public List<Speaker> getAllSpeakers() {
        return allSpeakers;
    }
}
