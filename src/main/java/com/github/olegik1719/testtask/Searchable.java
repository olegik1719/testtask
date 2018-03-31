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
    Collection<Index> searchAll(String substring);


    /**
     * Search all entries
     * @param substring for search
     * @param num entries for out
     * @return collection of positions
     */
    Collection<Index> searchFirsts(String substring, int num);


    /**
     * Search all entries
     * @param substring for search
     * @return position
     */
    default Index searchFirst(String substring){
        return searchFirsts(substring,1).iterator().next();
    }

    default boolean contains(String substring){
        return searchFirst(substring) != null;
    }



    int charAt(Index index);

    interface Index{
    }
}
