package gateway;

import entities.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class PersistenceStorage {
    /**
     * Path constants for different Savable classes
     */
    private static final String USER_DB_URL = "https://icyn81k5kk.execute-api.ca-central-1.amazonaws.com/prod/users";

    public static final String ATTENDEE_STORAGE_PATH = "./phase1/storage/attendees.txt";
    public static final String SPEAKER_STORAGE_PATH = "./phase1/storage/speakers.txt";
    public static final String ORGANIZER_STORAGE_PATH = "./phase1/storage/organizers.txt";
    public static final String EVENT_STORAGE_PATH = "./phase1/storage/events.txt";
    public static final String ROOM_STORAGE_PATH = "./phase1/storage/rooms.txt";
    public static final String MESSAGE_STORAGE_PATH = "./phase1/storage/messages.txt";
    public static final String CONTACT_BOOK_PATH = "./phase1/storage/contact_book.txt";

    public static void main(String[] args) throws IOException {
        User lynn = new Attendee("lynn", "1234","Lynn", "Qian");
        ArrayList user_list = new ArrayList();
        user_list.add(lynn);
        putRequest(user_list);
        getRequest();
    }

    /**
     * Save input entries as a csv/txt file to the input path.
     *
     * @param entries to-be-saved entries, need to implement `Savable`
     * @param path path of the saved file
     */
    public static void saveEntities(List<Savable> entries, String path) {
        try {
            PrintWriter pw = getPrinterWriter(path);

            for (Savable e : entries) {
                pw.println(e.toSavableString());
            }
            pw.flush();
            pw.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Fetch all entries from a csv/txt file and return the entries as an `ArrayList`.
     *
     * @param path path of the file, should use `returnType`'s corresponding static path constant
     * @param returnType type of the entries in the file
     * @param <T> type of the entries in the file
     * @return an `ArrayList` of fetched `T`s
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> readEntities(String path, Class<T> returnType) {
        List<T> res = new ArrayList<>();
        try {
            File eventFile = new File(path);
            Scanner eventScanner = new Scanner(eventFile);

            while (eventScanner.hasNext()) {
                String nl = eventScanner.nextLine().trim();
                if (returnType.equals(Event.class)) {
                    res.add((T) new Event(nl));
                } else if (returnType.equals(Attendee.class)) {
                    res.add((T) new Attendee(nl));
                } else if (returnType.equals(Speaker.class)) {
                    res.add((T) new Speaker(nl));
                } else if (returnType.equals(Organizer.class)) {
                    res.add((T) new Organizer(nl));
                } else if (returnType.equals(Room.class)) {
                    res.add((T) new Room(nl));
                } else if (returnType.equals(Message.class)) {
                    res.add((T) new Message(nl));
                }
            }
            return res;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return res;
        }
    }

    /**
     * Save input contact book as a csv/txt file to the input path.
     *
     * @param contactBook to-be-saved contact book
     * @param path path of the saved file
     */
    public static void saveContactBook(HashMap<String, List<String>> contactBook, String path) {
        try {
            PrintWriter pw = getPrinterWriter(path);

            for (Map.Entry<String, List<String>> me : contactBook.entrySet()) {
                pw.println(me.getKey() + Savable.DELIMITER + String.join(Savable.DELIMITER, me.getValue()));
            }
            pw.flush();
            pw.close();
            System.out.println("All entries saved to path: " + path);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Fetch all contacts from a csv/txt file and return the contact book as an `HashMap<String, List<String>>`.
     *
     * @param path path of the file
     * @return a `HashMap<String, List<String>>` representing the fetched contact book
     */
    public static HashMap<String, List<String>> readContactBook(String path) {
        HashMap<String, List<String>> res = new HashMap<>();

        try {
            File eventFile = new File(path);
            Scanner eventScanner = new Scanner(eventFile);

            while (eventScanner.hasNext()) {
                String[] usernames = eventScanner.nextLine().trim().split(Savable.DELIMITER);
                List<String> contacts = Arrays.asList(Arrays.copyOfRange(usernames, 1, usernames.length));
                String user = usernames[0];
                res.put(user, contacts);
            }
            return res;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return res;
        }
    }


    // --- Private Helpers ---
    private static PrintWriter getPrinterWriter(String path) throws IOException {
        FileWriter fw = new FileWriter(path, false);
        BufferedWriter bw = new BufferedWriter(fw);

        return new PrintWriter(bw);
    }

    public static void getRequest() throws IOException {
        StringBuilder returnedString = new StringBuilder();
        URL urlForInformation = new URL(USER_DB_URL);
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
        parseToString(returnedString.toString());
    }

    public static void putRequest(List<User> users) throws IOException {
        URL urlForInformation = new URL(USER_DB_URL);
        HttpURLConnection con = (HttpURLConnection) urlForInformation.openConnection();

        //Request setup
        con.setDoOutput(true);
        con.setRequestMethod("PUT");
        con.addRequestProperty("Content-Type", "application/json");
        con.setConnectTimeout(6000); // 6 secs
        con.setReadTimeout(6000); // 6 secs

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
        ArrayList<String> returnedList = new ArrayList<>();
        for (User user : users) {
            System.out.println(user.toSavableString());
            returnedList.add("{" + user.toSavableString() + "}");
        }
        System.out.println(returnedList.toString());
        writer.write(returnedList.toString());
        writer.flush();
        writer.close();
        con.disconnect();
        System.out.println(con.getResponseCode());
    }

    public static void parseToString(String response) {
        JSONArray users = new JSONArray(response);
        for (int i = 0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);
            System.out.println("User full name: " + user.getString("first_name") + " " + user.getString("last_name"));
        }
    }
    
}
