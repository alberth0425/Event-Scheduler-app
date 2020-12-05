package ui.user.create_event;

import entities.Rater;
import entities.Room;
import entities.Speaker;
import entities.User;
import ui.rate.RatePresenter;
import use_cases.AuthService;
import use_cases.EventService;
import use_cases.RaterService;
import use_cases.RoomService;

public class createEventPresenter {

    private final createEventView view;

    public createEventPresenter(createEventView view) {
        this.view = view;
    }

    /**
     * Handle the confirm button action.
     */
    public void onConfirmButtonPressed() {
        try {
            String title = view.getEventTitle();
            int startingTime = view.getStartingTime();
            Speaker speaker = (Speaker) AuthService.shared.getUserByUsername(view.getSpeakerUN()); //TODO 加exception如果输进去的不是speaker
            Room room = RoomService.shared.getRoom(view.getRoomNumber());

            EventService.shared.createEvent(title, startingTime, speaker, room);


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

    public interface createEventView {
        String getEventTitle();
        int getStartingTime();
        String getSpeakerUN();
        int getRoomNumber();
        void setError(String error);
        void navigateToSuccessCreateEvent();
    }
}
