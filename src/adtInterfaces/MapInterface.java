package adtInterfaces;

import adtImplementation.LinkedHashMap;

import java.util.Iterator;

public interface MapInterface<K,V> {
    V put(K k, V v);
    /**
     * get function
     * @param k
     * @return
     */
    V get(K k);

    int size();
    /**
     * Entry interface
     * @param <K>
     * @param <V>
     */

    public boolean containsKey(K k);
    public void clear();
    public boolean containsValue(V Value);
    public boolean isEmpty();
    public Set keySet();

    void putAll(LinkedHashMap map);

    public V remove(K key);
    public V[] values();


    interface Entry<K, V>{
        /**
         * get the key in an entry object
         * @return
         */
        K getKey();
        /**
         * get the value in an entry object
         * @return
         */
        V getValue();

    }
}
