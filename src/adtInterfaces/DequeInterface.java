package adtInterfaces;

import java.util.Iterator;

public interface DequeInterface<T>{
    void addFirst(T element);
    void addLast(T element);
    void removeFirst();
    void removeLast();
    T peekFirst();
    T peekLast();
    T pollFirst();
    T pollLast();
    boolean contains(T element);
    int size();
    boolean isEmpty();
    void clear();
    void addAll(T[] elements);
    String toString();
    T[] toArray();    
    Iterator<T> iterator();
}
