package adtInterfaces;

public interface ListInterface<T> {
    public boolean add (T element);
    public boolean remove (int index);
    public boolean contains(T element);     
    public T get(int index);
    public boolean replace(int index, T element);
    boolean isEmpty();
    public int size();
    public void clear();
}
