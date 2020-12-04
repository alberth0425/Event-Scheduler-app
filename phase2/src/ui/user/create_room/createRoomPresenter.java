package ui.user.create_room;

import entities.Room;
import entities.Speaker;
import ui.user.create_event.createEventPresenter;
import use_cases.AuthService;
import use_cases.EventService;
import use_cases.RoomService;

public class createRoomPresenter {
    private final createRoomView view;

    public createRoomPresenter(createRoomView view) {
        this.view = view;
    }

    /**
     * Handle the confirm button action.
     */
    public void onConfirmButtonPressed() {
        try {
            int roomNumber = view.getRoomNumber();
            int roomCapacity = view.getRoomCapacity();

            //Check room's existence
            if (!RoomService.shared.createRoom(roomNumber, roomCapacity)) {
                view.setError("Room number already exist.");
            } else {
                view.navigateToSuccessCreateRoom();
            }

        } catch (NumberFormatException e) {
            view.setError("Room number and room capacity must be a number.");
        }  catch (Exception e) {
            view.setError("Unknown Exception: " + e.toString());
        }
    }

    public interface createRoomView {
        int getRoomNumber();
        int getRoomCapacity();
        void setError(String error);
        void navigateToSuccessCreateRoom();
    }
}
