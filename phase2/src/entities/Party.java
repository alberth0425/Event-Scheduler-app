package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Party extends Event{
    public Party(String title, int startingTime, int roomNumber, int duration, int capacity){
        super(title, startingTime, roomNumber, duration, capacity);
    }

    @Override
    public List<String> getSpeakerUNs() {
        return new ArrayList<>();
    }
}
