package de.alws11.data;

import de.alws11.IDataProvider;
import de.alws11.fileio.IFileWriteAccess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This interface is a lazy data provider that determines which character to return at request time.
 */
public class DynamicData implements IDataProvider {
    private abstract class DataPart {
        public long startIndex;
        public String data;

        public abstract int dataLength();

        public abstract char dataPosition(int index);
    }

    private class StaticDataPart extends DataPart {
        @Override
        public int dataLength() {
            return data.length();
        }

        @Override
        public char dataPosition(int index) {
            return data.charAt(index);
        }
    }

    private class RandomDataPart extends DataPart {
        private Random _rand = new Random();

        @Override
        public int dataLength() {
            return 1;
        }

        @Override
        public char dataPosition(int index) {
            index = _rand.nextInt(data.length());
            return data.charAt(index);
        }
    }

    private long _size;
    private List<DataPart> _parts;

    private DynamicData() {
        _size = 0;
        _parts = new ArrayList<DataPart>();
    }

    /**
     * This method creates an instance of the data provider that is pre initialized with a lazy sequence. It does not enumerate the sequence.
     *
     * @param repetitions This parameter defines how many times the pattern is to be repeated.
     * @param data        This parameter defines that pattern that is repeated.
     * @return The method returns the instance of the data provider.
     */
    public static DynamicData startWith(long repetitions, String data) {
        DynamicData dd = new DynamicData();
        dd.addDataPart(repetitions, data);
        return dd;
    }

    /**
     * This method creates an instance of the data provider that is pre initialized with a lazy sequence of random characters. It does not enumerate the sequence.
     *
     * @param repetitions This parameter defines how many times a random character is repeated.
     * @param set         This parameter defines the set of characters that are used to get random characters from.
     * @return The method returns the instance of the data provider.
     */
    public static DynamicData startWithRandom(long repetitions, String set) {
        DynamicData dd = new DynamicData();
        dd.addDataPartRandom(repetitions, set);
        return dd;
    }

    /**
     * This method appends a new lazy sequence of characters to the data provider. It does not enumerate the sequence.
     *
     * @param repetitions This parameter defines how many times the pattern is to be repeated.
     * @param data        This parameter defines the pattern that is repeated.
     * @return The method returns the instance of the same data provider.
     */
    public DynamicData then(long repetitions, String data) {
        addDataPart(repetitions, data);
        return this;
    }

    /**
     * This method appends a new lazy sequence of random characters to the data provider. It does not enumerate the sequence.
     *
     * @param repetitions This parameter defines how many times a random character is to be repeated.
     * @param set         This parameter defines the set of characters that are used to get random characters from.
     * @return The method returns the instance of the same data provider.
     */
    public DynamicData thenRandom(long repetitions, String set) {
        addDataPartRandom(repetitions, set);
        return this;
    }

    /**
     * This method returns a character based on its index position by finding its registered pattern and calculating the offset position.
     *
     * @param index The zero based position from where the character is requested.
     * @return The character at the requested position is returned. If there is no character at the given index, '\u0000' is returned.
     */
    public char getPosition(long index) {
        if (index >= _size) return '\u0000';
        int i = 0;
        while (i < _parts.size() && _parts.get(i).startIndex <= index) i++;
        DataPart responsiblePart = _parts.get(i - 1);
        if (responsiblePart.dataLength() == 0) return '\u0000';
        long responsiblePartIndex = (index - responsiblePart.startIndex) % responsiblePart.dataLength();
        return responsiblePart.dataPosition((int) responsiblePartIndex);
    }

    /**
     * This method returns the size of the lazy sequence when it is enumerated.
     *
     * @return The method returns the amount of characters in the sequence.
     */
    public long size() {
        return _size;
    }

    /**
     * This method does not induce any action since there is nothing that needs to be closed.
     */
    public void close() {

    }

    /**
     * This method enumerates the lazy sequence and writes each character into a file.
     *
     * @param writer The abstract file that is used to write the sequence to.
     * @throws IOException A file access error might occur.
     */
    public void toFile(IFileWriteAccess writer) throws IOException {
        for (long i = 0; i < size(); i++) {
            writer.write(String.valueOf(getPosition(i)));
        }
    }

    private void addDataPart(long repetitions, String data) {
        DataPart newData = new StaticDataPart();
        newData.startIndex = _size;
        newData.data = data;
        _parts.add(newData);
        _size += repetitions * data.length();
    }

    private void addDataPartRandom(long repetitions, String set) {
        DataPart newData = new RandomDataPart();
        newData.startIndex = _size;
        newData.data = set;
        _parts.add(newData);
        _size += repetitions;
    }
}
