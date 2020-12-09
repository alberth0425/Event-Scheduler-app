package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Speaker extends User {
    protected List<String> allRate = new ArrayList<>();

    /**
     * the constructor for speaker class.
     *
     * @param username the username of this speaker
     * @param password the password this speaker needs to login
     * @param firstName the firstname of this speaker
     * @param lastName the lastname of this speaker
     */
    public Speaker(String username, String password, String firstName, String lastName) {
        super(username, password, firstName, lastName);
    }


    /**
     * add rate to the allRate list.
     *
     * @param rate the rate given
     */
    public void addRate(int rate) {
        allRate.add(String.valueOf(rate));

    }

    /**
     * return the average rate the speaker received.
     *
     * @return average rate the speaker received
     */
    public Double getAverageRate() {
        double sum = 0;
        for (String r : allRate) {
            sum += Integer.parseInt(r);
        }
        return sum/allRate.size();
    }

    /**
     * turn the information of this speaker into a savable string.
     *
     * @return a savable string that contains all the information of this speaker
     */
    @Override
    public String toSavableString() {
        return id + DELIMITER + username + DELIMITER + password + DELIMITER + firstName + DELIMITER + lastName +
                DELIMITER + String.join("|", allRate);
    }
}

