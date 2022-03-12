package adtInterfaces;

public interface QueueInterface<T>
{
    public int size();
    public boolean isEmpty();
    public boolean contains(T o);
    // public Iterator<E> iterator();
    public T[] toArray();
    public T remove(Object o);
    // public boolean containsAll(Collection<?> c);
    // public boolean addAll(Collection<? extends T> c);
    // public boolean removeAll(Collection<?> c);
    // public boolean retainAll(Collection<?> c);
    public void clear();
    public boolean add(T e);
    public boolean offer(T e);
    // public T remove();
    public T poll();
    public T element();
    public T peek();
    public boolean isFull();
};

