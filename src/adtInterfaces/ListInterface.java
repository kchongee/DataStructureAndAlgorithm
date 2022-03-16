package adtInterfaces;

import java.util.Iterator;

public interface ListInterface<T> {
    boolean add (T element);
    boolean add (int index, T element);
    boolean remove (int index);
    boolean remove (T element);
    T get(int index);
    int get(T element);
    boolean replace(int index, T element);
    boolean contains(T element);
    boolean isEmpty();
    int size();
    void clear();
    String toString();
    Iterator<T> iterator();
}
