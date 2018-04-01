package com.github.olegik1719.testtask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;

public class Store{

    //private List<Text> store;
    private Map<Integer,Text> store;
    private int last = 0;


    public Store(){
        store = new HashMap<>();
    }

    synchronized public int addToStore(InputStream is) {
        Text text;
        try {
            text = new Text(is);
        } catch (IOException e){

            //return this;
            return -1;
        }
        //return this;
        return addToStore(text);
    }

    synchronized private int addToStore(Text text){
        store.put(last++,text);
        return store.size();
    }

    public Map<CharSequence, Collection<Result>> searchAll(CharSequence substring) {
        return store.keySet().stream().collect(Collectors.toMap(key-> store.get(key).getBegin(),
                key -> store.get(key).findAll(substring)));
    }

    public Map<CharSequence, Result> searchFirst(CharSequence substring) {
        return store.keySet().stream().collect(Collectors.toMap(key-> store.get(key).getBegin(),
                key -> store.get(key).findFirst(substring)));
    }

    public Map<CharSequence, Collection<Result>> searchFirsts(CharSequence substring, int num) {
        return store.keySet().stream().collect(Collectors.toMap(key-> store.get(key).getBegin(),
                key -> store.get(key).findFirsts(substring,num)));
    }

    public Collection<CharSequence> contains(CharSequence substring, int count) {
        int size = store.size();
        Collection<CharSequence> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(i).append(store.get(i).getBegin(count));
            result.add(sb);
        }
        return result;
    }

    public Collection<CharSequence> contains(CharSequence substring) {
        return contains(substring,10);
    }

    public Collection<CharSequence> list (int count){
        int size = store.size();
        Collection<CharSequence> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(i).append(store.get(i).getBegin(count));
            result.add(sb);
        }
        return result;
    }

    public Collection<CharSequence> list(){
        return list(10);
    }

    public ByteArrayOutputStream getFromStore(int index) {
        Text text = store.get(index);
        return text == null? null : text.getText();
    }
}
