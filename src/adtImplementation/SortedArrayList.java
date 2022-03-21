package adtImplementation;

import adtInterfaces.SortedListInterface;

public class SortedArrayList<T extends Comparable<T>> implements SortedListInterface<T> {

  private T[] array;
  private int numberOfEntries;
  private static final int DEFAULT_CAPACITY = 25;
  private int arrayQty;

  public SortedArrayList() {
    this(DEFAULT_CAPACITY);
  }

  public int size() {
    return arrayQty;
  }

  public SortedArrayList(int initialCapacity) {
    numberOfEntries = 0;
    array = (T[]) new Comparable[initialCapacity];
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
}
