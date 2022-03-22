package adtImplementation;

import adtInterfaces.QueueInterface;

import java.lang.reflect.Array;
import java.util.NoSuchElementException;
import java.util.Queue;


public class CircularQueue<T> implements QueueInterface<T> {
    private final static int DEFAULT_SIZE = 5;
    private T[] elements;
    private int starting;
    private int nextInsertion;
    private final int size;
    private int elementQty;

    public CircularQueue(){
        this.starting = 0;
        this.nextInsertion = 0;
        this.elementQty = 0;
        this.elements = (T[]) new Object[DEFAULT_SIZE];
        this.size = DEFAULT_SIZE;
    }


    public CircularQueue(int size)
    {
        this.starting = 0;
        this.nextInsertion = 0;
        this.elementQty = 0;
        this.elements = (T[]) new Object[size];
        this.size = size;
    }


    public int size() {
        return elementQty;
    }


    public boolean isEmpty() {
        return elementQty == 0;
    }


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


    public T[] toArray()
    {
        T[] result = (T[])new Object[size()];

        for (int i = 0 ; i < size() ; i++)
        {
            result[i] = this.poll();
            this.add(result[i]);
        }
        return result;
    }


    public T remove()
    {
        if (!isEmpty())
        {
            T returnElement = elements[starting];
            elements[starting] = null;
            starting = getNewStart(starting);
            --elementQty;
            return returnElement;
        }
        else
        {
            throw new NoSuchElementException("Queue is empty");
        }
    }


    public void clear()
    {
        this.starting = 0;
        this.nextInsertion = 0;
        this.elementQty = 0;
        this.elements = (T[]) new Object[size];
    }


    public boolean add(T e)
    {
        if (!isFull())
        {
            elements[getNextInsertionIndex()] = e;
            updateNextInsertionIndex();
            ++elementQty;
            return true;
        }
        else
        {
            throw new IllegalStateException("Exceeded queue limitations");
        }
    }


    public boolean isFull(){
        return elementQty >= size;
    }


    public boolean offer(final T e)
    {
        if (!isFull())
        {
            elements[getNextInsertionIndex()] = e;
            updateNextInsertionIndex();
            ++elementQty;
            return true;
        }
        return false;
    }


    public T poll()
    {
        T returnElement = null;
        if (!isEmpty())
        {
            returnElement = elements[starting];
            elements[starting] = null;
            starting = getNewStart(starting);
            --elementQty;
        }
        return returnElement;
    }


    public T element()
    {
        if (!isEmpty()){
            return elements[starting];
        }else {
            throw new NoSuchElementException("Queue is empty");
        }
    }


    public T peek()
    {
        T headElement = null;
        if (!isEmpty()){
            headElement = elements[starting];
        }
        return headElement;
    }


    // utility
    private int getNewStart(int oldStartingPoint)
    {
        int newStart = oldStartingPoint + 1;
        if (newStart >= size){
            newStart = 0;
        }
        return newStart;
    }


    private int getNextInsertionIndex() {
        return nextInsertion == size? 0:nextInsertion;
    }


    private void updateNextInsertionIndex(){
        nextInsertion = nextInsertion+1 >= size? 0 : nextInsertion+1;
    }

    // Debug
    public void printLogicalQueue()
    {
        int start = starting;
        boolean endNotReached = true;
        System.out.println("Logical : ");
        while (endNotReached)
        {
            T element = elements[start];
            System.out.print(element + ", ");
            start = getNewStart(start);
            endNotReached = start != nextInsertion;
        }
    }


    public void printActualQueue(){
        System.out.print("Actual : ");
        for (T e : elements){
            System.out.print(e + ", ");
        }
    }


    public void printSize(){
        System.out.println("Size = " + size());
    }


    public void printEndingAndStarting(){
        System.out.println("Ending = " + nextInsertion);
        System.out.println("Starting = " + starting);
    }
}

class TestMyCircularQueue{
    public static void main(String[] args) {
        CircularQueue cq = new CircularQueue<Integer>(5);
        cq.printActualQueue();
        cq.printLogicalQueue();
        cq.printSize();
        cq.printEndingAndStarting();

        cq.add(1);
        cq.printActualQueue();
        cq.printLogicalQueue();
        cq.printSize();
        cq.printEndingAndStarting();

        cq.add(2);
        cq.printActualQueue();
        cq.printLogicalQueue();
        cq.printSize();
        cq.printEndingAndStarting();

        cq.add(2);
        cq.printActualQueue();
        cq.printLogicalQueue();
        cq.printSize();
        cq.printEndingAndStarting();

        cq.add(3);
        cq.printActualQueue();
        cq.printLogicalQueue();
        cq.printSize();
        cq.printEndingAndStarting();

        cq.poll();
        cq.printActualQueue();
        cq.printLogicalQueue();
        cq.printSize();
        cq.printEndingAndStarting();

        cq.poll();
        cq.printActualQueue();
        cq.printLogicalQueue();
        cq.printSize();
        cq.printEndingAndStarting();

        cq.add(3);
        cq.printActualQueue();
        cq.printLogicalQueue();
        cq.printSize();
        cq.printEndingAndStarting();

        cq.add(4);
        cq.printActualQueue();
        cq.printLogicalQueue();
        cq.printSize();
        cq.printEndingAndStarting();

        cq.remove();

        cq.add(5);
        cq.printActualQueue();
        cq.printLogicalQueue();
        cq.printSize();
        cq.printEndingAndStarting();

        cq.add(7);
        cq.printActualQueue();
        cq.printLogicalQueue();
        cq.printSize();
        cq.printEndingAndStarting();

        Object[] arr = cq.toArray();
        System.out.println(arr.length);
        System.out.print("ARR= [] ");
        for (int i = 0 ; i < cq.toArray().length ; i++){
            System.out.print(arr[i] + ", ");
        }

        cq.printActualQueue();
        cq.printLogicalQueue();
        cq.printSize();
        cq.printEndingAndStarting();

        cq.poll();
        cq.printActualQueue();
        cq.printLogicalQueue();
        cq.printSize();
        cq.printEndingAndStarting();

        cq.poll();
        cq.printActualQueue();
        cq.printLogicalQueue();
        cq.printSize();
        cq.printEndingAndStarting();

        cq.poll();
        cq.printActualQueue();
        cq.printLogicalQueue();
        cq.printSize();
        cq.printEndingAndStarting();

        cq.poll();
        cq.printActualQueue();
        cq.printLogicalQueue();
        cq.printSize();
        cq.printEndingAndStarting();

        cq.poll();
        cq.printActualQueue();
        cq.printLogicalQueue();
        cq.printSize();
        cq.printEndingAndStarting();

        cq.poll();
        cq.printActualQueue();
        cq.printLogicalQueue();
        cq.printSize();
        cq.printEndingAndStarting();

        cq.poll();
        cq.printActualQueue();
        cq.printLogicalQueue();
        cq.printSize();
        cq.printEndingAndStarting();

        cq.poll();
        cq.printActualQueue();
        cq.printLogicalQueue();
        cq.printSize();
        cq.printEndingAndStarting();

        cq.poll();
        cq.printActualQueue();
        cq.printLogicalQueue();
        cq.printSize();
        cq.printEndingAndStarting();

    }
}