package com.github.olegik1719.testtask;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Map;

public interface Storing {
    Storing addToStore(InputStream is);
    Map<Searchable,Collection<Integer>> search(String substring);
    Map<Searchable,Integer> searchFirst(String substring);
    Map<Searchable,Collection<Integer>> SearchFirsts(String substring, int num);
    Collection<Searchable> contains();
    OutputStream getFromStore(Searchable text);
}
