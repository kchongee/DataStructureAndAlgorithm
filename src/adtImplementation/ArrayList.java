package adtImplementation;

import adtInterfaces.ListInterface;

public class ArrayList<T> implements ListInterface<T> {    
    private T[] listArray;
    //private Account[] accountList;
    private int numberOfEntries;
    private static final int DEFAULT_CAPACITY=50;

    public ArrayList(){
        this(DEFAULT_CAPACITY);
    }

    public ArrayList(int inputCapacity) {
        listArray = (T[]) new Object[inputCapacity];
        numberOfEntries=0;
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return this.numberOfEntries;
    }

    @Override
    public boolean add(T inputElement) {
        // TODO Auto-generated method stub
        if (isArrayFull()){
            doubleArray();
        }
        listArray[numberOfEntries]=inputElement;
        numberOfEntries++;
        return true;
    }

    @Override
    public boolean remove(int givenPosition) {
        // TODO Auto-generated method stub

        if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
            listArray[givenPosition - 1]=null;

            if (givenPosition < numberOfEntries) {
                removeGap(givenPosition);
            }
            numberOfEntries--;
        }else{
            return false;
        }
        return true;
    }

    @Override
    public void clear(){
        this.numberOfEntries=0;
    }
    
    @Override
    public boolean contains(T inputElement){
        boolean found=true;
        for(int i=0;i<this.numberOfEntries;i++){
            if (inputElement.equals(listArray[i])){
                found=true;
                break;
            } else{
                found=false;
            }
        }
        return found;
    }

    @Override
    public T get(int givenPosition) {
        // TODO Auto-generated method stub
        T returned = null;
        if((givenPosition>=1) && (givenPosition <= numberOfEntries)){
            returned = listArray[givenPosition];
        }
        return returned;
    }

    @Override
    public boolean replace(int givenPosition, T inputElement){
        if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)){
            listArray[givenPosition]=inputElement;
        } else{
            return false;
        }
        return true;
    }
    
    private void doubleArray() {
        T[] oldArray=listArray;
        int oldsize=oldArray.length;
        listArray = (T[]) new Object[2*oldsize];

        System.arraycopy(oldArray, 0, listArray, 0, oldsize);
    }

    private boolean isArrayFull() {
        return this.numberOfEntries==listArray.length;
    }

    private void removeGap(int givenPosition) {
        // move each entry to next lower position starting at entry after the
        // one removed and continuing until end of array
        //int removedIndex = givenPosition - 1;
        //int lastIndex = numberOfEntries - 1;
        for (int i = givenPosition; givenPosition<this.numberOfEntries; givenPosition++) {
          listArray[i] = listArray[i+1];
        }
    }

}
