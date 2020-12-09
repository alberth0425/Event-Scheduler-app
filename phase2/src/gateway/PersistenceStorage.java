package gateway;

import entities.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class PersistenceStorage {
    /**
     * Path constants for different Savable classes
     */
    private static final String USER_DB_URL = "https://icyn81k5kk.execute-api.ca-central-1.amazonaws.com/prod/users";
    private static final String ROOM_DB_URL = "https://icyn81k5kk.execute-api.ca-central-1.amazonaws.com/prod/rooms";
    private static final String EVENT_DB_URL = "https://icyn81k5kk.execute-api.ca-central-1.amazonaws.com/prod/events";
    private static final String MESSAGE_DB_URL = "https://icyn81k5kk.execute-api.ca-central-1.amazonaws.com/prod/messages";

    /**
     * Print out the request in to the console from the input URL
     * @param returnType the type of wanted entities
     * @throws IOException throws Input/Output exception
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> getRequest(Class<T> returnType) throws IOException {
        StringBuilder returnedString = new StringBuilder();
        URL urlForInformation = getURLByClass(returnType);
        HttpURLConnection con = (HttpURLConnection) urlForInformation.openConnection();

        // Request setup
        con.setRequestMethod("GET");
        con.setConnectTimeout(6000); // 6 secs
        con.setReadTimeout(6000); // 6 secs

        BufferedReader output = new BufferedReader(new InputStreamReader(con.getResponseCode() > 299 ? con.getErrorStream() : con.getInputStream()));
        String line;
        while ((line = output.readLine())!=null) returnedString.append(line);
        output.close();
        con.disconnect();

        if (returnType.equals(User.class)) {
            return (List<T>) parseToUserList(returnedString.toString());
        } else if (returnType.equals(Room.class)) {
            return (List<T>) parseToRoomList(returnedString.toString());
        } else if (returnType.equals(Event.class)) {
            return (List<T>) parseToEventList(returnedString.toString());
        } else if (returnType.equals(Message.class)) {
            return (List<T>) parseToMessageList(returnedString.toString());
        } else return new ArrayList<>();
    }

    /**
     *
     * @param list the list of all inputs
     * @param inputType the type of the input. Could be user, room, message or event.
     * @param <T> The generic type.
     * @throws IOException catch input output exceptions.
     */
    public static <T> void putRequest(List<T> list, Class<T> inputType) throws IOException {

        URL url = getURLByClass(inputType);

        assert url != null;
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        //Request setup
        con.setRequestMethod("PUT");
        con.setDoOutput(true);
        con.addRequestProperty("Content-Type", "text/plain");
        con.setConnectTimeout(6000); // 6 secs
        con.setReadTimeout(6000); // 6 secs

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
        ArrayList<String> returnedList = new ArrayList<>();
        if (inputType.equals(User.class)) {
            for (T user: list) {
                returnedList.add("{" + ((User)user).toSavableString() + "}");
            }
        } else if (inputType.equals(Event.class)) {
            for (T event: list) {
                returnedList.add("{" + ((Event)event).toSavableString() + "}");
            }
        } else if (inputType.equals(Room.class)) {
            for (T room: list) {
                returnedList.add("{" + ((Room)room).toSavableString() + "}");
            }
        } else {
            for (T message: list) {
                returnedList.add("{" + ((Message)message).toSavableString() + "}");
            }
        }

        writer.write(returnedList.toString());
        writer.flush();
        writer.close();
        con.getResponseCode();
        con.disconnect();
    }

    // --- Private Helpers ---

    private static List<User> parseToUserList(String response) {
        ArrayList<User> userList = new ArrayList<>();
        JSONArray users = new JSONArray(response);
        for (int i = 0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);

            switch (user.getString("user_type")) {
                case "attendee":
                    userList.add(new Attendee(user.getString("username"), user.getString("password")
                            , user.getString("first_name"), user.getString("last_name")));
                    break;
                case "speaker":
                    // parse all rate from JSONArray to List<String>
                    JSONArray allRateRaw = (JSONArray) user.get("rate");
                    List<String> allRate = new ArrayList<>();
                    for (int j = 0; j < allRateRaw.length(); j++) allRate.add(allRateRaw.getString(j));

                    Speaker speaker = new Speaker(user.getString("username"), user.getString("password")
                            , user.getString("first_name"), user.getString("last_name"));
                    speaker.setAllRate(allRate);
                    userList.add(speaker);
                    break;
                case "organizer":
                    userList.add(new Organizer(user.getString("username"), user.getString("password")
                            , user.getString("first_name"), user.getString("last_name")));
                    break;
                case "rater":
                    // parse all speaker id rated from JSONArray to List<String>
                    JSONArray speakerRatedRaw = (JSONArray) user.get("speaker_id_rated");
                    List<String> speakerRated = new ArrayList<>();
                    for (int j = 0; j < speakerRatedRaw.length(); j++) speakerRated.add(speakerRatedRaw.getString(j));

                    Rater rater = new Rater(user.getString("username"), user.getString("password")
                            , user.getString("first_name"), user.getString("last_name"));
                    rater.setSpeakerIdRated(speakerRated);
                    userList.add(rater);
                    break;
            }
        }
        return userList;
    }

    private static List<Room> parseToRoomList(String response) {
        ArrayList<Room> roomList = new ArrayList<>();
        JSONArray rooms = new JSONArray(response);
        for (int i = 0; i < rooms.length(); i++) {
            JSONObject room = rooms.getJSONObject(i);
            roomList.add(new Room(room.getInt("capacity"), room.getInt( "room_number")));
        }
        return roomList;
    }

    private static List<Event> parseToEventList(String response) {
        ArrayList<Event> eventList = new ArrayList<>();
        JSONArray events = new JSONArray(response);
        for (int i = 0; i < events.length(); i++) {
            JSONObject event = events.getJSONObject(i);
// TODO; handle multipple types of events like user
            // parse attendee usernames from JSONArray to List<String>
            JSONArray attendeeUNsRaw = (JSONArray) event.get("attendee_uns");
            List<String> attendeeUNs = new ArrayList<>();
            for (int j = 0; j < attendeeUNsRaw.length(); j++) attendeeUNs.add(attendeeUNsRaw.getString(j));

            Event evObj = new Event(event.getString("title"), event.getString( "speaker_un"),
                    event.getInt("starting_time"), event.getInt("room_number"), event.getInt("capacity"));
            evObj.setAttendeeUNs(attendeeUNs);
            evObj.setUUID(event.getString("id"));
            eventList.add(evObj);
        }
        return eventList;
    }

    private static List<Message> parseToMessageList(String response) {
        ArrayList<Message> messagesList = new ArrayList<>();
        JSONArray messages = new JSONArray(response);
        for (int i = 0; i < messages.length(); i++) {
            JSONObject message = messages.getJSONObject(i);
            Message msg = new Message(message.getString("text"), message.getString( "sender_un"),
                    message.getString("receiver_un"));
            msg.setTimeStamp(message.getLong("timestamp"));
            msg.setUUID(message.getString("message_id"));
            messagesList.add(msg);
        }
        return messagesList;
    }

    private static <T> URL getURLByClass(Class<T> tClass) throws MalformedURLException {
        URL resUrl = null;
        if (tClass.equals(User.class)) {
            resUrl = new URL(USER_DB_URL);
        } else if (tClass.equals(Event.class)) {
            resUrl = new URL(EVENT_DB_URL);
        } else if (tClass.equals(Room.class)) {
            resUrl = new URL(ROOM_DB_URL);
        } else if (tClass.equals(Message.class)) {
            resUrl = new URL(MESSAGE_DB_URL);
        }
        return resUrl;
    }
}
