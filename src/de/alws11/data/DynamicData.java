package de.alws11.data;

import de.alws11.IDataProvider;
import de.alws11.fileio.IFileWriteAccess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DynamicData implements IDataProvider {
    abstract class DataPart {
        public long startIndex;
        public String data;

        public abstract int dataLength();

        public abstract char dataPosition(int index);
    }

    class StaticDataPart extends DataPart {
        @Override
        public int dataLength() {
            return data.length();
        }

        @Override
        public char dataPosition(int index) {
            return data.charAt(index);
        }
    }

    class RandomDataPart extends DataPart {
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

    public static DynamicData startWith(long repetitions, String data) {
        DynamicData dd = new DynamicData();
        dd.addDataPart(repetitions, data);
        return dd;
    }

    public static DynamicData startWithRandom(long repetitions, String set) {
        DynamicData dd = new DynamicData();
        dd.addDataPartRandom(repetitions, set);
        return dd;
    }

    public DynamicData then(long repetitions, String data) {
        addDataPart(repetitions, data);
        return this;
    }

    public DynamicData thenRandom(long repetitions, String set) {
        addDataPartRandom(repetitions, set);
        return this;
    }

    public char getPosition(long index) {
        if (index >= _size) return '\u0000';
        int i = 0;
        while (i < _parts.size() && _parts.get(i).startIndex <= index) i++;
        DataPart responsiblePart = _parts.get(i - 1);
        if (responsiblePart.dataLength() == 0) return '\u0000';
        long responsiblePartIndex = (index - responsiblePart.startIndex) % responsiblePart.dataLength();
        return responsiblePart.dataPosition((int) responsiblePartIndex);
    }

    public long size() {
        return _size;
    }

    public void close() {

    }

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
