package de.alws11;

import de.alws11.data.DynamicData;
import de.alws11.data.IndexData;
import junit.framework.Assert;
import org.junit.Test;

import java.util.List;

public class SearchPerfTest {
    private final String INDICES_FILE = "E:\\indices.txt";
    private final String INDICES_ROOT = "E:\\measures\\indices";

    @Test
    public void search1000a1bIn20000a1bOnSingleIndicesFile_singleMatchAt19000Found() throws Exception {
        IIndexStore indices = SearchIntegrationHelper.getIndexStoreFile(INDICES_FILE);
        IDataProvider pattern = DynamicData.startWith(1000, "a").then(1, "b");
        IDataProvider source = DynamicData.startWith(20000, "a").then(1, "b");

        ISearch search = SearchHelper.getKnuthMorrisPrattSearcher(indices, true);
        List<Long> findings = search.forPattern(pattern).inSource(source);
        Assert.assertTrue(findings.contains((long) 19000));
        Assert.assertTrue(findings.size() == 1);

        indices.close();
        FileAccessHelper.delete(INDICES_FILE);
    }

    @Test
    public void search1000a1bIn20000a1bOnMultipleIndicesFiles_singleMatchAt19000Found() throws Exception {
        IIndexStore indices = SearchIntegrationHelper.getIndexStoreFiles(INDICES_ROOT, 10);
        IDataProvider pattern = DynamicData.startWith(1000, "a").then(1, "b");
        IDataProvider source = DynamicData.startWith(20000, "a").then(1, "b");

        ISearch search = SearchHelper.getKnuthMorrisPrattSearcher(indices, true);
        List<Long> findings = search.forPattern(pattern).inSource(source);
        Assert.assertTrue(findings.contains((long) 19000));
        Assert.assertTrue(findings.size() == 1);

        indices.close();
        FileAccessHelper.deleteFolder(INDICES_ROOT);
    }

    @Test
    public void search1000000a1bIn20000000a1bOnMultipleIndicesFiles_singleMatchAt19000000Found() throws Exception {
        IDataProvider pattern = DynamicData.startWith(1000000, "a").then(1, "b");
        IDataProvider source = DynamicData.startWith(20000000, "a").then(1, "b");
        IIndexStore indices = SearchIntegrationHelper.getIndexStoreFiles(INDICES_ROOT, 100000);

        ISearch search = SearchHelper.getKnuthMorrisPrattSearcher(indices, true);
        List<Long> findings = search.forPattern(pattern).inSource(source);
        Assert.assertTrue(findings.contains((long) 19000000));
        Assert.assertTrue(findings.size() == 1);

        indices.close();
        FileAccessHelper.deleteFolder(INDICES_ROOT);
    }

    @Test
    public void search1000a1bIn20000a1bOnIndicesArray_singleMatchAt19000Found() throws Exception {
        IIndexStore indices = new IndexData();
        IDataProvider pattern = DynamicData.startWith(1000, "a").then(1, "b");
        IDataProvider source = DynamicData.startWith(20000, "a").then(1, "b");

        ISearch search = SearchHelper.getKnuthMorrisPrattSearcher(indices, true);
        List<Long> findings = search.forPattern(pattern).inSource(source);
        Assert.assertTrue(findings.contains((long) 19000));
        Assert.assertTrue(findings.size() == 1);
    }

    @Test
    public void search1000000a1bIn20000000a1bOnIndicesArray_singleMatchAt19000000Found() throws Exception {
        IIndexStore indices = new IndexData();
        IDataProvider pattern = DynamicData.startWith(1000000, "a").then(1, "b");
        IDataProvider source = DynamicData.startWith(20000000, "a").then(1, "b");

        ISearch search = SearchHelper.getKnuthMorrisPrattSearcher(indices, true);
        List<Long> findings = search.forPattern(pattern).inSource(source);
        Assert.assertTrue(findings.contains((long) 19000000));
        Assert.assertTrue(findings.size() == 1);
    }

    @Test
    public void searchFloriansFilesBuffer1000_matchAtEnd() throws Exception {
        String florianPATTERN_FILE = "E:\\measures\\florian\\TestFilePattern.txt";
        String florianTEXT_FILE = "E:\\measures\\florian\\TestFileText.txt";
        String florianINDICES_ROOT = "E:\\measures\\florian\\indices";

        int bufferSize = 1000;
        AsymmetricDataProvider patternFile = SearchIntegrationHelper.getFileAsym(florianPATTERN_FILE, bufferSize);
        IDataProvider textFile = SearchIntegrationHelper.getFile(florianTEXT_FILE, bufferSize);
        IIndexStore prefixIndices = SearchIntegrationHelper.getIndexStoreFiles(florianINDICES_ROOT, bufferSize);
        ISearch kmpSearch = SearchHelper.getKnuthMorrisPrattSearcher(prefixIndices, false);
        List<Long> matches = kmpSearch.forPattern(patternFile).inSource(textFile);

        SearchIntegrationHelper.close(prefixIndices, patternFile, textFile);
        FileAccessHelper.deleteFolder(florianINDICES_ROOT);
        Assert.assertEquals(1, matches.size());
    }
}
