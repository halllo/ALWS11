package de.alws11;

/**
 * This class represents a found match in an indexable data source.
 */
public class MatchFoundArgs {

    /**
     * This field indicates whether a searching algorithm shall continue searching for more matches. If it is set to FALSE, a search algorithm iss told to stop searching. TRUE means the search should go on. TRUE is default.
     */
    public boolean shouldContinue;

    /**
     * This field holds the index at which the match was found in the indexable data source. If there was a match without a position, the field holds the value -1.
     */
    public long position;

    /**
     * This constructor creates a match representative with a default match position of -1 and the indication of continue searching.
     */
    public MatchFoundArgs() {
        shouldContinue = true;
        position = -1;
    }
}
