package src.use_cases;

import src.entities.Message;
import src.entities.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageService {
    public static HashMap<Integer, List<Integer>> contactBook;
    public static HashMap<Integer, List<Message>> messageRepository;
    private User thisUser;

    public static void main(String[] args) {

    }

    public MessageService(User thisUser) {
        this.thisUser = thisUser;
        contactBook = new HashMap<Integer, List<Integer>>();
        messageRepository = new HashMap<Integer, List<Message>>();
    }

    public void addUserToConTact(User user) {
        if (contactBook.containsKey(thisUser.getId()))
            contactBook.get(thisUser.getId()).add(user.getId());
        else {
            List contact = new ArrayList<Integer>(user.getId());
            contact.add(user.getId());
            contactBook.put(thisUser.getId(), contact);
        }
    }

    private Message createMessage(String text, int receiverId) {
        Message newMessage = new Message(text, thisUser.getId(), receiverId);
        return newMessage;
    }

    private boolean messageRepositoryContains(int userId){
        return messageRepository.containsKey(userId);
    }

    public boolean messageTo(String text, User receiver) {
        if (contactBook.get(thisUser.getId()).contains(receiver.getId())) {
            Message newMessage = createMessage(text, receiver.getId());
            if (messageRepositoryContains(receiver.getId()))
                messageRepository.get(receiver.getId()).add(newMessage);
            else{
                List messageList = new ArrayList();
                messageList.add(newMessage);
                messageRepository.put(receiver.getId(), messageList);
            }
            return true;
        }
        return false;
    }

}
