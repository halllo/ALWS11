package de.alws11.data;

import de.alws11.IDataProvider;

public class StringData implements IDataProvider {
    private static char[] charArrayOf(String word) {
        char[] arrayOfStrings = new char[word.length()];
        for (int i = 0; i < word.length(); i++) {
            arrayOfStrings[i] = word.charAt(i);
        }
        return arrayOfStrings;
    }

    private char[] _text;

    public StringData(String text) {
        _text = charArrayOf(text);
    }

    public char getPosition(long index) {
        return _text[(int) index];
    }

    public long size() {
        return _text.length;
    }

    public void close() {

    }
}
