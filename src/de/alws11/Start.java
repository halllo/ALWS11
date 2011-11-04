package de.alws11;

import de.alws11.data.FileData;
import de.alws11.data.IndexFileData;
import de.alws11.data.IndexFilesData;
import de.alws11.fileio.FileBufferedReader;
import de.alws11.fileio.FileBufferedWriter;
import de.alws11.fileio.FilesController;

import java.io.File;
import java.util.List;

public class Start {
    public static void main(String[] args) throws Exception {
        System.out.println("hello");
        if (args.length != 4) {
            System.out.println("<buffer_size> <text_file> <pattern_file> <indices_root>");
        } else {
            String text = args[1];
            String pattern = args[2];
            String indices = args[3];
            int bufferSize = Integer.parseInt(args[0]);

            measureKmpSearchTime(bufferSize, text, pattern, indices);

            System.out.print("cleaning indices folder...");
            deleteFolder(indices);
            System.out.println("done!");
        }
    }

    public static void measureKmpSearchTime(int bufferSize, String textFilePath, String patternFilePath, String indicesRoot) throws Exception {
        IDataProvider textFile = getFile(textFilePath, bufferSize);
        AsymmetricDataProvider patternFile = getFileAsym(patternFilePath, bufferSize);
        IIndexStore prefixIndices = getIndexStoreFiles(indicesRoot, bufferSize);
        ISearch kmpSearch = getKnuthMorrisPrattSearcher(prefixIndices, false);

        System.out.print("calculating next function...");
        kmpSearch = kmpSearch.forPattern(patternFile);
        System.out.println("done!");

        System.out.print("searching...");
        List<Long> matches = kmpSearch.inSource(textFile);
        System.out.println("done!");

        close(prefixIndices, patternFile, textFile);
    }

    public static void measureNaiveSearchTime(int bufferSize, String textFilePath, String patternFilePath) throws Exception {
        IDataProvider textFile = getFile(textFilePath, bufferSize);
        AsymmetricDataProvider patternFile = getFileAsym(patternFilePath, bufferSize);

        ISearch naiveSearch = getNaiveSearcher(false);
        List<Long> matches = naiveSearch.forPattern(patternFile).inSource(textFile);

        close(patternFile, textFile);
    }

    public static void delete(String... files) throws Exception {
        for (String file : files) {
            delete(file);
        }
    }

    public static void deleteFolder(String folder) throws Exception {
        for (String file : new File(folder).list()) {
            delete(folder + "\\" + file);
        }
    }

    private static void delete(String file) throws Exception {
        File fileToDelete = new File(file);
        if (fileToDelete.exists()) {
            boolean deleted = fileToDelete.delete();
            if (!deleted) {
                throw new Exception("\"" + file + "\" could not be deleted");
            }
        }
    }

    public static void close(IIndexStore indices, IDataProvider... dataProviders) {
        indices.close();
        close(dataProviders);
    }

    public static void close(IDataProvider... dataProviders) {
        for (IDataProvider data : dataProviders) {
            data.close();
        }
    }

    public static IDataProvider getFile(String file, int bufferSize) throws Exception {
        return new FileData(new FileBufferedReader(file), bufferSize);
    }

    public static AsymmetricDataProvider getFileAsym(String file, int bufferSize) throws Exception {
        return new AsymmetricDataProvider(
                new FileData(new FileBufferedReader(file), bufferSize),
                new FileData(new FileBufferedReader(file), bufferSize));
    }

    public static IIndexStore getIndexStoreFile(String file) throws Exception {
        return new IndexFileData(new FileBufferedWriter(file), new FileBufferedReader(file));
    }

    public static IIndexStore getIndexStoreFiles(String path, int bufferSize) throws Exception {
        return new IndexFilesData(new FilesController(path), bufferSize);
    }

    public static ISearch getKnuthMorrisPrattSearcher(IIndexStore indices, boolean findAllOccurrences) {
        de.alws11.KnuthMorrisPratt.Searcher searcher = new de.alws11.KnuthMorrisPratt.Searcher(indices);
        searcher.findAllMatches = findAllOccurrences;
        return searcher;
    }

    public static ISearch getNaiveSearcher(boolean findAllOccurrences) {
        de.alws11.Naive.Searcher searcher = new de.alws11.Naive.Searcher();
        searcher.findAllMatches = findAllOccurrences;
        return searcher;
    }
}
