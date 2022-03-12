package adtInterfaces;

public interface QueueInterfaceEe<T>
{
    boolean contains(T element);
    int size();
    boolean isEmpty();
    void clear();
    void addAll(T[] elements);
    T[] toArray();
};

