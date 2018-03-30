package com.github.olegik1719.testtask;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Map;

public interface Storing {
    Storing addToStore(InputStream is);
    Map<Searchable,Collection<Integer>> search(String substring);
    Map<Searchable,Integer> searchFirst(String substring);
    OutputStream getFromStore(Searchable text);
}
