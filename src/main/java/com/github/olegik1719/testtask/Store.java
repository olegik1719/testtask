package com.github.olegik1719.testtask;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Store implements Storing<Text>{

    private List<Text> store;

    public Store(){
        store = new ArrayList<>();
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
    public Map<Text, Collection<? extends Searchable.Index>> searchAll(String substring) {
        return store.stream().collect(Collectors.toMap(text-> text, text -> text.searchAll(substring)));
    }

    @Override
    public Map<Text, ? extends Searchable.Index> searchFirst(String substring) {
        return store.stream().collect(Collectors.toMap(text-> text, text -> text.searchFirst(substring)));
    }

    @Override
    public Map<Text, Collection<? extends Searchable.Index>> searchFirsts(String substring, int num) {
        return store.stream().collect(Collectors.toMap(text-> text, text -> text.searchFirsts(substring,num)));
    }


    @Override
    public Collection<Text> contains(String substring) {
        return store.stream().filter(text -> text.contains(substring)).collect(Collectors.toList());
    }

    public OutputStream getFromStore(Text text) {
        return text.getText();
    }
}
