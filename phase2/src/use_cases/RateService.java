package use_cases;

import entities.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RateService {

    public static RateService shared = new RateService();

    private HashMap<Integer, List<Integer>> raterToSpeakerRated = new HashMap<>();
    private HashMap<Integer, List<Integer>> speakerToRate = new HashMap<>();

    private RateService() {}

    public void rateSpeaker(User speaker, Rater rater, int rate) throws notSpeakerException, rateRepetitionException,
            rateOutOfBoundException {

        if (!(speaker instanceof Speaker)) {
            throw new notSpeakerException();
        }
        if (raterToSpeakerRated.get(rater.getId()).contains(speaker.getId())) throw new rateRepetitionException();

        if (rate < 1 || rate > 10) throw new rateOutOfBoundException();

        Rate newRate = new Rate(rate, rater.getUsername(), speaker.getUsername());

        if (raterToSpeakerRated.containsKey(rater.getId())) {
            raterToSpeakerRated.get(rater.getId()).add(speaker.getId());
        } else {
            List<Integer> a = new ArrayList<>();
            a.add(speaker.getId());
            raterToSpeakerRated.put(rater.getId(), a);
        }

        if (speakerToRate.containsKey(speaker.getId())) {
            speakerToRate.get(speaker.getId()).add(rate);
        } else {
            List<Integer> a = new ArrayList<>();
            a.add(rate);
            raterToSpeakerRated.put(speaker.getId(), a);
        }

    }

    public double getAverageRate(int speakerId) {
        List<Integer> rate = speakerToRate.get(speakerId);
        double sum = 0;
        for (int r: rate) {
            sum += r;
        }
        return sum/rate.size();
    }

    public void setRaterToSpeakerRated(HashMap<Integer, List<Integer>> raterToSpeakerRated) {
        this.raterToSpeakerRated = raterToSpeakerRated;
    }

    public void setSpeakerToRate(HashMap<Integer, List<Integer>> speakerToRate) {
        this.speakerToRate = speakerToRate;
    }



    public static class raterException extends Exception {}
    public static class rateOutOfBoundException extends raterException {}
    public static class rateRepetitionException extends raterException {}
    public static class notSpeakerException extends raterException {}
}
