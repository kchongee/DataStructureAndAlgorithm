package adtImplementation;

import adtInterfaces.Set;

import java.util.Collection;
import java.util.Iterator;

public class LinkedHashSet<T> implements Set<T>
{
    private static final Object PRESENT = new Object();
    private HashMap mapAsSet;

    public HashSet() {
        this.mapAsSet = new HashMap<T,Object>();
    }

    public int size() {
        return 0;
    }

    public boolean isEmpty() {
        return false;
    }

    public boolean contains(Object o) {
        return false;
    }

    public Iterator<String> iterator() {
        return null;
    }

    public Object[] toArray() {
        return new Object[0];
    }

    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
    public boolean add(String s) {
        return false;
    }

    public boolean add(T e) {

        mapAsSet.put(e,PRESENT);
    }

    public boolean remove(Object o) {
        return false;
    }

    public boolean containsAll(Collection<?> c) {
        return false;
    }

    public boolean addAll(Collection<? extends String> c) {
        return false;
    }

    public boolean retainAll(Collection<?> c) {
        return false;
    }

    public boolean removeAll(Collection<?> c) {
        return false;
    }

    public void clear() {

    }
}
