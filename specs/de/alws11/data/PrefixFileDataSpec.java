package de.alws11.data;

import de.alws11.KnuthMorrisPratt.IIndexStore;
import junit.framework.Assert;
import org.junit.Test;

public class PrefixFileDataSpec {
    @Test
    public void noIndexWritten_noIndexReadable() throws Exception {
        IIndexStore indices = DataHelper.getIndexStore();
        Assert.assertEquals(-1, indices.getIndex(0));
    }

    @Test
    public void oneIndexWritten_oneIndexReadable() throws Exception {
        IIndexStore indices = DataHelper.getIndexStore();
        indices.pushIndex(1);
        Assert.assertEquals(1, indices.getIndex(0));
    }

    @Test
    public void twoIndicesWritten_firstIndexReadable() throws Exception {
        IIndexStore indices = DataHelper.getIndexStore();
        indices.pushIndex(1);
        indices.pushIndex(2);
        Assert.assertEquals(1, indices.getIndex(0));
    }

    @Test
    public void twoIndicesWritten_secondIndexReadable() throws Exception {
        IIndexStore indices = DataHelper.getIndexStore();
        indices.pushIndex(1);
        indices.pushIndex(2);
        Assert.assertEquals(2, indices.getIndex(1));
    }

    @Test
    public void twoIndicesWritten_bothReadableInOrder() throws Exception {
        IIndexStore indices = DataHelper.getIndexStore();
        indices.pushIndex(1);
        indices.pushIndex(2);
        Assert.assertEquals(1, indices.getIndex(0));
        Assert.assertEquals(2, indices.getIndex(1));
    }

    @Test
    public void twoIndicesWritten_bothReadableInReverseOrder() throws Exception {
        IIndexStore indices = DataHelper.getIndexStore();
        indices.pushIndex(1);
        indices.pushIndex(2);
        Assert.assertEquals(2, indices.getIndex(1));
        Assert.assertEquals(1, indices.getIndex(0));
    }

    @Test
    public void twoIndicesWritten_secondOnlyReadableAfterItsPush() throws Exception {
        IIndexStore indices = DataHelper.getIndexStore();
        indices.pushIndex(1);
        Assert.assertEquals(-1, indices.getIndex(1));
        indices.pushIndex(2);
        Assert.assertEquals(2, indices.getIndex(1));
    }

    @Test
    public void twoIndicesWritten_oneAfterLastNotReadable() throws Exception {
        IIndexStore indices = DataHelper.getIndexStore();
        indices.pushIndex(1);
        indices.pushIndex(2);
        Assert.assertEquals(-1, indices.getIndex(2));
    }

    @Test
    public void twoIndicesWritten_oneBeforeFirstNotReadable() throws Exception {
        IIndexStore indices = DataHelper.getIndexStore();
        indices.pushIndex(1);
        indices.pushIndex(2);
        Assert.assertEquals(-1, indices.getIndex(-1));
    }
}
