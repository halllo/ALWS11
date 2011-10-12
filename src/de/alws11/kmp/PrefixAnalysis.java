package de.alws11.kmp;

import de.alws11.IDataProvider;
import de.alws11.IPrefixStore;

/**
 * Created by IntelliJ IDEA.
 * User: Manuel
 * Date: 09.10.11
 * Time: 16:44
 * To change this template use File | Settings | File Templates.
 */
public class PrefixAnalysis {
    public static void ForPattern(IDataProvider pattern, IPrefixStore prefixes) {
        prefixes.RequestSize(pattern.Size() + 1);
        long i = 0;
        long j = -1;
        prefixes.SetPosition(i, j);
        while (i < pattern.Size()) {
            while (j >= 0 && !pattern.GetPosition(j).equals(pattern.GetPosition(i))) {
                j = prefixes.GetPosition(j);
            }
            i = i + 1;
            j = j + 1;
            prefixes.SetPosition(i, j);
        }
    }
}
