package de.alws11;

import org.junit.Test;

public class PapeTests {
    private final String INDICES_ROOT = "E:\\measures\\indices";
    private final String TEST_ROOT = "E:\\WS201112\\AlgoLabWS11\\test\\";

    @Test
    public void times() throws Exception {
        Stopwatch s = new Stopwatch();
        System.out.println("PapeTests start: " + Stopwatch.dateTimeNow());
        System.out.println();
        File("worst_case_39.txt", s);
        File("worst_case_40.txt", s);
        File("worst_case_41.txt", s);
        File("worst_case_42.txt", s);
        File("worst_case_43.txt", s);
        System.out.println();
        System.out.println("PapeTests done: " + Stopwatch.dateTimeNow());
    }

    private void File(String file, Stopwatch s) throws Exception {
        Kmp(file, s);
        DeleteIndices(s);
        Naive(file, s);
        DeleteIndices(s);
    }

    private void Kmp(String file, final Stopwatch s) throws Exception {
        System.out.print("kmp::   " + file + "... ");
        s.start();
        TestFileWithKmp(TEST_ROOT + file, 1000000, new ITimePoint() {
            public void now() {
                System.out.print("next: " + s.getElapsedTime() + "ms ");
            }
        });
        System.out.println("found: " + s.stopAndTime() + "ms");
    }

    private void Naive(String file, final Stopwatch s) throws Exception {
        System.out.print("naive:: " + file + "... ");
        s.start();
        TestFileNaive(TEST_ROOT + file, 1000000);
        System.out.println("found: " + s.stopAndTime() + "ms");
    }

    private void DeleteIndices(Stopwatch s) throws Exception {
        System.out.print("(deleting indices... ");
        s.start();
        FileAccessHelper.deleteFolder(INDICES_ROOT);
        System.out.print(s.stopAndTime() + "ms)");
    }

    private void TestFileWithKmp(String testFile, int buffer, ITimePoint pointInTime) throws Exception {
        TimeMeasures.measureKmpSearchTime(buffer, testFile, testFile, INDICES_ROOT, pointInTime);
    }

    private void TestFileNaive(String testFile, int buffer) throws Exception {
        TimeMeasures.measureNaiveSearchTime(buffer, testFile, testFile);
    }
}
