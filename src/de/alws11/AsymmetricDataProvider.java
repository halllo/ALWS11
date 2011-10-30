package de.alws11;

public class AsymmetricDataProvider implements IDataProvider {
    private IDataProvider _primary;
    private IDataProvider _secondary;

    public AsymmetricDataProvider(IDataProvider primary) {
        _primary = primary;
        _secondary = null;
    }

    public AsymmetricDataProvider(IDataProvider primary, IDataProvider secondary) {
        _primary = primary;
        _secondary = secondary;
    }

    public char getPosition(long index) {
        return _primary.getPosition(index);
    }

    public char getAsymmetricPosition(long index) {
        return _secondary != null ?
                _secondary.getPosition(index) : _primary.getPosition(index);
    }

    public long size() {
        return _primary.size();
    }

    public void close() {
        _primary.close();
        if (_secondary != null) _secondary.close();
    }
}
