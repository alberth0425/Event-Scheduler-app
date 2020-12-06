package ui.event.create_event;

public class CreateEventPresenter {
    private final CreateEventView view;

    /**
     * Construct a create event presenter with given view.
     *
     * @param view the view using this presenter
     */
    public CreateEventPresenter(CreateEventView view) {
        this.view = view;
    }

    public interface CreateEventView {

    }
}
