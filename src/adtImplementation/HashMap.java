package adtImplementation;

import adtInterfaces.ListInterface;
import adtInterfaces.MapInterface;

public class HashMap<K,V> implements MapInterface<K,V> {

    //Default length of the array, initial value is 16
    private static int defaultLength = 16;
    //Loading factor, 0.75 by default
    private static double defaultLoader = 0.75;
    //Entry array
    private Entry<K, V>[] table = null;
    //The size of this hashmap
    private int size = 0;

    public HashMap() {
        this(defaultLength, defaultLoader);
    }

    public HashMap(int length, double loader) {
        defaultLength = length;
        defaultLoader = loader;
        //initialize entry array
        table = new Entry[defaultLength];
    }

    @Override
    public V get(K k) {
        //get the index that the entry is stored
        int index = getKey(k);
        //non-empty check
        if (table[index] == null) {
            return null;
        }
        //call function to find the real value and then return
        return findValueByEqualKey(k,table[index]);
    }

    @Override
    public V put(K k, V v) {
        //calculate the index of a given key
        int index = getKey(k);
        Entry<K, V> entry = table[index];
        //determine if entry is null
        if (entry == null) {
            /*
            * if entry is null, it means there is no data in this slot
            * create an entry object
            * next pointer has no value at this moment because there is only
            * one entry object in this slot
            * */
            table[index] = new Entry(k, v, null);
            //size of map + 1
            size++;
        } else {
            /*
            * if entry is not null, it means there is at least one entry in
            * this slot
            * create an entry object
            * set the next pointer to be previous entry and replace the data
            * in the array
            * */
            table[index] = new Entry<K, V>(k, v, entry);
        }
        return table[index].getValue();
    }

    @Override
    public int size() {
        return size;
    }
    
    private class Entry<K, V> implements  MapInterface.Entry<K, V> {
        K k;
        V v;
        
        Entry<K, V> next;
        
        public Entry(K k, V v, Entry next) {
            this.k = k;
            this.v = v;
            this.next = next;
        }

        @Override
        public K getKey() {
            return k;
        }

        @Override
        public V getValue() {
            return v;
        }
    }

    private int getKey(K k) {
        int m = defaultLength;
        int index = k.hashCode() % m;
        return index >= 0 ? index : -index;
    }

        //resize of the array
    private void expand() {
        //create an entry array that its length is two times as previous
        Entry<K, V>[] newTable = new Entry[2 * defaultLength];
        //call rehash function
        rehash(newTable);
    }

    private void findEntryByNext(Entry<K, V> entry,ListInterface<Entry<K, V>> list ) {
        if (entry != null && entry.next != null) {
            list.add(entry);
            //call recursive function
            findEntryByNext(entry.next,list);
        }else {
            list.add(entry);
        }
    }

    //the process of rehash
    private void rehash(Entry<K,V>[] newTable) {
        //create a list to store all the entry objects in the hashmap
        ListInterface<Entry<K, V>> list = new ArrayList<Entry<K, V>>();

        //traverse the array
        for(int i=0; i<table.length;i++) {
            //continue if the given slot has no data
            if (table[i] == null) {
                continue;
            }
            //store all entries into the list using recursive method
            findEntryByNext(table[i],list);
            if (list.size() > 0) {
                //reset size
                size = 0;
                //double the default size
                defaultLength = 2 * defaultLength;

                table = newTable;
                
                for(int j=0;j<list.size();i++) {
                    if (list.retrieve(j) != null) {
                        //set next pointer of all entries to null
                        list.retrieve(j).next = null;
                    }
                    //rehash new table
                    put(list.retrieve(j).getKey(), list.retrieve(j).getValue());
                }
            }
        }
    }


    private V findValueByEqualKey(K k , Entry<K,V> entry) {

        /*
        * if key of this parameter equals to the key of this entry, that means
        * this is the target entry
        * */
        if (k == entry.getKey() || k.equals(entry.getKey())) {
            return entry.getValue();
        } else {
            /*
            * if they are not equal, use recursive method to compare the key of its next pointer to find the real value
            * */
            if (entry.next != null) {
                return findValueByEqualKey(k, entry.next);
            }
        }
        return entry.getValue();
    }
    
}


