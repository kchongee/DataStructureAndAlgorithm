package adtImplementation;

import adtInterfaces.MapInterface;
import adtInterfaces.Set;

import java.util.Iterator;


public class LinkedHashSet<E> implements Set<E>
{
    private static final Object PRESENT = new Object();
    private LinkedHashMap<E, Object> mapAsSet;


    public LinkedHashSet() {
        this.mapAsSet = new LinkedHashMap<E,Object>();
    }


    public int size() {
        return mapAsSet.size();
    }


    public boolean isEmpty() {
        return mapAsSet.isEmpty();
    }

    public boolean contains(E e) {
        return mapAsSet.containsKey(e);
    }


    public E[] toArray()
    {
        E[] arr = (E[]) new Object[size()];
        int i = 0;
        for (MapInterface.Entry e : mapAsSet){
            arr[i] = (E) e;
        }
        return arr;
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
        Object o = mapAsSet.remove(e);
        if (o != null){
            return true;
        }
        return false;
    }


    public void clear() {
        mapAsSet.clear();
    }

    public String toString()
    {
        String set = "{";
        for (MapInterface.Entry element : mapAsSet)
        {
            set = set + element.getKey() + ", ";
        }
        set = set.substring(0, set.length()-2 > 3 ? set.length()-2: set.length());
        return set + "}";
    }


    public LinkedHashMap<E, Object> getMapAsSet() {
        return mapAsSet;
    }

    public void setMapAsSet(LinkedHashMap<E, Object> mapAsSet) {
        this.mapAsSet = mapAsSet;
    }


    public static void main(String[] args)
    {
        LinkedHashSet<Integer> test = new LinkedHashSet<Integer>();
        test.add(1);
        test.add(1);
        test.add(2);
        test.add(3);
        test.add(4);
        test.remove(4);
        test.remove(1);


        System.out.println(test.toString());
        System.out.println(test.size());

    }
}

