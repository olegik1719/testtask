package com.github.olegik1719.testtask;

import lombok.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.LinkedList;

public class Text implements Searchable {
    //private static final int BUFFER_SIZE = 256;
    private static final int BUFFER_SIZE = 2;
    //private static final long MAX_STRING_SIZE = Integer.MAX_VALUE;
    private static final long MAX_STRING_SIZE = 3;
    private LinkedList<StringBuilder> text;

    public Text(InputStream inputStream) throws IOException {
        InputStreamReader reader = new InputStreamReader(inputStream);
        char[] buffer = new char[BUFFER_SIZE];
        //int readChars = BUFFER_SIZE;
        text = new LinkedList<>();
        text.add(new StringBuilder());
        int readChars;
        //while (readChars == BUFFER_SIZE){
        do{
            readChars = reader.read(buffer);
            StringBuilder charSequence = text.getLast();
            if ((long) charSequence.length() + readChars > MAX_STRING_SIZE){
                charSequence = new StringBuilder();
                text.addLast(charSequence);
            }
            charSequence.append(buffer,0,readChars);
        }while (readChars == BUFFER_SIZE);
    }

    @Override
    public Collection<Index> searchAll(String substring) {
        return search(substring,-1);
    }

    @Override
    public Collection<Index> searchFirsts(String substring, int num) {
        return search(substring,num);
    }

    @Override
    public int charAt(Index index) {
        return index instanceof Position? charAt((Position) index) : -1;
    }

    private Collection<Index> search(String substring, int count){
        boolean searchAll = count == 0;
        Collection found = new LinkedList();
        return found;
    }

    public int charAt(Position position){
        return charAt(position.getNumString(),position.getPosInString());
    }

    public int charAt(int numString, int posInString){
        if (numString >= text.size()) return -1;
        StringBuilder currentString = text.get(numString);
        if (posInString >= currentString.length()) return -1;
        return currentString.charAt(posInString);
    }

    @Getter
    @Setter(AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public class Position implements Index{

        /**
         * Number of string in List;
         */
        private int numString;

        /**
         * Number of symbol in List;
         */
        private int posInString;
    }
}
