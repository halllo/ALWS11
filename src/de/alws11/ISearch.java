package de.alws11;

import java.util.List;

public interface ISearch {
    List<Long> search(IDataProvider source, IDataProvider pattern);
}
