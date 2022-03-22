package adtInterfaces;

public interface QueueInterface<T>
{
    int size();
    boolean isEmpty();
    boolean contains(T o);
    // public Iterator<E> iterator();
    T[] toArray();
    T remove();
    // public boolean containsAll(Collection<?> c);
    // public boolean addAll(Collection<? extends T> c);
    // public boolean removeAll(Collection<?> c);
    // public boolean retainAll(Collection<?> c);
    void clear();
    boolean add(T e);
    boolean offer(T e);
    // public T remove();
    T poll();
    T element();
    T peek();
    boolean isFull();
}

