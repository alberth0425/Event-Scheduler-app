package src.gateway;

import src.entities.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PersistenceStorage {
    /**
     * Path constants for different Savable classes
     */
    private static final String ATTENDEE_STORAGE_PATH = "attendees.csv";
    private static final String SPEAKER_STORAGE_PATH = "speakers.csv";
    private static final String ORGANIZER_STORAGE_PATH = "organizers.csv";
    private static final String EVENT_STORAGE_PATH = "events.csv";
    private static final String ROOM_STORAGE_PATH = "rooms.csv";
    private static final String MESSAGE_STORAGE_PATH = "messages.csv";

    /**
     * Save input entries as a csv/txt file to the input path.
     *
     * @param entries to-be-saved entries, need to implement `Savable`
     * @param path path of the saved file
     */
    public static void saveEntries(List<Savable> entries, String path) {
        try {
            FileWriter fw = new FileWriter(path, false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            for (Savable e : entries) {
                pw.println(e.toSavableString());
            }
            pw.flush();
            pw.close();
            System.out.println("All entries saved to path: " + path);

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
    public static <T> List<T> readEntries(String path, Class<T> returnType) {
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
    
}
