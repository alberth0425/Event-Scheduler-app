package use_cases;

import entities.*;

public class RaterService {

    /**
     * singleton implementation.
     */
    public static RaterService shared = new RaterService();
    private RaterService() {}

    /**
     * rate speaker from rater with given rate
     *
     * @param speaker the speaker wanted to rate
     * @param rater the rater who give rate to the speaker
     * @param rate the rate given out by rater
     */
    public void rateSpeaker(User speaker, Rater rater, int rate) throws notSpeakerException, rateRepetitionException,
            rateOutOfBoundException {

        if (!(speaker instanceof Speaker)) {
            throw new notSpeakerException();
        }

        if (rate < 1 || rate > 10) {
            throw new rateOutOfBoundException();
        }

        if (rater.speakerIdRated.contains(String.valueOf(speaker.getId()))) {
            throw new rateRepetitionException();
        }

        ((Speaker) speaker).addRate(rate);
        rater.addSpeakerIdRated(speaker.getId());
    }

    // Custom Exception Classes

    public static class raterException extends Exception {}
    public static class rateOutOfBoundException extends raterException {}
    public static class rateRepetitionException extends raterException {}
    public static class notSpeakerException extends raterException {}
}
