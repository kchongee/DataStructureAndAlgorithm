package adtInterfaces;

public interface ListInterface<T> {
    public boolean add (T inputElement);
    public boolean delete (int givenPosition);
    public boolean contains(T inputElement); 
    public T getElementValue(int givenPosition);
    public int size();
    public void clear();
}
