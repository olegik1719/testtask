package com.github.olegik1719.testtask;

import java.util.Collection;

/**
 * This is interface for our App's Core:
 * Text.
 * Here we can search any String.
 */
public interface Searchable {

    /**
     * Search all entries
     * @param substring for search
     * @return collection of position
     */
    Collection<Integer> searchAll(String substring);

    /**
     * Search all entries
     * @param substring for search
     * @param num entries for out
     * @return collection of positions
     */
    Collection<Integer> searchFirsts(String substring, int num);

    int searchFirst(String substring);

    default boolean contains(String substring){
        return searchFirst(substring) > 0;
    }
}
