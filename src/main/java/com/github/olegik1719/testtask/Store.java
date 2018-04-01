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

    public Map<Text, Collection<Text.Position>> searchAll(String substring) {
        return store.stream().collect(Collectors.toMap(text-> text, text -> text.searchAll(substring)));
    }

    public Map<Text, Text.Position> searchFirst(String substring) {
        return store.stream().collect(Collectors.toMap(text-> text, text -> text.searchFirst(substring)));
    }

    public Map<Text, Collection<Text.Position>> searchFirsts(String substring, int num) {
        return store.stream().collect(Collectors.toMap(text-> text, text -> text.searchFirsts(substring,num)));
    }

    public Collection<Text> contains(String substring) {
        return store.stream().filter(text -> text.contains(substring)).collect(Collectors.toList());
    }

    public OutputStream getFromStore(Text text) {
        return text.getText();
    }
}
