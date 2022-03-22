package adtInterfaces;

public interface SortedListInterface<T extends Comparable<T>> {

<<<<<<< HEAD
    /**
     * Task: Adds a new entry to the sorted list in its proper order.
     *
     * @param newEntry the object to be added as a new entry
     * @return true if the addition is successful
     */
    int size();
    boolean add(T newEntry);
  
    /**
     * Task: Removes a specified entry from the sorted list.
     *
     * @param anEntry the object to be removed
     * @return true if anEntry was located and removed
     */
    boolean remove(T anEntry);
  
    boolean contains(T anEntry);
  
    void clear();

    T get(int index);
  
    int getNumberOfEntries();
  
    boolean isEmpty();
=======
    public int size();
    public boolean add(T newEntry);
    public boolean remove(T anEntry);
    public boolean contains(T anEntry);
    public void clear();
    public T get(int index);
    public int getNumberOfEntries();
    public boolean isEmpty();
>>>>>>> 0be726aea5616a112233dd516c8be364649f0eb0
  
} 
    

