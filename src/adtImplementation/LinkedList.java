package adtImplementation;

import java.util.Iterator;

import adtInterfaces.ListInterface;

public class LinkedList<T> implements ListInterface<T>{

    private Node firstNode; // reference to first node
    private int nodeCount;  	// number of entries in list

    public LinkedList() {
        clear();
    }

    @Override
    public boolean add(T newElement) {
        Node newNode = new Node(newElement);	// create the new node

        if (isEmpty()) {
            firstNode = newNode;
        } else {                        // add to end of nonempty list
            Node currentNode = firstNode;	// traverse linked list with p pointing to the current node
            while (currentNode.nextNode != null) { // while have not reached the last node
                currentNode = currentNode.nextNode;
            }
            currentNode.nextNode = newNode; // make last node reference new node
        }

        nodeCount++;
        return true;
    }

    @Override
    public boolean add(int newPosition, T newElement) { // OutOfMemoryError possible
        boolean isSuccessful = true;

        if ((newPosition >= 1) && (newPosition <= nodeCount + 1)) {
            Node newNode = new Node(newElement);

            if (isEmpty() || (newPosition == 1)) { // case 1: add to beginning of list
                newNode.nextNode = firstNode;
                firstNode = newNode;
            } else {								// case 2: list is not empty and newPosition > 1
                Node nodeBefore = firstNode;
                for (int i = 1; i < newPosition - 1; ++i) {
                    nodeBefore = nodeBefore.nextNode;		// advance nodeBefore to its nextNode node
                }
                newNode.nextNode = nodeBefore.nextNode;	// make new node point to current node at newPosition
                nodeBefore.nextNode = newNode;		// make the node before point to the new node
            }
            nodeCount++;
        } else {
            isSuccessful = false;
        }

        return isSuccessful;
    }

    @Override
    public boolean remove(int index) {
        Node currentNode = firstNode;
        Node prevNode = null;
        int i = 0;         
        if(index<0 || index>=size())
            return false; 
        else{            
            while(i++<index){
                prevNode = currentNode;
                currentNode = currentNode.nextNode;
                if(currentNode==null){
                    return false;
                }
            }                        
            if(prevNode==null){
                firstNode = currentNode.nextNode;
            }else{
                prevNode.nextNode = currentNode.nextNode;
            }
            return true;            
        }
    }

    @Override
    public boolean remove(T element) {                
        Node currentNode = firstNode;        
        if(!isEmpty()){
            while(currentNode.nextNode!=null && currentNode.equals(element)){
                Node doubleBackNode = currentNode.nextNode.nextNode;
                if(doubleBackNode!=null){
                    currentNode.nextNode = doubleBackNode;
                }
                currentNode.nextNode= null;
                return true;
            }
        }                        
        return false;        
    }

    @Override
    public T get(int index) {
        T result = null;

        if ((index >= 0) && (index < nodeCount)) {
            Node currentNode = firstNode;
            for (int i = 0; i < index; ++i) {
                currentNode = currentNode.nextNode;		// advance currentNode to nextNode node
            }
            result = currentNode.element;	// currentNode is pointing to the node at index
        }
        return result;
    }  

    @Override
    public int get(T element) {        
        return 0;
    }

    @Override
    public boolean replace(int index, T newElement) {
        boolean isSuccessful = true;

        if ((index >= 1) && (index <= nodeCount)) {
            Node currentNode = firstNode;
            for (int i = 0; i < index - 1; ++i) {
                currentNode = currentNode.nextNode;		// advance currentNode to nextNode node
            }
            currentNode.element = newElement;	// currentNode is pointing to the node at index
        } else {
            isSuccessful = false;
        }

        return isSuccessful;
    }

    @Override
    public boolean contains(T element) {        
        boolean found = false;
        Node currentNode = firstNode;

        while (!found && (currentNode != null)) {
            if (element.equals(currentNode.element)) {
                found = true;
            } else {
                currentNode = currentNode.nextNode;
            }
        }
        return found;
    }

    @Override
    public boolean isEmpty() {
        boolean result;

        result = nodeCount == 0;

        return result;
    }

    @Override
    public int size() {        
        return nodeCount;
    }

    @Override
    public final void clear() {
        firstNode = null;
        nodeCount = 0;
    }

    // @Override
    public T[] toArray(T[] a) {
        if (a.length < size())
            a = (T[])java.lang.reflect.Array.newInstance(
                                a.getClass().getComponentType(), size());
        int i = 0;
        Object[] result = a;
        for (Node x = firstNode; x != null; x = x.nextNode)
            result[i++] = x.element;

        if (a.length > size())
            a[size()] = null;

        return a;
    }

    // public Object[] toArray(){
    //     Object[] arr = new Object[size()];
    //     Node currentNode = firstNode;
    //     int i = 0;
    //     while (currentNode != null) {
    //         arr[i] = currentNode.element;            
    //         currentNode = currentNode.nextNode;
    //         i++;
    //     }
    //     return arr;
    // }

    @Override
    public String toString() {
        String outputStr = "";
        Node currentNode = firstNode;
        while (currentNode != null) {
            outputStr += currentNode.element + "\n";
            currentNode = currentNode.nextNode;
        }
        return outputStr;
    }

    @Override
    public Iterator<T> iterator() {        
        return new LinkedIterator();
    }
    
    private class Node {

        private T element;
        private Node nextNode;
    
        private Node(T element) {
            this.element = element;
            this.nextNode = null;
        }
    
        private Node(T element, Node nextNode) {
            this.element = element;
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
}
