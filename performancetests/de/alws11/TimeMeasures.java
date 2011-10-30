package de.alws11;

import de.alws11.data.DynamicData;
import junit.framework.Assert;
import org.junit.Test;

import java.util.List;

public class TimeMeasures {
    private final String PATTERN_FILE = "E:\\measures\\a_pattern.txt";
    private final String TEXT_FILE = "E:\\measures\\a_test.txt";
    private final String INDICES_ROOT = "E:\\measures\\indices";

    @Test
    public void create_pattern100000_text1000000() throws Exception {
        DynamicDataHelper.create(PATTERN_FILE, DynamicData.startWith(99999, "a").then(1, "b"));
        DynamicDataHelper.create(TEXT_FILE, DynamicData.startWith(999999, "a").then(1, "b"));
    }

    @Test
    public void search_buffer1000() throws Exception {
        int bufferSize = 1000;

        IDataProvider textFile = SearchIntegrationHelper.getFile(TEXT_FILE, bufferSize);
        AsymmetricDataProvider patternFile = SearchIntegrationHelper.getFileAsym(PATTERN_FILE, bufferSize);
        IIndexStore prefixIndices = SearchIntegrationHelper.getIndexStoreFiles(INDICES_ROOT, bufferSize);

        ISearch kmpSearch = SearchHelper.getKnuthMorrisPrattSearcher(prefixIndices, false);
        List<Long> matches = kmpSearch.forPattern(patternFile).inSource(textFile);

        SearchIntegrationHelper.close(prefixIndices, patternFile, textFile);
        FileAccessHelper.deleteFolder(INDICES_ROOT);
        Assert.assertEquals(1, matches.size());
    }
}
