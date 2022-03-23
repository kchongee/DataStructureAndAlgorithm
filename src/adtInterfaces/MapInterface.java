package adtInterfaces;


import java.util.Iterator;

public interface MapInterface<K,V> extends Iterable<MapInterface.Entry<K, V>> {
    V put(K key, V value);
    V get(K key);
    V remove(K key);
    int putAll(MapInterface<K,V> map);
    int size();
    boolean containsKey(K key);
    boolean containsValue(V value);
    boolean isEmpty();
    Set<K> keySet();
    V[] values();
    Iterator<Entry<K,V>> iterator();
    void clear();


    interface Entry<K, V>{
        K getKey();
        V getValue();
    }
}
