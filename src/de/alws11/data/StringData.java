package de.alws11.data;

import de.alws11.IIndexable;

/**
 * Created by IntelliJ IDEA.
 * User: Manuel
 * Date: 12.10.11
 * Time: 10:15
 * To change this template use File | Settings | File Templates.
 */
public class StringData implements IIndexable {
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

    public String AtIndex(long index) {
        return _text[(int)index];
    }

    public long Size() {
        return _text.length;
    }
}
