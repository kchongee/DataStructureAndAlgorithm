package adtInterfaces;

import java.util.Iterator;

public interface StackInterface<T> {
    void push(T element);
    T pop();
    T peek();
    boolean isEmpty();
    int size();
    void clear();
    String toString();
    Iterator<T> iterator();
}
