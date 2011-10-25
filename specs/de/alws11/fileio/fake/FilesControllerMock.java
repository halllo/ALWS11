package de.alws11.fileio.fake;

import de.alws11.fileio.IFileReadAccess;
import de.alws11.fileio.IFileWriteAccess;
import de.alws11.fileio.IFilesController;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class FilesControllerMock implements IFilesController {
    public List<String> calls_to_createFile;
    public List<String> calls_to_getReaderForFile;
    public List<String> calls_to_getWriterForFile;
    public Dictionary<String, IFileReadAccess> readers;
    public Dictionary<String, IFileWriteAccess> writers;

    public FilesControllerMock() {
        calls_to_createFile = new ArrayList<String>();
        calls_to_getReaderForFile = new ArrayList<String>();
        calls_to_getWriterForFile = new ArrayList<String>();
        readers = new Hashtable<String, IFileReadAccess>();
        writers = new Hashtable<String, IFileWriteAccess>();
    }

    public void createFile(String fileName) {
        calls_to_createFile.add(fileName);
    }

    public IFileReadAccess getReaderForFile(String fileName) {
        calls_to_getReaderForFile.add(fileName);
        IFileReadAccess reader = readers.get(fileName);
        return reader != null ? reader : new FileAccessStub();
    }

    public IFileWriteAccess getWriterForFile(String fileName) {
        calls_to_getWriterForFile.add(fileName);
        IFileWriteAccess writer = writers.get(fileName);
        return writer != null ? writer : new FileAccessStub();
    }
}
