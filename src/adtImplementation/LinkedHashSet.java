package adtImplementation;

import UtilityClasses.CMD;
import adtInterfaces.MapInterface;
import adtInterfaces.Set;
import entity.RoomList;

import java.util.HashSet;
import java.util.Iterator;


public class LinkedHashSet<E> implements Set<E>, Iterable<E>
{
    private static final Object PRESENT = new Object();
    private LinkedHashMap<E, Object> mapAsSet;


    public void HashSet() {
        this.mapAsSet = new LinkedHashMap<E,Object>();
    }


    public int size() {
        return mapAsSet.size();
    }


    public boolean isEmpty() {
        return mapAsSet.isEmpty();
    }


    public boolean contains(Object o) {
        return false;
    }


    public Iterator<E> iterator() {
        return null;
    }


    public E[] toArray(E[] a)
    {
        E[] arr = (E[]) new Object[size()];
        int i = 0;
        for (E element : this) { arr[i++] = element; }
        return arr;
    }


    public boolean add(String s) {
        return false;
    }


    public boolean add(E e)
    {
        boolean exist = mapAsSet.containsKey(e);
        if (!exist){
            mapAsSet.put(e,PRESENT);
        }
        return !exist;
    }


    public boolean remove(E e) {
        return (boolean) mapAsSet.remove(e);
    }


    public void clear() {
        mapAsSet.clear();
    }

    public String toString()
    {
        String set = "{";
        for (E element : this){
            set = set + element + ", ";
        }
        set = set.substring(0, -2);
        return set + "}";
    }


    public static class LinkedHashSetIterator<E> implements Iterator<E>
    {
        E current;
        Iterator mapIterator;

        public LinkedHashSetIterator(LinkedHashSet<E> linkedHashSet)
        {
            this.mapIterator = linkedHashSet.mapAsSet.iterator();
            this.current = (E) ((MapInterface.Entry)mapIterator.next()).getKey();
        }

        public boolean hasNext() {
            return mapIterator.hasNext();
        }

        public E next() {
            return (E) ((MapInterface.Entry)(mapIterator.next())).getKey();
        }
    }

    public static void main(String[] args) {
        HashSet<String>
    }
}
