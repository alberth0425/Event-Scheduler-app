package ui.message.send_message;

import entities.Attendee;
import entities.Event;
import entities.Speaker;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import use_cases.AuthService;
import use_cases.EventService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class OrganizerSendMessagePresenter extends SendMessagePresenter {
    public OrganizerSendMessagePresenter(SendMessageView view) {
        super(view);
    }

    @Override
    public ObservableList<String> getReceiverTypes() {
        return FXCollections.observableArrayList(
                "Send to all users",
                "Send to all speakers",
                "Send to all attendees",
                "Send to all attendees in event",
                "Send to user"
        );
    }

    @Override
    public String getReceiverTextPrompt(int index) {
        switch (index) {
            case 3:
                return "Event ID";
            case 4:
                return "Username";
            default:
                return null;
        }
    }

    @Override
    void handleSendMessage(int receiverTypeIndex, String receiver, String content) {
        List<String> usernames;

        switch (receiverTypeIndex) {
            case 0:
                String currentUsername = AuthService.shared.getCurrentUser().getUsername();
                usernames = AuthService.shared.getAllUsers()
                        .stream()
                        .filter(user -> !user.getUsername().equals(currentUsername))
                        .map(User::getUsername)
                        .collect(Collectors.toList());
                break;
            case 1:
                usernames = AuthService.shared.getAllUsers()
                        .stream()
                        .filter(user -> user instanceof Speaker)
                        .map(User::getUsername)
                        .collect(Collectors.toList());
                break;
            case 2:
                usernames = AuthService.shared.getAllUsers()
                    .stream()
                    .filter(user -> user instanceof Attendee)
                    .map(User::getUsername)
                    .collect(Collectors.toList());
                break;
            case 3:
                try {
//                    Integer eventId = Integer.parseInt(receiver);
                    Event event = EventService.shared.getEventById(receiver);
                    usernames = event.getAttendeeUNs();

                } catch (NumberFormatException | EventService.EventException e) {
                    getView().displayError("Invalid event ID.");
                    return;
                }
                break;
            case 4:
                try {
                    AuthService.shared.getUserByUsername(receiver);
                } catch (AuthService.AuthException e) {
                    getView().displayError("Invalid username.");
                    return;
                }
                usernames = Collections.singletonList(receiver);
                break;
            default:
                return;
        }

        for (String username : usernames) {
            if (!sendMessage(username, content)) {
                getView().displayError("Unknown error: failed to send message to " + username);
            }
        }

        getView().navigateToMessages();
    }
}
