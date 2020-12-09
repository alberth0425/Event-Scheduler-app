package ui.event;

import entities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ui.util.TextFieldPrompt;
import use_cases.AuthService;
import use_cases.EventService;
import use_cases.RoomService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TalkPresenter extends EventPresenter{


    /**
     * Construct the event presenter with given view and event filter.
     *
     * @param view the view using this presenter
     * @param filter the type of filter to apply to the event list
     */
    public TalkPresenter(EventView view, EventFilter filter) {
        super(view, filter);

    }






}
