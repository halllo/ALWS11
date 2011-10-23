package de.alws11;

import java.util.List;

public interface ISearch {
    ISearch forPattern(IDataProvider pattern);

    List<Long> inSource(IDataProvider source);
}
