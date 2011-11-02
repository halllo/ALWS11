package de.alws11;

import de.alws11.data.DynamicData;
import junit.framework.Assert;
import org.junit.Test;

import java.util.List;

public class TimeMeasures {
    private final String INDICES_ROOT = "E:\\measures\\indices";
    private final String TEXT_FILE_0 = "E:\\measures\\text0.txt";
    private final String TEXT_FILE_1 = "E:\\measures\\text1.txt";
    private final String TEXT_FILE_2 = "E:\\measures\\text2.txt";
    private final String TEXT_FILE_3 = "E:\\measures\\text3.txt";
    private final String TEXT_FILE_4 = "E:\\measures\\text4.txt";
    private final String TEXT_FILE_5 = "E:\\measures\\text5.txt";
    private final String TEXT_FILE_6 = "E:\\measures\\text6.txt";
    private final String TEXT_FILE_7 = "E:\\measures\\text7.txt";
    private final String TEXT_FILE_8 = "E:\\measures\\text8.txt";
    private final String TEXT_FILE_9 = "E:\\measures\\text9.txt";

    @Test
    public void create_patternAndTextFiles() throws Exception {
        DynamicDataHelper.create(TEXT_FILE_0, DynamicData.startWith(100l, "abc").then(1, "d"));
        DynamicDataHelper.create(TEXT_FILE_1, DynamicData.startWith(1000l, "abc").then(1, "d"));
        DynamicDataHelper.create(TEXT_FILE_2, DynamicData.startWith(10000l, "abc").then(1, "d"));
        DynamicDataHelper.create(TEXT_FILE_3, DynamicData.startWith(100000l, "abc").then(1, "d"));
        DynamicDataHelper.create(TEXT_FILE_4, DynamicData.startWith(1000000l, "abc").then(1, "d"));
        DynamicDataHelper.create(TEXT_FILE_5, DynamicData.startWith(10000000l, "abc").then(1, "d"));
        DynamicDataHelper.create(TEXT_FILE_1, DynamicData.startWith(1000000l, "abc").then(1, "d"));
        DynamicDataHelper.create(TEXT_FILE_6, DynamicData.startWith(100000000l, "abc").then(1, "d"));
        DynamicDataHelper.create(TEXT_FILE_7, DynamicData.startWith(1000000000l, "abc").then(1, "d"));
        DynamicDataHelper.create(TEXT_FILE_8, DynamicData.startWith(10000000000l, "abc").then(1, "d"));
        DynamicDataHelper.create(TEXT_FILE_9, DynamicData.startWith(100000000000l, "abc").then(1, "d"));
    }

    @Test
    public void searchKMP_text0_in_text3_buffer1000() throws Exception {
        measureKmpSearchTime(1000, TEXT_FILE_2, TEXT_FILE_0);
        FileAccessHelper.deleteFolder(INDICES_ROOT);
    }

    private void measureKmpSearchTime(int bufferSize, String textFilePath, String patternFilePath) throws Exception {
        IDataProvider textFile = SearchIntegrationHelper.getFile(textFilePath, bufferSize);
        AsymmetricDataProvider patternFile = SearchIntegrationHelper.getFileAsym(patternFilePath, bufferSize);
        IIndexStore prefixIndices = SearchIntegrationHelper.getIndexStoreFiles(INDICES_ROOT, bufferSize);

        ISearch kmpSearch = SearchHelper.getKnuthMorrisPrattSearcher(prefixIndices, false);
        List<Long> matches = kmpSearch.forPattern(patternFile).inSource(textFile);

        SearchIntegrationHelper.close(prefixIndices, patternFile, textFile);
        Assert.assertEquals(1, matches.size());
    }

    private void measureNaiveSearchTime(int bufferSize, String textFilePath, String patternFilePath) throws Exception {
        IDataProvider textFile = SearchIntegrationHelper.getFile(textFilePath, bufferSize);
        AsymmetricDataProvider patternFile = SearchIntegrationHelper.getFileAsym(patternFilePath, bufferSize);

        ISearch naiveSearch = SearchHelper.getNaiveSearcher(false);
        List<Long> matches = naiveSearch.forPattern(patternFile).inSource(textFile);

        SearchIntegrationHelper.close(patternFile, textFile);
        Assert.assertEquals(1, matches.size());
    }
}
