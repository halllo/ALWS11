package de.alws11;

/**
 * This interface provides a callback method that is to be invoked when a match has been found in an indexable data source.
 */
public interface IMatchFound {
    /**
     * This method is the callback which is invoked when a match is found in an indexable data source.
     *
     * @param e This parameter holds the event arguments of the callback. These arguments include the position of the found match.
     */
    public void newMatch(MatchFoundArgs e);
}
