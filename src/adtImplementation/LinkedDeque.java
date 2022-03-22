package adtImplementation;

import java.util.Iterator;

import adtInterfaces.DequeInterface;

public class LinkedDeque<T> implements DequeInterface<T>{
    // public int firstIndex,lastIndex;   
    private Node firstNode;
    private Node lastNode;
    private int nodeCount;

    public LinkedDeque(){
        clear();
    }

    public LinkedDeque(T... elements){
        this();
        addAll(elements);
    }        

    @Override
    public int size() {        
        return nodeCount;
    }

    @Override
    public boolean isEmpty() {        
        return nodeCount==0;
    }

    @Override
    public boolean contains(T element) {        
        Node frontNode = firstNode;
        Node rearNode = lastNode;
                
        while(frontNode!=null){                        
            if (element.equals(frontNode.element) || element.equals(rearNode.element)) {
                return true;
            }            
            frontNode = frontNode.nextNode;
            rearNode = rearNode.prevNode;
        }   
        return false;     
    }

    @Override
    public T[] toArray() {        
        Node frontNode = firstNode;
        Node rearNode = lastNode;
        T[] arr = (T[]) new Object[nodeCount];
        for(int i = 0, j = nodeCount; i < nodeCount && j >= 0; i++, j--){
            arr[i] = frontNode.element;
            arr[j-1] = rearNode.element;
            if(i == j){
                break;
            }
            frontNode = frontNode.nextNode;
            rearNode = rearNode.prevNode;
        }
        return arr;
    }
    
    @Override
    public void clear() {        
        nodeCount = 0;
        firstNode = null;
        lastNode = null;
    }

    @Override
    public void addAll(T[] elements) {        
        for (T element : elements) {
            addLast(element);
        }
    }        

    @Override
    public void addFirst(T element) {  
        Node newNode = new Node(element);
        if(isEmpty()){            
            lastNode = newNode;
        }else{            
            firstNode.prevNode = newNode;
            newNode.nextNode = firstNode;
        }        
        firstNode = newNode;
        nodeCount++;        
    }

    @Override
    public void addLast(T element) {        
        Node newNode = new Node(element);
        if(isEmpty()){
            firstNode = newNode;
        }else{
            lastNode.nextNode = newNode;
            newNode.prevNode = lastNode;
        }
        lastNode = newNode;
        nodeCount++;        
    }

    @Override
    public void removeFirst() {
        if(!isEmpty()){
            if(firstNode.nextNode!=null){
                firstNode = firstNode.nextNode;
                firstNode.prevNode.nextNode = null;
                firstNode.prevNode = null;
                nodeCount--;
            }else{
                clear();
            }
        }else{
            System.out.println("Unable to remove! Nothing inside.");
        }        
    }

    @Override
    public void removeLast() {        
        if(!isEmpty()){
            if(lastNode.prevNode!=null){
                lastNode = lastNode.prevNode;
                lastNode.nextNode.prevNode = null;
                lastNode.nextNode = null;
                nodeCount--;
            }else{
                clear();
            }
        }else{
            System.out.println("Unable to remove! Nothing inside.");
        }        
    }

    @Override
    public T peekFirst() {        
        if(firstNode!=null){
            return firstNode.element;
        }
        return null;        
    }

    @Override
    public T peekLast() {    
        if(firstNode!=null){    
            return lastNode.element;
        }
        return null;
    }

    @Override
    public T pollFirst() {        
        T elementGet = peekFirst();
        if(elementGet!=null){
            removeFirst();
        }        
        return elementGet;
    }

    @Override
    public T pollLast() {                
        T elementGet = peekLast();
        if(elementGet!=null){
            removeLast();
        }
        return elementGet;
    }    
    
    @Override
    public String toString() {        
        String outputStrFront = "";
        String outputStrRear = "";
        Node frontNode = firstNode;
        Node rearNode = lastNode;        
        boolean isEven = size()%2==0;
        while (frontNode!=null){                        
            outputStrFront += frontNode.element + "\n";                            
            if(isEven){
                outputStrRear = rearNode.element + "\n" + outputStrRear;
            }
            if(frontNode.equals(rearNode) ){                
                return outputStrFront+"\n"+outputStrRear;
            }            
            frontNode = frontNode.nextNode;
            rearNode = rearNode.prevNode;
        }
        return outputStrFront;
    }

    @Override
    public Iterator<T> iterator() {        
        return new LinkedIterator();
    }

    private class Node{
        private final T element;
        private Node prevNode,nextNode;

        private Node(T element){
            this.element = element;
        }

        private Node(T element, Node prevNode, Node nextNode){
            this.element = element;
            this.prevNode = prevNode;
            this.nextNode = nextNode;
        }
    }    

    private class LinkedIterator implements Iterator<T>{   
        private Node currentNode = firstNode;

        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        public T next() {
            if(hasNext()){
                T element = currentNode.element;
                currentNode = currentNode.nextNode;
                return element;
            }      
            return null;
        }        
    }

    public static void main(String[] args) {
        LinkedDeque<Integer> intDeque = new LinkedDeque<>();
        intDeque.addLast(3);
        intDeque.addFirst(2);
        intDeque.addLast(4);
        intDeque.addFirst(1);
        intDeque.addFirst(0);
        intDeque.addLast(5);
        
        System.out.println(intDeque);                

        // System.out.println("0? "+intDeque.contains(0));
        // System.out.println("5? "+intDeque.contains(5));
        // System.out.println("2? "+intDeque.contains(2));
        // System.out.println("3? "+intDeque.contains(3));
        // System.out.println("1? "+intDeque.contains(1));
        // System.out.println("4? "+intDeque.contains(4));
        // System.out.println("1? "+intDeque.contains(7));
        // System.out.println("1? "+intDeque.contains(-2));
        // System.out.println("4? "+intDeque.contains(10));
        // System.out.println("4? "+intDeque.contains(-20));

        System.out.println("peekFirst: "+intDeque.peekFirst());
        System.out.println("peekLast: "+intDeque.peekLast());

        System.out.println(intDeque);

        // System.out.println("pollFirst: "+intDeque.pollFirst());
        // System.out.println("pollLast: "+intDeque.pollLast());        

        System.out.println(intDeque);

        // intDeque.removeFirst();
        // intDeque.removeLast();

        // System.out.println(intDeque);

        // intDeque.clear();

        // System.out.println("clear? "+intDeque);

        // System.out.println(intDeque.isEmpty());

        Object[] obj = intDeque.toArray();
        Integer[] ints = new Integer[obj.length];
        int i =0;
        for(Object o:obj){
            System.out.println("object: "+o);
            ints[i] = (int)o;
            i++;
        }

        intDeque.clear();
        intDeque.addAll(ints);

        System.out.println("added all");

        System.out.println(intDeque);
    }
}

