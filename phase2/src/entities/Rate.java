package entities;

public class Rate implements Savable {
    private int rate;
    private String raterUsername;
    private String speakerRatedUsername;

    /**
     * Constructor for rate class
     * @param rate
     * @param raterUsername
     * @param speakerUsername
     */
    public Rate(int rate, String raterUsername, String speakerUsername){
        this.rate = rate;
        this.raterUsername = raterUsername;
        this.speakerRatedUsername = speakerUsername;
    }

    /**
     * construct rate from a dataEntry.
     *
     * @param dataEntry the savable string that represents a rate
     */
    public Rate(String dataEntry) {
        String[] entries = dataEntry.split(DELIMITER);
        this.rate = Integer.parseInt(entries[0]);
        this.raterUsername = entries[1];
        this.speakerRatedUsername = entries[2];
    }

    /**
     * getter for rater username.
     * @return rater's username
     */
    public String getRaterUsername() {
        return raterUsername;
    }

    /**
     * getter for speaker's username who got rated
     * @return speaker's username
     */
    public String getSpeakerRatedUsername() {
        return speakerRatedUsername;
    }

    public Integer getRate() {
        return rate;
    }

    @Override
    public String toSavableString() {
        return rate + DELIMITER + raterUsername + DELIMITER + speakerRatedUsername;
    }
}
