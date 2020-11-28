package entities;

import java.util.ArrayList;
import java.util.List;

public class Speaker extends User {
    protected List<Integer> allRate = new ArrayList<>();
    protected double averageRate;

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
        this.averageRate = Double.parseDouble(dataEntry.split(DELIMITER)[5]);
    }

    public void addRate(int rate) {
        allRate.add(rate);
        averageRate = getAverageRate();
    }

    public Double getAverageRate() {
        double sum = 0;
        for (int rate: allRate) {
            sum += rate;
        }
        return sum/allRate.size();
    }

    @Override
    public String toSavableString() {
        return id + DELIMITER + username + DELIMITER + password + DELIMITER + firstName + DELIMITER + lastName +
                DELIMITER + averageRate;
    }
}

