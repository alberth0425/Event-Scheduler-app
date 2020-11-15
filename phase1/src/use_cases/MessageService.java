package src.use_cases;

import src.entities.Message;
import src.entities.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageService {
    private HashMap<String, List<String>> contactBook = new HashMap<>();
    private HashMap<String, List<Message>> messageRepository = new HashMap<>();

    public static MessageService shared = new MessageService();
    private MessageService() {}

    public void setContactBook(HashMap<String, List<String>> contactBook) {
        this.contactBook = contactBook;
    }

    public void setMessageRepository(HashMap<String, List<Message>> messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> getAllMessages() {
        ArrayList<Message> allMessageList = new ArrayList<>();
        for (List<Message> e : messageRepository.values()) {
            allMessageList.addAll(e);
        }
        return allMessageList;
    }

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
        Message newMessage = new Message(text, sender.getUsername(), receiver.getUsername());

        if (!canSendMessage(sender, receiver)) return false;

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
    }

    private boolean canSendMessage(User sender, User receiver) {
//        // Check if sender is in the contact book
//        if (!contactBook.containsKey(sender.getUsername())) return false;
//
//        // Check whether receiver is in the contact book of sender
//        // Not in the contact book
//        return contactBook.get(sender.getUsername()).contains(receiver.getUsername());

        // Check if sender is receiver
        return !sender.getFullname().equals(receiver.getUsername());
    }

    /**
     * Get the list of messages received by a user.
     *
     * @param receiverUN the username of the user
     * @return the list of messages
     */
    public List<Message> getReceivedMessages(String receiverUN) {
        if (!messageRepository.containsKey(receiverUN)) {
            return new ArrayList<>();
        } else {
            return messageRepository.get(receiverUN);
        }
    }

}
