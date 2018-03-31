package com.github.olegik1719.testtask;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

public class Store implements Storing<Text>{

    private Collection<Text> store;

    public Store(){
        store = new HashSet<>();
    }

    @Override
    public Storing addToStore(InputStream is) {
        Text text;
        try {
            text = new Text(is);
        } catch (IOException e){

            return this;
        }
        store.add(text);
        return this;
    }

    @Override
    public Map<Text, Collection> search(String substring) {
        return null;
    }

    @Override
    public Map<Text, Object> searchFirst(String substring) {
        return null;
    }

    @Override
    public Map<Text, Collection> SearchFirsts(String substring, int num) {
        return null;
    }

    @Override
    public Collection<Text> contains() {
        return null;
    }

    @Override
    public OutputStream getFromStore(Text text) {
        return null;
    }
}
