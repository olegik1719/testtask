package com.github.olegik1719.testtask;

public class Result {
    private CharSequence[] result;
    public Result(CharSequence prefix, CharSequence substring, CharSequence postfix){
        result = new CharSequence[3];
        result[0] = prefix;
        result[1] = substring;
        result[2] = postfix;
    }

    public String getPrefix(){
        return result[0] == null? "" : result[0].toString();
    }

    public String getSubstring(){
        return result[0] == null? "" : result[1].toString();
    }

    public String getPostfix(){
        return result[2] == null? "" : result[2].toString();
    }

    @Override
    public String toString() {
        return toString("[","]");
    }

    public String toString(CharSequence beforeSubstring, CharSequence afterSubstring){
        return getPrefix()
                + beforeSubstring
                + getSubstring()
                + afterSubstring
                + getPostfix();
    }

    public String toString(CharSequence split) {
        return toString(split,split);
    }
}
