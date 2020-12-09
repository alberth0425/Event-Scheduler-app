package entities;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Rater extends User {
    public List<String> speakerIdRated = new ArrayList<>();


    /**
     * the constructor for rater class.
     *
     * @param username the username of this rater
     * @param password the password this rater needs to login
     * @param firstName the firstname of this rater
     * @param lastName the lastname of this rater
     */
    public Rater(String username, String password, String firstName, String lastName) {
        super(username, password, firstName, lastName);
    }

    public List<String> getSpeakerIdRated() {
        return speakerIdRated;
    }

    public void setSpeakerIdRated(List<String> speakerIdRated) {
        this.speakerIdRated = speakerIdRated;
    }

    public void addSpeakerIdRated(int id) {
        speakerIdRated.add(String.valueOf(id));
    }

}
