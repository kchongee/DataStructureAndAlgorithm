package adtInterfaces;

public interface ListInterface<T> {
    public boolean add (T inputElement);
    public boolean remove (int givenPosition);
    public boolean contains(T inputElement); 
    public T retrieve(int givenPosition);
    public int size();
    public void clear();
}
