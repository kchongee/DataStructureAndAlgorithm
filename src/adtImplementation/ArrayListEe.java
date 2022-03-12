package adtImplementation;

import adtInterfaces.ListInterfaceEe;

public class ArrayListEe<T> implements ListInterfaceEe<T>{

  private T[] array;
  private int numberOfEntries;
  private static final int DEFAULT_CAPACITY = 5;

  public ArrayListEe() {
    this(DEFAULT_CAPACITY);
  }

  public ArrayListEe(int initialCapacity) {
    numberOfEntries = 0;
    array = (T[]) new Object[initialCapacity];
  }

  @Override
  public boolean add(T newElement) {
    array[numberOfEntries] = newElement;
    numberOfEntries++;
    return true;
  }

  @Override
  public boolean add(int newPosition, T newElement) {
    boolean isSuccessful = true;

    if ((newPosition >= 1) && (newPosition <= numberOfEntries + 1)) {
      shiftOneBack(newPosition);
        array[newPosition - 1] = newElement;
        numberOfEntries++;
    } else {
      isSuccessful = false;
    }

    return isSuccessful;
  }
  
  public boolean addAll(T[] elements) {
    boolean isSuccessful = true;

    if(array.length < elements.length){
      expandArray();
    }
    System.arraycopy(elements, 0, array, 0, elements.length);
    numberOfEntries = elements.length;

    return isSuccessful;
  }

  @Override
  public boolean remove(int givenPosition) {
    boolean isRemoved = false;

    if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {      
      if (givenPosition < numberOfEntries) {
        removeGap(givenPosition);
      }
      numberOfEntries--;
      isRemoved = true;
    }

    return true;
  }

  @Override
  public boolean remove(T element) {
    for(int i=0;i<array.length;i++){
      if(element.equals(array[i])){
        remove(i);
        return true;
      }
    }
    return false;
  }  

  @Override
  public void clear() {
    numberOfEntries = 0;
  }

  @Override
  public boolean replace(int givenPosition, T newElement) {
    boolean isSuccessful = true;

    if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
      array[givenPosition - 1] = newElement;
    } else {
      isSuccessful = false;
    }

    return isSuccessful;
  }

  @Override
  public boolean replace(T oldElement, T newElement) {      
    for(int i=0;i<array.length;i++){
      if(oldElement.equals(array[i])){
        replace(i,newElement);
        return true;
      }
    }    
    return false;
  }

  @Override
  public T retrieve(int givenIndex) {
    T result = null;

    if ((givenIndex >= 0) && (givenIndex < numberOfEntries)) {
      result = array[givenIndex];
    }

    return result;
  }

  @Override
  public int retrieve(T anEntry) {    
    for (int index = 0; index < numberOfEntries; index++) {
      if (anEntry.equals(array[index])) {
        return index;
      }
    }
    return -1;
  }

  @Override
  public int size() {
    return numberOfEntries;
  }

  @Override
  public boolean isEmpty() {
    return numberOfEntries == 0;
  }

  public boolean isFull() {
    return numberOfEntries == array.length;
  }

  @Override
  public String toString() {
    String outputStr = "";
    for (int index = 0; index < numberOfEntries; ++index) {
      outputStr += array[index] + "\n";
    }

    return outputStr;
  }

  private void shiftOneBack(int newPosition) {
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

  private void expandArray(){
    T[] oldArray = array;
    array = (T[]) new Object[2*oldArray.length];

    for (int index = 0; index < oldArray.length; index++) {
      array[index] = oldArray[index];
    }
  }
}
