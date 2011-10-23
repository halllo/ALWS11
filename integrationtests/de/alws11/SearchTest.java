package de.alws11;

import junit.framework.Assert;
import org.junit.Test;

import java.util.List;

public class SearchTest {
    private final String SOURCE_FILE = "E:\\source.txt";
    private final String PATTERN_FILE = "E:\\pattern.txt";
    private final String INDICES_FILE = "E:\\indices.txt";

    @Test
    public void searchWikipediaSample1OnFiles_singleMatchFound() throws Exception {
        FileAccessHelper.create(SOURCE_FILE, "abababcbababcababcab");
        FileAccessHelper.create(PATTERN_FILE, "ababcabab");

        int bufferSize = 5;
        IIndexStore indices = SearchIntegrationHelper.getIndexStoreFile(INDICES_FILE);
        IDataProvider pattern = SearchIntegrationHelper.getPatternFile(PATTERN_FILE, bufferSize);
        IDataProvider source = SearchIntegrationHelper.getSourceFile(SOURCE_FILE, bufferSize);

        ISearch search = SearchHelper.getKnuthMorrisPrattSearcher(indices, true);
        List<Long> findings = search.forPattern(pattern).inSource(source);
        Assert.assertTrue(findings.contains((long) 8));
        Assert.assertTrue(findings.size() == 1);

        SearchIntegrationHelper.close(indices, pattern, source);
        FileAccessHelper.delete(SOURCE_FILE, PATTERN_FILE, INDICES_FILE);
    }
}
