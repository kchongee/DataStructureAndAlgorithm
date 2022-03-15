package adtInterfaces;

public interface HashMapInterfaceEe {    
    class Entry <K, V> { //K and V are generics where they are the type the the user inputs when instaniating their HashMap
        //Instance variables
        public K key;
        public V value;
        //Constructors
        public Entry(K k,V v){
            key = k;
            value = v;
        }
        //toString for printing and debugging
        public String toString(){
            return key + " = " + value;
        }
    }   
}
