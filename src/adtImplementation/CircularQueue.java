package adtImplementation;

import adtInterfaces.QueueInterface;

public class CircularQueue<T> implements QueueInterface<T> {
    T elements[];
    int starting;
    int ending;
    int size;
    int elementQty;

    public CircularQueue(int size) 
    {
        this.starting = 0;
        this.ending = 0;
        this.elementQty = 0;
        this.elements = (T[]) new Object[size];
        this.size = size;
    }

    @Override
    public int size() {
        return elementQty;
    }

    @Override
    public boolean isEmpty() {
        return elementQty == 0;
    }

    @Override
    public boolean contains(T e) 
    {
        for(T element : elements)
        {
            if(element == e){
                return true;
            }
        }
        return false;
    }

    @Override
    public T[] toArray() {
        int queueSize = size();
        T[] arr = (T[]) new Object[queueSize];
        return arr;
    }

    @Override
    public boolean remove(final Object o) {
        return false;
    }

    // @Override
    // public boolean remove(Object o) {
    //     // TODO Auto-generated method stub
    //     return false;
    // }

    @Override
    public void clear() 
    {
        this.elements = (T[]) new Object[size];
    }

    @Override
    public boolean add(T e)
    {
        if (!isFull())
        {
            ++elementQty;
            System.out.println("next insert= " + getNextInsertionIndex());
            elements[getNextInsertionIndex()] = e;
            ++ending;
            return true;
        }
        return false;
    }

    private int getNextInsertionIndex()
    {
        System.out.println("ENDING = " + ending);
        int nextInsertion = 0;
        if (ending == 0){
            return 0;
        } else if (ending == size){
            nextInsertion = 0;
        } else {
            nextInsertion = ending;
        }
        System.out.print("NEXT INSERTION = " + nextInsertion);
        return nextInsertion;
    }

    @Override
    public boolean isFull(){
        return elementQty >= size;
    }

    @Override
    public boolean offer(final T e) {
        return false;
    }

    @Override
    public T poll()
    {
        T returnElement = null;
        if (elementQty > 0)
        {
            --elementQty;
            returnElement = elements[starting];
            elements[starting] = null;
            starting = getNewStart(starting);
        }
        return returnElement;
    }

    private int getNewStart(int oldStartingPoint)
    {
        int newStart = oldStartingPoint + 1;
        if (newStart >= size){
            newStart = 0;
        }
        return newStart;
    }

    @Override
    public T element() {
        return null;
    }

    @Override
    public T peek() {
        return null;
    }

    public void printLogicalQueue(){
        int start = starting;
        boolean endNotReached = true;
        System.out.print("Logical : ");
        while (endNotReached)
        {
            T element = elements[start];
            System.out.print(element + ", ");
            start = getNewStart(start);
            endNotReached = start != ending;
        }
    }

    public void printActualQueue(){
        System.out.print("Actual : ");
        for (T e : elements){
            System.out.print(e + ", ");
        }
    }
}

class TestMyCircularQueue{
    public static void main(String[] args) {
        CircularQueue cq = new CircularQueue<Integer>(5);
        cq.printActualQueue();

        cq.add(1);
        cq.add(2);

        cq.printActualQueue();


        cq.add(3);
        cq.add(4);

        cq.printActualQueue();

        cq.poll();

        cq.printActualQueue();


        cq.add(6);
        cq.printActualQueue();


        cq.add(9);
        cq.printActualQueue();

        cq.poll();
        cq.printActualQueue();

    }
}