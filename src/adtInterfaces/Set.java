package adtInterfaces;

import java.util.Collection;
import java.util.Iterator;

public interface Set<E>
{
    int size();
    boolean isEmpty();
    boolean contains(E o);
    // public Iterator<E> iterator();
    E[] toArray();
    boolean add(E e);
    boolean remove(E o);
//    public boolean containsAll(Collection<?> c);
//    public boolean addAll(Collection<? extends String> c);
//    public boolean retainAll(Collection<?> c);
//    public boolean removeAll(Collection<?> c);
void clear();
    boolean equals(Object o);
    int hashCode();
}
