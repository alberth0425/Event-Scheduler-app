package ui.rate.ViewRate;

import entities.Rater;
import entities.User;
import ui.rate.RatePresenter;
import use_cases.AuthService;
import use_cases.RaterService;

public class ViewRatePresenter {

    private final ViewRateView view;

    public ViewRatePresenter(ViewRateView view) {
        this.view = view;
    }



    public interface ViewRateView {
    }
}
