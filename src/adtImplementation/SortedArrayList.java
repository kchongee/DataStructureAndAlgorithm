package adtImplementation;

import java.util.Iterator;

import adtInterfaces.SortedListInterface;

public class SortedArrayList<T extends Comparable<T>> implements SortedListInterface<T> {

  private final T[] array;
  private int numberOfEntries;
  private static final int DEFAULT_CAPACITY = 25;
  private int arrayQty;

  public SortedArrayList() {
    this(DEFAULT_CAPACITY);
  }

  public SortedArrayList(int initialCapacity) {
    numberOfEntries = 0;
    array = (T[]) new Comparable[initialCapacity];
  }

  public SortedArrayList(T[] array) {
    this.array = array;
    numberOfEntries = array.length;
  }

  public int size() {
    return arrayQty;
  }

  public boolean add(T newEntry) {
    int i = 0;
    while (i < numberOfEntries && newEntry.compareTo(array[i]) > 0) {
      i++;
    }
    makeRoom(i + 1);
    array[i] = newEntry;
    numberOfEntries++;
    return true;
  }

  public boolean remove(T anEntry) {
    if (numberOfEntries == 0) {
      return false;
    } else {
      int index = 0;
      while (index < numberOfEntries && array[index].compareTo(anEntry) < 0) {
        index++;
      }
      if (array[index].equals(anEntry)) {
        removeGap(index + 1);
        numberOfEntries--;
        return true;
      }
    }
    return false;
  }

  public void clear() {
    numberOfEntries = 0;
  }

  public boolean contains(T anEntry) {
    boolean found = false;
    for (int index = 0; !found && (index < numberOfEntries); index++) {
      if (anEntry.equals(array[index])) {
        found = true;
      }
    }
    return found;
  }

  public int getNumberOfEntries() {
    return numberOfEntries;
  }

  public T get(int index) {
    T returned = null;
    if ((index >= 1) && (index <= size())) {
      returned = array[index];
    }
    return returned;
  }

  public boolean isEmpty() {
    return numberOfEntries == 0;
  }

  public String toString() {
    String outputStr = "";
    for (int index = 0; index < numberOfEntries; ++index) {
      outputStr += array[index] + "\n";
    }

    return outputStr;
  }

  private void makeRoom(int newPosition) {
    int newIndex = newPosition - 1;
    int lastIndex = numberOfEntries - 1;

    for (int index = lastIndex; index >= newIndex; index--) {
      array[index + 1] = array[index];
    }
  }

  private void removeGap(int givenPosition) {
    int removedIndex = givenPosition - 1;
    int lastIndex = numberOfEntries - 1;

    for (int index = removedIndex; index < lastIndex; index++) {
      array[index] = array[index + 1];
    }
  }

  @Override
  public Iterator<T> iterator(){
    return new ListIterator();
}
private class ListIterator implements Iterator<T>{   
    private int currIndex = 0;     

    @Override
    public boolean hasNext() {
        return currIndex < numberOfEntries;
    }

    @Override
    public T next() {
    if(hasNext()){
        T data = array[currIndex];
        currIndex++;
        return data;
    }      
    return null;
    }    
}    
}
