package entities;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.UUID;

public class Message implements Savable {

    private final String text;
    private final String senderUsername;
    private final String receiverUsername;
    private final long timeStamp;  // message send time in millisecond
    private final String id;
    public boolean isArchived; // if this message is archived or not

    /**
     * constructor for the message class
     *
     * @param text the text in this message
     * @param senderUsername the username of the sender
     * @param receiverUsername the username of the receiver
     */
    public Message(String text, String senderUsername, String receiverUsername) {
        this.text = text;
        this.senderUsername = senderUsername;
        this.receiverUsername = receiverUsername;

        // Set time stamp to current time
        Date date = new Date();
        timeStamp = date.getTime();

        // Set id
        UUID uuid = UUID.randomUUID();
        id = uuid.toString();

        isArchived = false;

    }

    /**
     * construct message from a dataEntry.
     *
     * @param dataEntry the savable string that represents a message
     */
    public Message(String dataEntry) {
        String[] entries = dataEntry.split(DELIMITER);
        this.id = entries[0];
        this.text = entries[1];
        this.senderUsername = entries[2];
        this.receiverUsername = entries[3];
        this.timeStamp = Long.parseLong(entries[4]);

    }

    /**
     * getter for the text.
     *
     * @return the text of this message
     */
    public String getText() {
        return text;
    }

    /**
     * getter for the sender.
     *
     * @return the sender's username
     */
    public String getSenderUsername() {
        return senderUsername;
    }

    /**
     * getter for the receiver.
     *
     * @return the receiver's username
     */
    public String getReceiverUsername() {
        return receiverUsername;
    }

    /**
     * getter for the timeStamp.
     *
     * @return the event's timestamp
     */
    public long getTimeStamp() {
        return timeStamp;
    }

    /**
     * Get formatted timestamp, format: dd/MM/yyyy HH:mm:ss
     * @return formatted String representation of the message timestamp
     */
    public String getFormattedTimeStamp() {
        Date date = new Date(timeStamp);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        return dateFormat.format(date);
    }

    /**
     * getter for the id of this message.
     *
     * @return the id of this message
     */
    public String getId() {
        return id;
    }

    /**
     * set message to archived
     */
    public void setArchived(){
        isArchived = true;
    }

    /**
     * set message to unarchived
     */
    public void setUnarchived(){
        isArchived = false;
    }

    /**
     * turn the information of this message into a savable string.
     *
     * @return a savable string
     */
    @Override
    public String toSavableString() {
        return id + DELIMITER + text + DELIMITER + senderUsername + DELIMITER + receiverUsername + DELIMITER + timeStamp
                + DELIMITER + isArchived;
    }
}
