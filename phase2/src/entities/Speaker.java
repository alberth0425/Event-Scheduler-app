package entities;

import java.util.ArrayList;
import java.util.List;

public class Speaker extends User {
    protected List<Double> allRate = new ArrayList<>();

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
     * construct speaker from a dataEntry.
     *
     * @param dataEntry the savable string that represents speaker
     */
    public Speaker(String dataEntry) {
        super(dataEntry);
    }

    public void addRate(double rate) {
        allRate.add(rate);
    }

    public double getAverageRate() {
        double sum = 0;
        for (double rate: allRate) {
            sum += rate;
        }
        return sum/allRate.size();
    }
}

