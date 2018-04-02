package com.github.olegik1719.testtask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Store{

    //private List<Text> store;
    private Map<Integer,Text> store;
    private int last = 0;


    public Store(){
        store = new HashMap<>();
    }

    public int addToStore(InputStream is) {
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
        store.put(last,text);
        return last++;
    }

    public Map<CharSequence, Collection<Result>> searchAll(CharSequence substring) {
        Map<CharSequence, Collection<Result>> result = new HashMap<>();
        for (int key:store.keySet()){
            Collection<Result> keyResult = store.get(key).findAll(substring);
            if (!keyResult.isEmpty()){
                result.put(new StringBuilder(""+key+": ").append(store.get(key).getBegin()),keyResult);
            }
        }
        return result;
//        return store.keySet().stream().collect(Collectors.toMap(key-> store.get(key).getBegin(),
//                key -> store.get(key).findAll(substring)));
    }

    public Map<CharSequence, Result> searchFirst(CharSequence substring) {
        Map<CharSequence, Result> result = new HashMap<>();
        for (int key:store.keySet()){
            Result keyResult = store.get(key).findFirst(substring);
            if (keyResult != null){
                result.put(new StringBuilder(""+key+": ").append(store.get(key).getBegin()),keyResult);
            }
        }
        return result;
//        return store.keySet().stream().collect(Collectors.toMap(key-> store.get(key).getBegin(),
//                key -> store.get(key).findFirst(substring)));
    }

    public Map<CharSequence, Collection<Result>> searchFirsts(CharSequence substring, int num) {
        Map<CharSequence, Collection<Result>> result = new HashMap<>();
        for (int key:store.keySet()){
            Collection<Result> keyResult = store.get(key).findFirsts(substring, num);
            if (!keyResult.isEmpty()){
                result.put(new StringBuilder(""+key+": ").append(store.get(key).getBegin()),keyResult);
            }
        }
        return result;
//        return store.keySet().stream().collect(Collectors.toMap(key-> store.get(key).getBegin(),
//                key -> store.get(key).findFirsts(substring,num)));
    }

    public Collection<CharSequence> contains(CharSequence substring, int count) {
        int size = store.size();
        Collection<CharSequence> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            if (store.get(i).contains(substring)) {
                StringBuilder sb = new StringBuilder();
                sb.append(i).append(": ").append(store.get(i).getBegin(count));
                result.add(sb);
            }
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
            sb.append(i).append(": ").append(store.get(i).getBegin(count));
            result.add(sb);
        }
        return result;
    }

    public Collection<CharSequence> list(){
        return list(10);
    }

    public boolean isEmpty(){
        return store.isEmpty();
    }

    public ByteArrayOutputStream getFromStore(int index) {
        Text text = store.get(index);
        return text == null? null : text.getText();
    }
}
