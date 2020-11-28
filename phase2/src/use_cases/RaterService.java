package use_cases;

import controllers.RaterController;
import entities.Rater;
import entities.Speaker;
import entities.User;

public class RaterService extends AuthService{

    public static RaterService shared = new RaterService();

    private RaterService() {
        super();
    }


    public void rateSpeaker(User speaker, Rater rater, int rate) throws notSpeakerException, rateRepetitionException, rateOutOfBoundException {

        if (!(speaker instanceof Speaker)) {
            throw new notSpeakerException();
        }
        if (rater.getSpeakerIdRated().contains(speaker.getId())) throw new rateRepetitionException();

        if (rate < 1 || rate > 10) throw new rateOutOfBoundException();

        ((Speaker) speaker).addRate(rate);

        rater.addSpeakerIdRated(speaker.getId());

    }

    public static class raterException extends Exception {}
    public static class rateOutOfBoundException extends raterException {}
    public static class rateRepetitionException extends raterException {}
    public static class notSpeakerException extends raterException {}
}
