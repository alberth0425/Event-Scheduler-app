package src.use_cases;

import src.entities.Message;
import src.entities.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageService {
    public final HashMap<String, List<String>> contactBook = new HashMap<>();
    private final HashMap<String, List<Message>> messageRepository = new HashMap<>();

    public static MessageService shared = new MessageService();
    private MessageService() {}

    /**
     * Add user2 to user1's contact book.
     *
     * @param user1 the user whose contact book to add
     * @param user2 the user to add to user1's contact book
     */
    public void addUserToContact(User user1, User user2) {
        if (contactBook.containsKey(user1.getUsername()))
            contactBook.get(user1.getUsername()).add(user2.getUsername());
        else {
            List<String> contact = new ArrayList<>();
            contact.add(user2.getUsername());
            contactBook.put(user1.getUsername(), contact);
        }
    }

    /**
     * Send a message from sender to receiver.
     *
     * @param text the text content of the message
     * @param sender the username of the sender
     * @param receiver the username of the receiver
     * @return true if the message is sent, otherwise false
     */
    public boolean sendMessage(String text, User sender, User receiver) {
        // Check if sender is in the contact book
        if (!contactBook.containsKey(sender.getUsername())) return false;

        // Check whether receiver is in the contact book of sender
        if (contactBook.get(sender.getUsername()).contains(receiver.getUsername())) {
            Message newMessage = new Message(text, sender.getUsername(), receiver.getUsername());

            // Check if there are existing messages from sender to receiver
            if (messageRepository.containsKey(receiver.getUsername()))
                // Add to the existing list
                messageRepository.get(receiver.getUsername()).add(newMessage);
            else {
                // Create new list
                List<Message> messageList = new ArrayList<>();
                messageList.add(newMessage);
                messageRepository.put(receiver.getUsername(), messageList);
            }
            return true;

        } else {
            // Not in the contact book
            return false;
        }
    }
}
