package entities;

public interface Savable {
    String toSavableString();
    String DELIMITER = "φα";

    /**
     * Check that a string is savable in CSV format, i.e. it does not contain commas or newlines.
     *
     * @param string the string to be validated
     * @return true if the string does not contain commas or newlines
     */
    static boolean isStringSavable(String string) {
        return !(string.contains(DELIMITER) || string.contains("\n"));
    }
}
