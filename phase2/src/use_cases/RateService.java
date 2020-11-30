package use_cases;

import entities.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RateService {

    public static RateService shared = new RateService();

    private HashMap<String, List<String>> raterToSpeakerRated = new HashMap<>();
    private HashMap<String, List<Integer>> speakerToRate = new HashMap<>();

//    private List<Rate> allRate = new ArrayList<>();


    private RateService() {}

    public void rateSpeaker(User speaker, Rater rater, int rate) throws notSpeakerException, rateRepetitionException,
            rateOutOfBoundException {

        if (!(speaker instanceof Speaker)) {
            throw new notSpeakerException();
        }
        if (raterToSpeakerRated.containsKey(rater.getUsername())) {
            if (raterToSpeakerRated.get(rater.getUsername()).contains(speaker.getUsername())) throw new rateRepetitionException();
        }

        if (rate < 1 || rate > 10) throw new rateOutOfBoundException();

//        Rate newRate = new Rate(rate, rater.getUsername(), speaker.getUsername());
//        allRate.add(newRate);

        if (!raterToSpeakerRated.containsKey(rater.getUsername())) {
            raterToSpeakerRated.put(rater.getUsername(), new ArrayList<>());
        }
        raterToSpeakerRated.get(rater.getUsername()).add(speaker.getUsername());

        if (!speakerToRate.containsKey(speaker.getUsername())) {
            raterToSpeakerRated.put(speaker.getUsername(), new ArrayList<>());
        }
        speakerToRate.get(speaker.getUsername()).add(rate);

    }

    public double getAverageRate(String speakerUN) {
        List<Integer> rate = speakerToRate.get(speakerUN);
        double sum = 0;
        for (int r: rate) {
            sum += r;
        }
        return sum/rate.size();
    }

//    public void setRaterToSpeakerRated(HashMap<String, List<String>> raterToSpeakerRated) {
//        this.raterToSpeakerRated = raterToSpeakerRated;
//    }
//
//    public void setSpeakerToRate(HashMap<String, List<Integer>> speakerToRate) {
//        this.speakerToRate = speakerToRate;
//    }
//
//    public List<Rate> getAllRate() {
//        return allRate;
//    }


    public static class raterException extends Exception {}
    public static class rateOutOfBoundException extends raterException {}
    public static class rateRepetitionException extends raterException {}
    public static class notSpeakerException extends raterException {}
}
