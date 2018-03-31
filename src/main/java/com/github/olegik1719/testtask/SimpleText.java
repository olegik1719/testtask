package com.github.olegik1719.testtask;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

public class SimpleText implements Searchable{

    private String text;

    public SimpleText(String input){
        text = input;
    }

    @Override
    public Collection<Index> searchAll(String substring) {
        return search(substring,-1);
    }

    @Override
    public Collection<Index> searchFirsts(String substring, int num) {
        return search(substring,num);
    }

    private Collection<Index> search(String substring, int count){
        if (count <= 0){
            count = text.length();
        }
        Collection<Index> result = new ArrayList<>(count + 1);

        if (substring.length() == 0){
            for (int i = 0; i <= count; i++)
                result.add(new Position(i));
            return result;
        }

        for (int i = 0; i<=text.length() - substring.length() && result.size()<count; i++){
            int j = 0;
            while (j<substring.length()
                    && i+j<text.length()
                    && text.charAt(i+j)==text.charAt(j)
                    ){
                j++;
            }
            if (j == substring.length())
                result.add(new Position(i));
        }
        return result;
        //return found;
    }

    @Override
    public int charAt(Index index) {
        return index instanceof Position ?
                charAt((Position) index) : -1;
    }

    public int charAt(Position position) {
        return position.getNumPosition() < text.length() ?
                text.charAt(position.getNumPosition()) : -1;
    }


    @Getter
    @Setter(AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public class Position implements Index{
        private int numPosition;

        @Override
        public String toString() {
            return String.valueOf(numPosition);
        }
    }
}
