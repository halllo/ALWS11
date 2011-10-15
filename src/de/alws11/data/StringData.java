package de.alws11.data;

import de.alws11.IDataProvider;

public class StringData implements IDataProvider {
    public static String[] ofString(String word) {
        String[] arrayOfStrings = new String[word.length()];
        for (int i = 0; i < word.length(); i++) {
            arrayOfStrings[i] = "" + word.charAt(i);
        }
        return arrayOfStrings;
    }

    private String[] _text;

    public StringData(String text) {
        _text = ofString(text);
    }

    public String getPosition(long index) {
        return _text[(int)index];
    }

    public long size() {
        return _text.length;
    }
}
