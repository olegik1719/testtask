package com.github.olegik1719.testtask;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Store{

    private List<Text> store;

    public Store(){
        store = new ArrayList<>();
    }

    public Store addToStore(InputStream is) {
        Text text;
        try {
            text = new Text(is);
        } catch (IOException e){

            return this;
        }
        store.add(text);
        return this;
    }

    public Map<Text, Collection<Result>> searchAll(String substring) {
        return store.stream().collect(Collectors.toMap(text-> text, text -> text.findAll(substring)));
    }

    public Map<Text, Result> searchFirst(String substring) {
        return store.stream().collect(Collectors.toMap(text-> text, text -> text.findFirst(substring)));
    }

    public Map<Text, Collection<Result>> searchFirsts(String substring, int num) {
        return store.stream().collect(Collectors.toMap(text-> text, text -> text.findFirsts(substring,num)));
    }

    public Collection<Text> contains(String substring) {
        return store.stream().filter(text -> text.contains(substring)).collect(Collectors.toList());
    }

    public OutputStream getFromStore(Text text) {
        return text.getText();
    }
}
