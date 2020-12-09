package entities;

import java.text.MessageFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.UUID;

public class Message implements Savable {
//    private static int messageCount;

    private final String text;
    private final String senderUsername;
    private final String receiverUsername;
    private String uuid;           // TODO: change this to a string ID
    private long timeStamp;         // message send time in millisecond
    public boolean isArchived;

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
        uuid = UUID.randomUUID().toString();
//        id = Integer.toString(messageCount);
//        messageCount += 1;
        isArchived = false;
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
     * setter for the timeStamp.
     */
    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
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

    @Override
    public String toString() {
        return "Message{" +
                "text='" + text + '\'' +
                ", senderUsername='" + senderUsername + '\'' +
                ", receiverUsername='" + receiverUsername + '\'' +
                ", timeStamp=" + timeStamp +
                ", id=" + uuid +
                '}';
    }

    /**
     * getter for the uuid of this message.
     *
     * @return the uuid of this message
     */
    public String getUUID() {
        return uuid;
    }

    /**
     * setter for the uuid of this message.
     *
     */
    public void setUUID(String uuid) {
        this.uuid = uuid;
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
        return MessageFormat.format("\"message_id\": \"{0}\",\"text\": \"{1}\",\"sender_un\": \"{2}\", " +
                "\"receiver_un\": \"{3}\",\"timestamp\": {4},\"isArchived\": {5}", uuid, text, senderUsername, receiverUsername, Long.toString(timeStamp), isArchived);
    }
}
