package src.entities;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Message implements Savable {
    private static int messageCount;

    private final String text;
    private final String senderUsername;
    private final String receiverUsername;
    private final long timeStamp;  // message send time in millisecond
    private final int id;

    public Message(String text, String senderUsername, String receiverUsername) {
        this.text = text;
        this.senderUsername = senderUsername;
        this.receiverUsername = receiverUsername;

        // Set time stamp to current time
        Date date = new Date();
        timeStamp = date.getTime();

        // Set id
        id = messageCount;
        messageCount += 1;
    }

    public Message(String dataEntry) {
        String[] entries = dataEntry.split(",");
        this.id = Integer.parseInt(entries[0]);
        this.text = entries[1];
        this.senderUsername = entries[2];
        this.receiverUsername = entries[3];
        this.timeStamp = Long.parseLong(entries[4]);

        messageCount += 1;
    }

    public String getText() {
        return text;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

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

    public int getId() {
        return id;
    }

    @Override
    public String toSavableString() {
        return id + "," + text + "," + senderUsername + "," + receiverUsername + "," + timeStamp;
    }
}
