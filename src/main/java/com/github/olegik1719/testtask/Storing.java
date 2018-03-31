package com.github.olegik1719.testtask;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Map;

public interface Storing<T extends Searchable> {
    Storing addToStore(InputStream is);
    Map<T,Collection> search(String substring);
    Map<T,Object> searchFirst(String substring);
    Map<T,Collection> SearchFirsts(String substring, int num);
    Collection<T> contains();
    OutputStream getFromStore(T text);
}
