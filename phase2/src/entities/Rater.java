package entities;

import java.util.ArrayList;
import java.util.List;

public class Rater extends User {
    //public List<Integer> speakerIdRated = new ArrayList<>();


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

    /**
     * construct organizer from a dataEntry.
     *
     * @param dataEntry the savable string that represents rater
     */
    public Rater(String dataEntry) {
        super(dataEntry);
        // convert from list of strings to list of integers for speakerIdRated.
        //String[] allId = dataEntry.split(DELIMITER)[5].split(",");
        //for (String rate: allId) {
        //    this.speakerIdRated.add(Integer.parseInt(rate));
        //}
    }

    //public List<Integer> getSpeakerIdRated() {
    //    return speakerIdRated;
    //}

    //public void addSpeakerIdRated(int id) {
    //    speakerIdRated.add(id);
    //}

    //**
     //* turn the information of this user into a savable string.
     //*
     //* @return a savable string in the order of: id, username, password, firstname, lastname
     //*/
    //@Override
    //public String toSavableString() {
        // Make the list of speakerIdRated a string with commas in between to separate.
        //StringBuilder allIdRated = new StringBuilder();
        //for (Integer rate: speakerIdRated){
        //    allIdRated.append(rate + ",");
        //}
    //    return id + DELIMITER + username + DELIMITER + password + DELIMITER + firstName + DELIMITER + lastName;
   // }
}
