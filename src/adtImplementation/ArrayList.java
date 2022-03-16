package adtImplementation;

import java.util.Iterator;

import adtInterfaces.ListInterface;

public class ArrayList<T> implements ListInterface<T> {    
    private T[] arr;    
    private int size;
    private static final int DEFAULT_CAPACITY=50;

    public ArrayList(){
        this(DEFAULT_CAPACITY);
    }

    public ArrayList(int inputCapacity) {
        arr = (T[]) new Object[inputCapacity];
        size=0;
    }

    public ArrayList(T arr[]) {
        this.arr = arr;
        size= arr.length;
    }

    @Override
    public int size() {        
        return this.size;
    }

    @Override
    public boolean add(T element) {        
        if (isArrayFull()){
            expandArray();
        }
        arr[size]=element;
        size++;
        return true;
    }

    /*
    * Problem
    * Discuss : Nathan
    * Statement : default add method = overwrite
    * */

    public boolean add(int index, T element, boolean hold)
    {
        if (index >= size) {
            throw new IndexOutOfBoundsException("please insert with range");
        }
        else {
            if (isArrayFull()) {
                expandArray();
            }
            int lastEmptyIndex = size;
            for (int i = lastEmptyIndex ; i > index ; i--) {
                arr[i] = arr[i-1];
            }
            arr[index] = element;
            size++;
            return true;
        }
    }

    // @Override
    // public boolean add(int newIndex, T newElement) {
    //     boolean isSuccessful = false;
    @Override
    public boolean add(int newIndex, T newElement) {
        boolean isSuccessful = false;
    
        if ((newIndex >= 0) && (newIndex < size)) {
            addAGap(newIndex);
            arr[newIndex] = newElement;
            size++;
        } 
    
        return isSuccessful;
    }

    @Override
    public boolean remove(int index) {        
        if ((index >= 0) && (index < size)) {
            if (index < size) {
                removeGap(index);
            }
            size--;
        }else{
            return false;
        }
        return true;
    }

    public boolean remove(T element) {
        for(int i=0;i<arr.length;i++){
            if(element.equals(arr[i])){
                remove(i);
                size--;
                return true;
            }
        }
        return false;
    }  

    @Override
    public void clear(){
        this.size=0;
    }
    
    @Override
    public boolean contains(T element){
        boolean found=true;
        for(int i=0;i<this.size;i++){
            if (element.equals(arr[i])){
                found=true;
                break;
            } else{
                found=false;
            }
        }
        return found;
    }

    @Override
    public T get(int index) {        
        T returned = null;
        if((index>=0) && (index < size)){
            returned = arr[index];
        }
        return returned;
    }

    public int get(T anEntry) {    
        if(!isEmpty()){
            for (int index = 0; index < size; index++) {
                if (anEntry.equals(arr[index])) {
                  return index;
                }
            }
        }
        return -1;                
    }

    @Override
    public boolean replace(int index, T newElement){                
        if ((index >= 0) && (index < size) && arr[index]!=null){
            arr[index]=newElement;
            return true;
        }        
        return false;
    }

    public boolean addAll(T[] elements) {
        boolean isSuccessful = true;
    
        if(arr.length < elements.length){
          expandArray();
        }
        System.arraycopy(elements, 0, arr, 0, elements.length);
        size = elements.length;
    
        return isSuccessful;
    }

    @Override
    public boolean isEmpty() {        
        return size==0;
    }

    // @Override
    // public <T> T[] toArray(T[] a) {
    //     Object[] newArr = new Object[size()];
    //     System.out.println(newArr.length);
    //     System.arraycopy(arr, 0, newArr, 0, size());
    //     return (T[])newArr;
    // }    

    public Object[] toArray(){
        Object[] newArr = new Object[size()];        
        System.arraycopy(arr, 0, newArr, 0, size());        
        return newArr;
    }
    
    private void expandArray() {
        T[] oldArray=arr;
        int oldsize=oldArray.length;
        arr = (T[]) new Object[2*oldsize];

        System.arraycopy(oldArray, 0, arr, 0, oldsize);
    }

    private boolean isArrayFull() {
        return this.size==arr.length;
    }

    private void removeGap(int index) {
        // move each entry to next lower position starting at entry after the
        // one removed and continuing until end of array
        //int removedIndex = index - 1;
        //int lastIndex = size - 1;        
        for (int i = index; i<this.size; i++) {
          arr[i] = arr[i+1];
        }
    }    

    private void addAGap(int index) {
        int newIndex = index;
        int lastIndex = size-1;

        if(isArrayFull()){
            expandArray();
        }
    
        for (int i = lastIndex; i >= newIndex; i--) {
            arr[index + 1] = arr[index];
        }
    }


    public String toString()
    {
        String str = "";
        for (T element : arr)
        {
            str = str + (String) element + " ";
        }
        return str;
    }

    // @Override
    // public String toString(){
    //     int index = 0;
    //     String str = "";
    //     while(index<size()){
    //         str += String.format("%s\n",arr[index]);
    //         System.out.println();
    //         index++;
    //     }
    //     return str;
    // }

    public Iterator<T> iterator(){
        return new ListIterator();
    }
    private class ListIterator implements Iterator<T>{   
        private int currIndex = 0;     

        @Override
        public boolean hasNext() {
            return currIndex < size;
        }

        @Override
        public T next() {
        if(hasNext()){
            T data = arr[currIndex];
            currIndex++;
            return data;
        }      
        return null;
        }    
    }    
    public static void main(String[] args)
    {
        ArrayList<String> a = new ArrayList<String>();
        a.add("1");
        System.out.println(a.toString());
        a.add("2");
        System.out.println(a.toString());
        a.add(1, "4", true);
        System.out.println(a.toString());
    }
}
