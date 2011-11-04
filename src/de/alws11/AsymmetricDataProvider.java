package de.alws11;

/**
 * This class provides two data providers as one and allows usage of both explicitly.
 */
public class AsymmetricDataProvider implements IDataProvider {
    private IDataProvider _primary;
    private IDataProvider _secondary;

    /**
     * This constructor creates a data provider from a single data provider.
     *
     * @param primary This parameter is the data provider that is used for all data access.
     */
    public AsymmetricDataProvider(IDataProvider primary) {
        _primary = primary;
        _secondary = null;
    }

    /**
     * This constructor creates a data provider from two data providers.
     *
     * @param primary   This parameter is the data provider that is used for all data access except getAsymmetricPosition.
     * @param secondary This parameter is the data provider that is used for data access of getAsymmetricPosition.
     */
    public AsymmetricDataProvider(IDataProvider primary, IDataProvider secondary) {
        _primary = primary;
        _secondary = secondary;
    }

    /**
     * This method provides the character at a given position by usage of the primary data provider.
     *
     * @param index The zero based position from where the character is requested.
     * @return The method returns the character at the given index position.
     */
    public char getPosition(long index) {
        return _primary.getPosition(index);
    }

    /**
     * This method provides the character at a given position by usage of the secondary data provider. If there is no secondary data provider, the primary data provider is used.
     *
     * @param index The zero based position from where the character is requested.
     * @return The method returns the character at the given index position.
     */
    public char getAsymmetricPosition(long index) {
        return _secondary != null ?
                _secondary.getPosition(index) : _primary.getPosition(index);
    }

    /**
     * This method returns the size of the underlying data structure of the the primary data provider.
     *
     * @return The method returns the amount of characters in the primary data provider.
     */
    public long size() {
        return _primary.size();
    }

    /**
     * This method closes the primary and the secondary data provider, if there is one.
     */
    public void close() {
        _primary.close();
        if (_secondary != null) _secondary.close();
    }
}
