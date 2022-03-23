package adtInterfaces;

import java.util.Iterator;

public interface StackInterface<T> {
    boolean push(T element);
    T pop();
    T peek();
    boolean contains(T element);
    boolean isEmpty();
    int size();
    void clear();
    String toString();
    Iterator<T> iterator();
}
