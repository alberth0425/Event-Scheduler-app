package ui.message;

import entities.Message;

import java.text.DateFormat;
import java.util.Date;

public class MessageAdapter {
    private final Message message;
    private final DateFormat dateFormat;

    /**
     * Construct a MessageAdapter with a message and date format.
     *
     * @param message the message
     * @param dateFormat the date format
     */
    public MessageAdapter(Message message, DateFormat dateFormat) {
        this.message = message;
        this.dateFormat = dateFormat;
    }

    /**
     * Get the Message object of the adapter.
     *
     * @return a Message
     */
    public Message getMessage() {
        return message;
    }

    /**
     * Get the ID of the message.
     *
     * @return the ID string of the message
     */
    public String getId() {
        return message.getId();
    }

    /**
     * Get the text content of the message.
     *
     * @return the text content of the message
     */
    public String getContent() {
        return message.getText();
    }

    /**
     * Get the sender's username of the message.
     *
     * @return the sender username
     */
    public String getSenderUsername() {
        return message.getSenderUsername();
    }

    /**
     * Get the receiver's username of the message.
     *
     * @return the receiver username
     */
    public String getReceiverUsername() {
        return message.getReceiverUsername();
    }

    /**
     * Get the formatted time string of the message.
     *
     * @return the time string, formatted by dateFormat
     */
    public String getFormattedTime() {
        return dateFormat.format(new Date(message.getTimeStamp()));
    }
}
