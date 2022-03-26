package adtInterfaces;

import java.util.Iterator;

public interface SortedListInterface<T extends Comparable<T>> {

    int size();
    boolean add(T newEntry);
    boolean remove(T anEntry);
    boolean contains(T anEntry);
    void clear();
    T get(int index);
    int getNumberOfEntries();
    boolean isEmpty();
    Iterator<T> iterator();
} 
    

