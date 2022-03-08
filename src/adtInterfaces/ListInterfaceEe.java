package adtInterfaces;

public interface ListInterfaceEe<T> {
    boolean add(T newElement);
    boolean add(int index,T newElement);
    boolean remove(T elment);
    boolean remove(int index);
    T retrieve(int index);
    int retrieve(T elment);
    boolean replace(T oldElement,T newElement);
    boolean replace(int index,T newElement);
    void clear();
    boolean isEmpty();
    int size();
    boolean addAll(T[] elements);
}
