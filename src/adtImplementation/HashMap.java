package adtImplementation;

import adtInterfaces.ListInterface;
import adtInterfaces.MapInterface;
import adtInterfaces.Set;

import java.util.Iterator;

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
        
        if (size==defaultLength){
            expand();
        }
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
    public V remove(K key) {

        int i=indexOf(key);

        Entry<K,V> prev = table[i];
        Entry<K,V> e = prev;

        while(e!=null){
            if (e.getKey() != null && e.getKey().equals(key)){
                size--;
                if(prev==e){
                    table[i]=e.next;
                } else{
                    prev.next=e.next;
                }
            } else{
                prev = e;
                e = e.next;
            }
            return e.getValue();
        }
        return e.getValue();
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        this.size=0;
        defaultLength = 16;
    }

    @Override
    public boolean isEmpty() {
        return this.size==0;
    }

    @Override
    public boolean containsKey(K k) {
        throw new UnsupportedOperationException();
    }


    @Override
    public boolean containsValue(V Value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator iterator(){
        throw new UnsupportedOperationException();      
    }

    @Override
    public int putAll(MapInterface<K,V> map) {
        return 0;
    }


    @Override
    public V[] values() {
        return null;
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
                    if (list.get(j) != null) {
                        //set next pointer of all entries to null
                        list.get(j).next = null;
                    }
                    //rehash new table
                    put(list.get(j).getKey(), list.get(j).getValue());
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

    private int indexOf(K object) {
        return object == null ? 0 : hash(object) & (defaultLength - 1);
    }

    /*private boolean matches(Object o1, Object o2) {
        return Objects.equals(o1, o2);
    }*/

    private static int hash(Object key) {
        // This function ensures that hashCodes that differ only by
        // constant multiples at each bit position have a bounded
        // number of collisions (approximately 8 at default load factor).
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    private class Entry<K, V> implements  MapInterface.Entry<K, V> {
        private K k;
        private V v;
        
        private Entry<K, V> next;
        
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


}


