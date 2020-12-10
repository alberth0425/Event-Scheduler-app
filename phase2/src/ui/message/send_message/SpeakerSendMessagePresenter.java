package ui.message.send_message;

import entities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import use_cases.AuthService;
import use_cases.EventService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SpeakerSendMessagePresenter extends SendMessagePresenter {
    public SpeakerSendMessagePresenter(SendMessagePresenter.SendMessageView view) {
        super(view);
    }

    @Override
    public ObservableList<String> getReceiverTypes() {
        return FXCollections.observableArrayList(
                "Send to attendees in all my events",
                "Send to attendees in one of my events",
                "Send to user"
        );
    }

    @Override
    public String getReceiverTextPrompt(int index) {
        switch (index) {
            case 1:
                return "Event ID";
            case 2:
                return "Username";
            default:
                return null;
        }
    }

    @Override
    void handleSendMessage(int receiverTypeIndex, String receiver, String content) {
        List<String> usernames;
        String currentUsername = AuthService.shared.getCurrentUser().getUsername();

        switch (receiverTypeIndex) {
            case 0:
                usernames = EventService.shared.getEventsBySpeaker(currentUsername)
                        .stream()
                        .flatMap(event -> event.getAttendeeUNs().stream())
                        .distinct()
                        .collect(Collectors.toList());
                break;
            case 1:
                try {
                    Event event = EventService.shared.getEventById(receiver);
                    if (event.getSpeakerUNs().contains(currentUsername)) {
                        usernames = event.getAttendeeUNs();
                    } else {
                        getView().displayError("You are not giving speech at this event.");
                        return;
                    }
                } catch (NumberFormatException | EventService.EventException e) {
                    getView().displayError("Invalid event ID.");
                    return;
                }
                break;
            case 2:
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
