package com.github.olegik1719.testtask;

import lombok.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;

public class Text {

    private static final Logger log = Logger.getLogger(Text.class.getName());
    static {

        try {
            FileHandler fh = new FileHandler(Text.class.getName() + ".log");
            log.addHandler(fh);
            log.setLevel(Level.ALL);
            fh.setFormatter(new SimpleFormatter());
            log.setUseParentHandlers(false);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private static final int BUFFER_SIZE = 1024;
    private static final int MAX_STRING_SIZE = 20*BUFFER_SIZE;
    private ArrayList<StringBuilder> text;
    private final Position BEGIN_TEXT;
    private final Position END_TEXT;

    /**
     * Создает запись Текст:
     * @param inputStream -- считывает из потока
     * @throws IOException -- возможен. Обрабатывается выше.
     */

    public Text(InputStream inputStream) throws IOException {
        InputStreamReader reader = new InputStreamReader(inputStream);
        char[] buffer = new char[BUFFER_SIZE];
        text = new ArrayList<>();
        text.add(new StringBuilder());
        int readChars;

        do{
            readChars = reader.read(buffer);
            StringBuilder charSequence = text.get(text.size()-1);
            if (charSequence.length() + readChars > MAX_STRING_SIZE){
                charSequence = new StringBuilder();
                text.add(charSequence);
            }
            if (readChars != -1) charSequence.append(buffer,0,readChars);

            log.log(Level.FINE,"Readed: " + String.valueOf(buffer));

        }while (readChars == BUFFER_SIZE);

        BEGIN_TEXT = new Position(0,0);
        END_TEXT = new Position(text.size()-1,text.get(text.size()-1).length()-1);
    }

    /**
     * Находит все вхождения строки в тексте
     * @param substring -- строка, которую ищем
     * @return возвращает Список результатов -- перед подстрокой, подстроку, послеподстроки
     */

    public Collection<Result> findAll(CharSequence substring){
        return getResults(substring, -1);
    }

    /**
     * Находит первые несколько вхождений строки в тексте
     * @param substring -- строка, которую ищем
     * @param num -- количество результатов, которые нам нужны
     * @return возвращает Список результатов -- перед подстрокой, подстроку, послеподстроки
     */

    public Collection<Result> findFirsts(CharSequence substring, int num){
        return getResults(substring, num);
    }

    /**
     * Находит первое вхождение строки в тексте
     * @param substring -- строка, которую ищем
     * @return результат -- перед подстрокой, подстроку, послеподстроки
     */
    public Result findFirst(CharSequence substring){
        Collection<Result> result = getResults(substring, 1);
        Iterator<Result> iterator = result.iterator();

        return iterator.hasNext()? iterator.next():null;
    }

    public boolean contains(CharSequence substring){
        return findFirst(substring) != null;
    }

    /**
     * Главный поисковик. На данный момент использует простейший способ поиска подстроки.
     * @param substring -- строка для поиска.
     * @param count -- количество для поиска. Если меньше или равно нулю, ищет все.
     * @return Список Позиций начала подстроки в строке.
     */

    private Collection<Position> search(CharSequence substring, int count){
        return simpleSearch(substring,count);
    }

    /**
     * Две следующие процедуры -- перемещают курсор по тексту на один символ.
     * @param position изначальная позиция
     * @return смещение влево или вправо
     */
    private Position getNextPos(Position position){
        int currentString = position.getNumString();
        int posInString = position.getPosInString();

        if (posInString < text.get(currentString).length() - 1)
            return new Position(currentString, ++posInString);

        if (currentString < text.size() - 1)
            return new Position(++currentString,0);

        return position;
    }

    private Position getPrevPos(Position position){
        int currentString = position.getNumString();
        int posInString = position.getPosInString();

        if (posInString > 0)
            return new Position(currentString, --posInString);

        if (currentString > 0)
            return new Position(++currentString,0);

        return position;
    }

    /**
     * Две следующие процедуры -- перемещают курсор по тексту на заданное количество символов.
     * @param position изначальная позиция
     * @param count величина смещения
     * @return смещение влево или вправо
     */
    private Position getNextPos(Position position, int count){
        //TODO without loop Algorithm

        for (int i = 0; i < count && !isEndText(position); i++) {
            position = getNextPos(position);
        }

        return position;
    }

    private Position getPrevPos(Position position, int count){
        //TODO without loop Algorithm

        for (int i = 0; i < count && !isBeginText(position); i++) {
            position = getPrevPos(position);
        }

        return position;
    }


    /**
     * Две следующие процедуры -- проверяют, указывает ли позиция на конец текста.
     * @param position позиция для проверки
     * @return является ли позиция концом или началом текста
     */
    private boolean isEndText(Position position){
        return position.equals(END_TEXT);
    }

    private boolean isBeginText(Position position){
        return position.equals(BEGIN_TEXT);
    }

    /**
     * Реализует алгоритм простого поиска.
     * @param substring -- строка для поиска
     * @param count -- количество элементов, которые надо найти
     * @return коллекцию позиций начала строки
     */
    private Collection<Position> simpleSearch(CharSequence substring, int count){
        Collection<Position> result;

        if (count <= 0){
            result = new ArrayList<>();
            count = Integer.MAX_VALUE;
        }else {
            result = new ArrayList<>(count + 1);
        }

        if (substring.length() < 3) {
            //throw new RuntimeException("Length for search must be more than 3!");
            log.log(Level.WARNING, " Length for search must be more than 2! ");
            return result;
        }

        for (int i = 0; i < text.size() && result.size() < count ; i++) {
            for (int j = 0; j < text.get(i).length() && result.size() < count; j++){

                Position currentPosition = new Position(i,j);
                int k = 0;

                while (k < substring.length()
                        && !isEndText(currentPosition)
                        && charAt(currentPosition) == substring.charAt(k)){
                    k++;
                    currentPosition = getNextPos(currentPosition);
                }

                if (k == substring.length()) {
                    result.add(new Position(i,j));
                }
            }
        }

        return result;
    }

    private int charAt(Position position){
        return charAt(position.getNumString(),position.getPosInString());
    }

    private int charAt(int numString, int posInString){

        if (numString >= text.size()) return -1;
        StringBuilder currentString = text.get(numString);

        if (posInString >= currentString.length()) return -1;

        return currentString.charAt(posInString);
    }

    /**
     * Возвращает результат поиска в виде результата:
     * Перед искомой строкой, строку, после искомой строки
     * @param substring -- строка поиска;
     * @param count -- необходимое количество резульатов
     * @param radius -- величина префикса и постфикса для окружения
     * @return коллекцию резульатов
     */
    private Collection<Result> getResults(CharSequence substring, int count, int radius){
        return search(substring,count).stream()
                .map(position -> getResult(
                        position,
                        substring.length(),
                        getPrevPos(position,radius),
                        getNextPos(position,substring.length()+radius))
                )
                .collect(Collectors.toList());
        //return null;
    }

    /**
     * То же, что и выше, но размер радиуса задан. 5
     * @param substring подстрока
     * @param count количество
     * @return коллекция результатов
     */
    private Collection<Result> getResults(CharSequence substring, int count){
        return getResults(substring, count, 5);
    }

    /**
     * Процедура создания результата
     * @param found -- позиция, на которой начинается подстрока
     * @param substrLength -- длина подстроки
     * @param begin -- позиция, на которой начинается результат, префикс
     * @param end -- позиция, на которой заканчивается результат, постфикс.
     * @return возвращает результат.
     */
    //TODO слишком много параметров. Пожно сократить до 3х
    private Result getResult(Position found, int substrLength, Position begin, Position end ){
        StringBuilder prefix = new StringBuilder();
        StringBuilder substring = new StringBuilder();
        StringBuilder postfix = new StringBuilder();
        int printed = -1;
        StringBuilder result = prefix;
        Position current = begin;

        while (!current.equals(end)){

            if (printed == substrLength){
                result = postfix;
            }

            if (current.equals(found)){
                result=substring;
                printed++;
            }

            result.append((char)charAt(current));

            if (printed > -1 && printed < substrLength + 1){
                printed++;
            }

            current = getNextPos(current);
        }
        return new Result(prefix,substring, postfix);
    }

    public CharSequence getBegin(int count){
        Position current = BEGIN_TEXT;
        StringBuilder result = new StringBuilder(count);

        while (count > 0 && !isEndText(current)){
            result.append((char) charAt(current));
            count--;
            current = getNextPos(current);
        }

        if (count == 0 && !isEndText(current)){
            result.append("...");
        }

        return result;
    }

    /**
     * Метод, возвращающий первые 10 символов текста
     */
    public CharSequence getBegin(){
        return getBegin(10);
    }

    /**
     * Преобразует Текст в поток байт
     * @return поток.
     */
    public ByteArrayOutputStream getText(){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            for (StringBuilder sb: text) {
                outputStream.write(sb.toString().getBytes());
            }
            return outputStream;
        } catch (IOException e) {
            log.log(Level.SEVERE, "IOException " + e.getMessage());
            return null;
        }


    }


    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private class Position{

        /**
         * Number of string in List;
         */
        private final int numString;

        /**
         * Number of symbol in List;
         */
        private final int posInString;

        @Override
        public String toString() {
            return "[ " + numString + "; " + posInString + "]";
        }

        @Override
        public boolean equals(Object o){
            return o != null && o instanceof Position
                    && ((Position) o).numString == numString
                    && ((Position) o).posInString == posInString
                    ;
        }

        private boolean equals(Position o){
            return o != null &&  o.numString == numString
                 && o.posInString == posInString
                 ;
        }
    }
}
