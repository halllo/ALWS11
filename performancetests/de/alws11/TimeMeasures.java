package de.alws11;

import de.alws11.data.DynamicData;
import de.alws11.data.FileData;
import de.alws11.data.IndexFilesData;
import de.alws11.fileio.FileBufferedReader;
import de.alws11.fileio.FilesController;
import junit.framework.Assert;
import org.junit.Test;

import java.util.List;

public class TimeMeasures {
    private final String PATTERN_FILE = "E:\\measures\\a_pattern.txt";
    private final String TEXT_FILE = "E:\\measures\\a_test.txt";
    private final String INDICES_ROOT = "E:\\measures\\indices";

    @Test
    public void create_pattern100000_text1000000() throws Exception {
        TimeMeasuresHelper.create(PATTERN_FILE, DynamicData.startWith(99999, "a").then(1, "b"));
        TimeMeasuresHelper.create(TEXT_FILE, DynamicData.startWith(999999, "a").then(1, "b"));
    }

    @Test
    public void search_buffer1000() throws Exception {
        int bufferSize = 1000;
        IDataProvider patternFile1 = new FileData(new FileBufferedReader(PATTERN_FILE), bufferSize);
        IDataProvider patternFile2 = new FileData(new FileBufferedReader(PATTERN_FILE), bufferSize);
        IDataProvider textFile = new FileData(new FileBufferedReader(TEXT_FILE), bufferSize);
        IIndexStore prefixIndices = new IndexFilesData(new FilesController(INDICES_ROOT), bufferSize);
        ISearch kmpSearch = SearchHelper.getKnuthMorrisPrattSearcher(prefixIndices, false);
        List<Long> matches = kmpSearch.forPattern(patternFile1, patternFile2).inSource(textFile);
        SearchIntegrationHelper.close(prefixIndices, patternFile1, patternFile2, textFile);
        FileAccessHelper.deleteFolder(INDICES_ROOT);
        Assert.assertEquals(1, matches.size());
    }
}
