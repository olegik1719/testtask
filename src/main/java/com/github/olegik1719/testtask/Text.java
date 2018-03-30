package com.github.olegik1719.testtask;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public class Text implements Searchable {
    private static final int BUFFER_SIZE = 256;
    LinkedList<StringBuilder> text;

    public Text(InputStream inputStream) throws IOException {
        InputStreamReader reader = new InputStreamReader(inputStream);
        char[] buffer = new char[BUFFER_SIZE];
        int readChars = BUFFER_SIZE;
        text = new LinkedList<>();
        text.add(new StringBuilder());
        while (readChars == BUFFER_SIZE){
            readChars = reader.read(buffer);
            StringBuilder charSequence = text.getLast();
            if ((long) charSequence.length() + readChars > (long) Integer.MAX_VALUE){
                charSequence = new StringBuilder();
                text.addLast(charSequence);
            }
            charSequence.append(buffer,0,readChars);
        }
    }

    @Override
    public Collection<Integer> searchAll(String substring) {
        return search(substring,0);
    }

    @Override
    public Collection<Integer> searchFirsts(String substring, int num) {
        return search(substring,num);
    }

    @Override
    public int searchFirst(String substring) {
        return search(substring, 1).iterator().next();
    }

    private Collection<Integer> search(String substring, int count){
        return null;
    }
}
