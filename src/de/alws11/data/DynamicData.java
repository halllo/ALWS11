package de.alws11.data;

import de.alws11.IDataProvider;
import de.alws11.fileio.IFileWriteAccess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DynamicData implements IDataProvider {
    class DataPart {
        public long startIndex;
        public String data;
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

    public DynamicData then(long repetitions, String data) {
        addDataPart(repetitions, data);
        return this;
    }

    public char getPosition(long index) {
        int i = 0;
        while (i < _parts.size() && _parts.get(i).startIndex <= index) i++;
        DataPart responsiblePart = _parts.get(i - 1);
        if (responsiblePart.data.length() == 0) return '\u0000';
        long responsiblePartIndex = (index - responsiblePart.startIndex) % responsiblePart.data.length();
        return responsiblePart.data.charAt((int) responsiblePartIndex);
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
        DataPart newData = new DataPart();
        newData.startIndex = _size;
        newData.data = data;
        _parts.add(newData);
        _size += repetitions * data.length();
    }
}
