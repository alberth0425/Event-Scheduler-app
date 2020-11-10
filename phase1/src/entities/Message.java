package src.entities;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Message {
    private static int messageCount;

    private final String text;
    private final int senderID;
    private final int receiverID;
    private final long timeStamp;  // message send time in millisecond
    private final int id;

    public Message(String text, int senderID, int receiverID) {
        this.text = text;
        this.senderID = senderID;
        this.receiverID = receiverID;

        // Set time stamp to current time
        Date date = new Date();
        timeStamp = date.getTime();
        System.out.println(timeStamp);

        // Set id
        id = messageCount;
        messageCount += 1;
    }

    public String getText() {
        return text;
    }

    public int getSenderID() {
        return senderID;
    }

    public int getReceiverID() {
        return receiverID;
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
}
