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
    private final String PATTERN_FILE_0 = "E:\\measures\\pattern0.txt";
    private final String PATTERN_FILE_1 = "E:\\measures\\pattern1.txt";
    private final String PATTERN_FILE_2 = "E:\\measures\\pattern2.txt";
    private final String PATTERN_FILE_3 = "E:\\measures\\pattern3.txt";
    private final String PATTERN_FILE_4 = "E:\\measures\\pattern4.txt";
    private final String PATTERN_FILE_5 = "E:\\measures\\pattern5.txt";
    private final String PATTERN_FILE_6 = "E:\\measures\\pattern6.txt";
    private final String PATTERN_FILE_7 = "E:\\measures\\pattern7.txt";

    private void createFile(String textPath, String patternPath,
                            long preRepetitions, String randomSet,
                            long repetitions, String pattern, String patternPostfix,
                            long postRepetitions) throws Exception {
        DynamicDataHelper.create(patternPath,
                DynamicData.startWith(repetitions, pattern)
                        .then(1, patternPostfix));
        DynamicDataHelper.create(textPath,
                DynamicData.startWithRandom(preRepetitions, randomSet)
                        .then(repetitions, pattern)
                        .then(1, patternPostfix)
                        .thenRandom(postRepetitions, randomSet));
    }

    @Test
    public void create_patternAndTextFiles() throws Exception {
        createFile(TEXT_FILE_0, PATTERN_FILE_0, 1000l, "aaab", 10l, "ab", "c", 100l);
        createFile(TEXT_FILE_1, PATTERN_FILE_1, 10000l, "aaab", 100l, "ab", "c", 100l);
        createFile(TEXT_FILE_2, PATTERN_FILE_2, 100000l, "aaab", 1000l, "ab", "c", 100l);
        createFile(TEXT_FILE_3, PATTERN_FILE_3, 1000000l, "aaab", 10000l, "ab", "c", 100l);
        createFile(TEXT_FILE_4, PATTERN_FILE_4, 10000000l, "aaab", 100000l, "ab", "c", 100l);
        createFile(TEXT_FILE_5, PATTERN_FILE_5, 100000000l, "aaab", 1000000l, "ab", "c", 100l);
        createFile(TEXT_FILE_6, PATTERN_FILE_6, 1000000000l, "aaab", 10000000l, "ab", "c", 100l);
        createFile(TEXT_FILE_7, PATTERN_FILE_7, 10000000000l, "aaab", 100000000l, "ab", "c", 100l);
    }

    @Test
    public void searchKMP_pattern6_in_text6_buffer1000() throws Exception {
        measureKmpSearchTime(1000, TEXT_FILE_6, PATTERN_FILE_6);
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
