package com.github.olegik1719.testtask;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Map;

public interface Storing<T extends Searchable> {
    Storing addToStore(InputStream is);
    Map<T,Collection<? extends Searchable.Index>> searchAll(String substring);
    Map<T,? extends Searchable.Index> searchFirst(String substring);
    Map<T,Collection<? extends Searchable.Index>> searchFirsts(String substring, int num);
    Collection<T> contains(String substring);
}
