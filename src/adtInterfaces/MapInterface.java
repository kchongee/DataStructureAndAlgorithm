package adtInterfaces;


import java.util.Iterator;

public interface MapInterface<K,V> extends Iterable<MapInterface.Entry<K, V>> {
    V put(K k, V v);
    V get(K k);
    void putAll(MapInterface<K,V> map);
    int size();
    boolean containsKey(K k);
    void clear();
    boolean containsValue(V Value);
    boolean isEmpty();
    Set<K> keySet();
    Iterator<Entry<K,V>> iterator();
    V remove(K key);
    V[] values();

    interface Entry<K, V>{
        K getKey();
        V getValue();
    }
}
