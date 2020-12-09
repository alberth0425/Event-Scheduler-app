package ui.user.create_event;

import entities.Rater;
import entities.Room;
import entities.Speaker;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ui.rate.RatePresenter;
import use_cases.AuthService;
import use_cases.EventService;
import use_cases.RaterService;
import use_cases.RoomService;

import java.util.List;

public class createEventPresenter {

    private final createEventView view;

    int eventTypeIndex = -1;

    public createEventPresenter(createEventView view) {
        this.view = view;
    }

    /**
     *  Get the list of strings representing the different event types
     * @return the list of event types
     */
    public ObservableList<String> getEventTypes() {
        return FXCollections.observableArrayList(
                "Create Talk",
                "Create Party",
                "Create Panel Discussion"
        );
    }

    public void onSelectEventType(int index){
        eventTypeIndex = index;
    }

    public void onConfirmButtonPressed(){handleConfirmButton(eventTypeIndex);}

    public void handleConfirmButton(int index){
        switch(index){
            case 0:
                onConfirmButtonPressedTalk();
                break;
            case 1:
                onConfirmButtonPressedParty();
                break;
            case 2:
                onConfirmButtonPressedPD();
                break;
        }
    }

    /**
     * Handle the confirm button action.
     */
    public void onConfirmButtonPressedTalk() {
        try {
            String title = view.getEventTitle();
            int startingTime = view.getStartingTime();
            int capacity = view.getCapacity();
            int duration = view.getDuration();
            Speaker speaker = (Speaker) AuthService.shared.getUserByUsername(view.getSpeakerUN());
            Room room = RoomService.shared.getRoom(view.getRoomNumber());

            EventService.shared.createTalk(title, startingTime, speaker, room, duration, capacity);


            view.navigateToSuccessCreateEvent();



        } catch (EventService.SpeakerDoubleBookException e) {
            view.setError("The speaker is not available at this time.");
        } catch (EventService.RoomFullException e) {
            view.setError("The event is full.");
        } catch (EventService.InvalidEventTimeException e) {
            view.setError("The event starting time (" + view.getStartingTime() + ") is invalid.");
        } catch(EventService.RoomDoubleBookException e) {
            view.setError("The room is not available at this time.");
        } catch (AuthService.AuthException e) {
            view.setError("Speaker with username " + view.getSpeakerUN() + " does not exist." );
        } catch (NumberFormatException e) {
            view.setError("Starting time and room number entered must be a number.");
        } catch (RoomService.RoomException e) {
            view.setError("Room with room number " + view.getRoomNumber() + " does not exist.");
        } catch (Exception e) {
            view.setError("Unknown Exception: " + e.toString());
        }
    }

    /**
     * Handle the confirm button action.
     */
    public void onConfirmButtonPressedParty() {
        try {
            String title = view.getEventTitle();
            int startingTime = view.getStartingTime();
            int capacity = view.getCapacity();
            int duration = view.getDuration();
            Room room = RoomService.shared.getRoom(view.getRoomNumber());

            EventService.shared.createParty(title, startingTime, room, duration, capacity);


            view.navigateToSuccessCreateEvent();

        } catch (EventService.SpeakerDoubleBookException e) {
            view.setError("The speaker is not available at this time.");
        } catch (EventService.RoomFullException e) {
            view.setError("The event is full.");
        } catch (EventService.InvalidEventTimeException e) {
            view.setError("The event starting time (" + view.getStartingTime() + ") is invalid.");
        } catch(EventService.RoomDoubleBookException e) {
            view.setError("The room is not available at this time.");
        } catch (NumberFormatException e) {
            view.setError("Starting time and room number entered must be a number.");
        } catch (RoomService.RoomException e) {
            view.setError("Room with room number " + view.getRoomNumber() + " does not exist.");
        } catch (Exception e) {
            view.setError("Unknown Exception: " + e.toString());
        }
    }

    public void onConfirmButtonPressedPD() {
        try {
            String title = view.getEventTitle();
            int startingTime = view.getStartingTime();
            int capacity = view.getCapacity();
            int duration = view.getDuration();
            List<Speaker> speakers = AuthService.shared.getListOfSpeakersByUNs(view.getSpeakerUNs());
            Room room = RoomService.shared.getRoom(view.getRoomNumber());

            EventService.shared.createPD(title, startingTime, speakers, room, duration, capacity);


            view.navigateToSuccessCreateEvent();


        } catch (EventService.SpeakerDoubleBookException e) {
            view.setError("The speaker is not available at this time.");
        } catch (EventService.RoomFullException e) {
            view.setError("The event is full.");
        } catch (EventService.InvalidEventTimeException e) {
            view.setError("The event starting time (" + view.getStartingTime() + ") is invalid.");
        } catch(EventService.RoomDoubleBookException e) {
            view.setError("The room is not available at this time.");
        } catch (AuthService.AuthException e) {
            view.setError("At least one of the speaker username does not exist, try again!" );
        } catch (NumberFormatException e) {
            view.setError("Starting time and room number entered must be a number.");
        } catch (RoomService.RoomException e) {
            view.setError("Room with room number " + view.getRoomNumber() + " does not exist.");
        } catch (Exception e) {
            view.setError("Unknown Exception: " + e.toString());
        }
    }

    public interface createEventView {
        String getEventTitle();
        int getStartingTime();
        int getCapacity();
        String getSpeakerUN();
        List<String> getSpeakerUNs();
        int getRoomNumber();
        int getDuration();
        void setError(String error);
        void navigateToSuccessCreateEvent();
    }
}
