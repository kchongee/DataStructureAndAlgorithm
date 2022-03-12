package adtInterfaces;

public interface DeQueueInterface<T> extends QueueInterfaceEe<T>{    
    void addFirst(T element);
    void addLast(T element);
    void removeFirst();
    void removeLast();
    T peekFirst();
    T peekLast();
    T pollFirst();
    T pollLast();
    // boolean offerFirst(T element);
    // boolean offerLast(T element);
}
