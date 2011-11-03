package de.alws11;

import junit.framework.Assert;
import org.junit.Test;

import java.util.List;

public class FlorianTests {
    private void search(int bufferSize, String textPath, String patternPath, String indicesRootPath) throws Exception {
        AsymmetricDataProvider patternFile = SearchIntegrationHelper.getFileAsym(patternPath, bufferSize);
        IDataProvider textFile = SearchIntegrationHelper.getFile(textPath, bufferSize);
        IIndexStore prefixIndices = SearchIntegrationHelper.getIndexStoreFiles(indicesRootPath, bufferSize);
        ISearch kmpSearch = SearchHelper.getKnuthMorrisPrattSearcher(prefixIndices, false);
        List<Long> matches = kmpSearch.forPattern(patternFile).inSource(textFile);
        SearchIntegrationHelper.close(prefixIndices, patternFile, textFile);
        Assert.assertEquals(1, matches.size());
    }

    @Test
    public void searchFloriansFilesBuffer1000_01() throws Exception {
        search(1000,
                "E:\\measures\\florian\\01 TextTestFile Pos 10Mio.txt",
                "E:\\measures\\florian\\01 PatternTestFile.txt",
                "E:\\measures\\florian\\indices");
        FileAccessHelper.deleteFolder("E:\\measures\\florian\\indices");
    }

    @Test
    public void searchFloriansFilesBuffer1000_02() throws Exception {
        search(1000,
                "E:\\measures\\florian\\02 TextTestFile Pos 100000.txt",
                "E:\\measures\\florian\\02 PatternTestFile.txt",
                "E:\\measures\\florian\\indices");
        FileAccessHelper.deleteFolder("E:\\measures\\florian\\indices");
    }

    @Test
    public void searchFloriansFilesBuffer1000_03() throws Exception {
        search(1000,
                "E:\\measures\\florian\\03 TextTestFile Pos5432178.txt",
                "E:\\measures\\florian\\03 PatternTestFile.txt",
                "E:\\measures\\florian\\indices");
        FileAccessHelper.deleteFolder("E:\\measures\\florian\\indices");
    }

    @Test
    public void searchFloriansFilesBuffer1000_04() throws Exception {
        search(1000,
                "E:\\measures\\florian\\04 TestFileText.txt",
                "E:\\measures\\florian\\04 TestFilePattern.txt",
                "E:\\measures\\florian\\indices");
        FileAccessHelper.deleteFolder("E:\\measures\\florian\\indices");
    }
}
