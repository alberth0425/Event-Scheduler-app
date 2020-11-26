package ui.user;

import entities.Attendee;
import entities.Organizer;
import entities.Speaker;
import entities.User;
import javafx.collections.ObservableList;
import ui.event.EventPresenter;
import use_cases.AuthService;

abstract public class UserActionPresenter {
    private final UserActionView view;

    public UserActionPresenter(UserActionView view) {
        this.view = view;
    }

    public static UserActionPresenter create(UserActionView view) {
        User user = AuthService.shared.getCurrentUser();

        if (user instanceof Attendee) {
            return new AttendeeActionPresenter(view);
        } else if (user instanceof Organizer) {
            return new OrganizerActionPresenter(view);
        } else if (user instanceof Speaker) {
            return new SpeakerActionPresenter(view);
        } else {
            return null;
        }
    }

    public abstract ObservableList<String> getActionList();
    public abstract void onAction(int index);

    public UserActionView getView() {
        return view;
    }

    void browseEvents(EventPresenter.EventFilter filter) {
        getView().navigateToEvents(filter);
    }

    public interface UserActionView {
        void setUserInfoLabelText(String text);
        void setActionLabelText(String text);
        void navigateToEvents(EventPresenter.EventFilter filter);
    }
}
