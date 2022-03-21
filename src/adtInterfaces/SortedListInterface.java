package adtInterfaces;

public interface SortedListInterface<T extends Comparable<T>> {

    public int size();
    public boolean add(T newEntry);
    public boolean remove(T anEntry);
    public boolean contains(T anEntry);
    public void clear();
    public T get(int index);
    public int getNumberOfEntries();
    public boolean isEmpty();
  
} 
    

