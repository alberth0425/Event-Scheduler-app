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

public class PartyPresenter extends EventPresenter{
    public PartyPresenter(EventView view, EventFilter filter){
        super(view, filter);
    }


}
