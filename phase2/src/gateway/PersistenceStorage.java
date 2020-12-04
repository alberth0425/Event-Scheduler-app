package gateway;

import entities.*;

import java.io.*;
import java.util.*;

public class PersistenceStorage {
    /**
     * Path constants for different Savable classes
     */

    public static final String ATTENDEE_STORAGE_PATH = "./phase2/src/gateway/attendees.txt";
    public static final String SPEAKER_STORAGE_PATH = "./phase2/src/gateway/speakers.txt";
    public static final String ORGANIZER_STORAGE_PATH = "./phase2/src/gateway/organizers.txt";
    public static final String EVENT_STORAGE_PATH = "./phase2/src/gateway/events.txt";
    public static final String ROOM_STORAGE_PATH = "./phase2/src/gateway/rooms.txt";
    public static final String MESSAGE_STORAGE_PATH = "./phase2/src/gateway/messages.txt";
    public static final String CONTACT_BOOK_PATH = "./phase2/src/gateway/contact_book.txt";
    public static final String RATER_STORAGE_PATH= "./phase2/src/gateway/raters.txt";
    public static final String AGREEMENT_STORAGE_PATH= "./phase2/src/gateway/agreements.txt";

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
                } else if (returnType.equals(Rater.class)) {
                    res.add((T) new Rater(nl));
                } else if (returnType.equals(Agreement.class)) {
                    res.add((T) new Agreement(nl));
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
    
}
