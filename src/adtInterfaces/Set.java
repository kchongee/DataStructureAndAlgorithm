package adtInterfaces;

import java.util.Collection;
import java.util.Iterator;

public interface Set<T>
{
    public int size();
    public boolean isEmpty();
    public boolean contains(Object o);
    public Iterator<String> iterator();
    public Object[] toArray();
    public <T> T[] toArray(T[] a);
    public boolean add(String s);
    public boolean remove(Object o);
    public boolean containsAll(Collection<?> c);
    public boolean addAll(Collection<? extends String> c);
    public boolean retainAll(Collection<?> c);
    public boolean removeAll(Collection<?> c);
    public void clear();
    public boolean equals(Object o);
    public int hashCode();
}
