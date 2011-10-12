package de.alws11;

import java.util.List;

public interface ISearch {
    List<Long> Search(IDataProvider source, IDataProvider pattern);
}
