package ui.message;

import entities.Message;

import java.text.DateFormat;
import java.util.Date;

public class MessageAdapter {
    private final Message message;
    private final DateFormat dateFormat;

    public MessageAdapter(Message message, DateFormat dateFormat) {
        this.message = message;
        this.dateFormat = dateFormat;
    }

    public Message getMessage() {
        return message;
    }

    public Integer getId() {
        return message.getId();
    }

    public String getContent() {
        return message.getText();
    }

    public String getSenderUsername() {
        return message.getSenderUsername();
    }

    public String getReceiverUsername() {
        return message.getReceiverUsername();
    }

    public String getFormattedTime() {
        return dateFormat.format(new Date(message.getTimeStamp()));
    }
}
