package ui.rate;

import entities.Rater;
import entities.User;
import use_cases.AuthService;
import use_cases.RaterService;

public class RatePresenter {

    private final RateView view;

    public RatePresenter(RateView view) {
        this.view = view;
    }

    /**
     * Handle the ok button action.
     */
    public void onOkButtonPressed() {
        try {
            User speaker = AuthService.shared.getUserByUsername(view.getSpeakerUsername());
            Rater rater = (Rater) AuthService.shared.getCurrentUser();
            RaterService.shared.rateSpeaker(speaker, rater, view.getRate());
            view.navigateToSuccessRateView();



        } catch (AuthService.UserDoesNotExistException e) {
            view.setError("User with username" + view.getSpeakerUsername() + " does not exist.");
        } catch (NumberFormatException | RaterService.rateOutOfBoundException e) {
            view.setError("Rate entered must be an integer between 1 to 10.");
        } catch (RaterService.notSpeakerException e) {
            view.setError("Username given is not a Speaker username.");
        } catch (RaterService.rateRepetitionException e) {
            view.setError("You have already rated the speaker with username " + view.getSpeakerUsername() +
                    ", please choose a speaker you haven't rated before");
        } catch (Exception e) {
            view.setError("Unknown Exception: " + e.toString());
        }
    }

    public interface RateView {
        String getSpeakerUsername();
        int getRate();
        void setError(String error);
        void navigateToSuccessRateView();
    }
}
