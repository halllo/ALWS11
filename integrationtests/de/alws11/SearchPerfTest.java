package de.alws11;

import de.alws11.data.DynamicData;
import de.alws11.data.IndexData;
import junit.framework.Assert;
import org.junit.Test;

import java.util.List;

public class SearchPerfTest {
    private final String INDICES_FILE = "E:\\indices.txt";
    private final String INDICES_ROOT = "E:\\indices";

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
        IIndexStore indices = SearchIntegrationHelper.getIndexStoreFiles(INDICES_ROOT, 100000);
        IDataProvider pattern = DynamicData.startWith(1000000, "a").then(1, "b");
        IDataProvider source = DynamicData.startWith(20000000, "a").then(1, "b");

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
}
