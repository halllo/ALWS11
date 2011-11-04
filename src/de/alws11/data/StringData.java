package de.alws11.data;

import de.alws11.IDataProvider;

/**
 * This class provides a string as an indexable data structure.
 */
public class StringData implements IDataProvider {
    private static char[] charArrayOf(String word) {
        char[] arrayOfStrings = new char[word.length()];
        for (int i = 0; i < word.length(); i++) {
            arrayOfStrings[i] = word.charAt(i);
        }
        return arrayOfStrings;
    }

    private char[] _text;

    /**
     * This constructor initializes the given string as an indexable character array. The string therefore needs to be of limited length.
     *
     * @param text This parameter is the string which is used to access its characters.
     */
    public StringData(String text) {
        _text = charArrayOf(text);
    }

    /**
     * This method provides the character of the string at the given position.
     *
     * @param index The zero based position from where the character is requested. This index is used as an integer.
     * @return The method returns the character at the given position.
     */
    public char getPosition(long index) {
        return _text[(int) index];
    }

    /**
     * This method returns the size of the character array.
     *
     * @return The method returns the amount of characters in the array.
     */
    public long size() {
        return _text.length;
    }

    /**
     * This method does not do anything since there is nothing that needs to be closed.
     */
    public void close() {
        //ignored
    }
}
