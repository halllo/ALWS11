package de.alws11.data;

import de.alws11.IDataProvider;

public class StringData implements IDataProvider {
    public static String[] OfString(String word) {
        String[] arrayOfStrings = new String[word.length()];
        for (int i = 0; i < word.length(); i++) {
            arrayOfStrings[i] = "" + word.charAt(i);
        }
        return arrayOfStrings;
    }

    private String[] _text;

    public StringData(String text) {
        _text = OfString(text);
    }

    public String GetPosition(long index) {
        return _text[(int)index];
    }

    public long Size() {
        return _text.length;
    }
}
