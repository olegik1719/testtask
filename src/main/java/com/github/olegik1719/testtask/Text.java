package com.github.olegik1719.testtask;

import lombok.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class Text implements Searchable {
    //private static final int BUFFER_SIZE = 256;
    private static final int BUFFER_SIZE = 2;
    //private static final long MAX_STRING_SIZE = Integer.MAX_VALUE;
    private static final long MAX_STRING_SIZE = 7;
    private ArrayList<StringBuilder> text;

    public Text(InputStream inputStream) throws IOException {
        InputStreamReader reader = new InputStreamReader(inputStream);
        char[] buffer = new char[BUFFER_SIZE];
        //int readChars = BUFFER_SIZE;
        text = new ArrayList<>();
        text.add(new StringBuilder());
        int readChars;
        //while (readChars == BUFFER_SIZE){
        do{
            readChars = reader.read(buffer);
            StringBuilder charSequence = text.get(text.size()-1);
            if ((long) charSequence.length() + readChars > MAX_STRING_SIZE){
                charSequence = new StringBuilder();
                text.add(charSequence);
            }
            if (readChars != -1) charSequence.append(buffer,0,readChars);
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
        return simpleSearch(substring,count);
    }

    private Position getNextPos(Position position){
        int currentString = position.getNumString();
        int posInString = position.getPosInString();

        if (posInString < text.get(currentString).length() - 1)
            return new Position(currentString, ++posInString);

        if (currentString < text.size() - 1)
            return new Position(++currentString,0);

        return null;
    }


    private Collection<Index> simpleSearch(String substring, int count){
        Collection<Index> result;
        if (count <= 0){
            result = new ArrayList<>();
            count = Integer.MAX_VALUE;
        }else {
            result = new ArrayList<>(count + 1);
        }
        if (substring.length() < 3) throw new RuntimeException("Length for search must be more than 3!");

        //TODO write right condition

        for (int i = 0; i < text.size() && result.size() < count ; i++) {
            for (int j = 0; j < text.get(i).length() && result.size() < count; j++){

                Position currentPosition = new Position(i,j);
                int k = 0;

                while (k < substring.length()
                        && currentPosition != null
                        && charAt(currentPosition) == substring.charAt(k)){
                    k++;
                    currentPosition = getNextPos(currentPosition);
                }

                if (k == substring.length()) {
                    result.add(new Position(i,j));
                }
            }
        }

//        for (int i = 0; i<=text.length() - substring.length() && result.size()<count; i++){
//            int j = 0;
//            while (j<substring.length()
//                    && i+j<text.length()
//                    && text.charAt(i+j)==text.charAt(j)
//                    ){
//                j++;
//            }
//            if (j == substring.length())
//                result.add(new SimpleText.Position(i));
//        }
        return result;
        //return found;
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

    public OutputStream getText(){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            for (StringBuilder sb: text) {
                outputStream.write(sb.toString().getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream;
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

        @Override
        public String toString() {
            return "[ " + numString + "; " + posInString + "]";
        }
    }
}
