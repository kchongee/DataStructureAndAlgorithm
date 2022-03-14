package adtInterfaces;

public interface MapInterfaceEe<K,V> {
    boolean put(K k, V v);

    V get(K k);

    int size();

    interface Entry<K, V>{

        K getKey();

        V getValue();

    }
}
