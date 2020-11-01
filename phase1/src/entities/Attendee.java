package src.entities;

import java.util.List;

public class Attendee extends User{
    private static List<Integer> allAttendees;

    public Attendee(String username, String password){
        super(username, password);
        allAttendees.add(this.id);
    }

    public List<Integer> getAllAttendees(){
        return allAttendees;
    }
}
