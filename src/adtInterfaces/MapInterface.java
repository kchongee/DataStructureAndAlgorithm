package adtInterfaces;


import java.util.Iterator;

public interface MapInterface<K,V> {
    V put(K k, V v);
    V get(K k);
    int size();
    boolean containsKey(K k);
    void clear();
    boolean containsValue(V Value);
    boolean isEmpty();
    Set keySet();
    Iterator<Entry<K,V>> iterator();
    void putAll(MapInterface<K,V> map);

    V remove(K key);
    V[] values();

    interface Entry<K, V>{
        K getKey();
        V getValue();
    }
}
