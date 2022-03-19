package adtInterfaces;

import java.util.Collection;
import java.util.Iterator;

public interface Set<E>
{
    public int size();
    public boolean isEmpty();
    public boolean contains(E o);
    public Iterator<E> iterator();
    public E[] toArray(E[] a);
    public boolean add(String s);
    public boolean remove(E o);
//    public boolean containsAll(Collection<?> c);
//    public boolean addAll(Collection<? extends String> c);
//    public boolean retainAll(Collection<?> c);
//    public boolean removeAll(Collection<?> c);
    public void clear();
    public boolean equals(Object o);
    public int hashCode();
}
