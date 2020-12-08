package ui.rate.ViewSpeakerRate;

import ui.rate.RatePresenter;

public class ViewSpeakerRatePresenter {

    private final ViewSpeakerRateView view;

    public ViewSpeakerRatePresenter(ViewSpeakerRateView view) {
        this.view = view;
    }

    ViewSpeakerRateView getView() {
        return view;
    }

    public interface ViewSpeakerRateView {
    }
}
