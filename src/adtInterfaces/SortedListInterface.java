package adtInterfaces;

public interface SortedListInterface<T extends Comparable<T>> {

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
  
  } class SortedListInterfaces {
    
}
