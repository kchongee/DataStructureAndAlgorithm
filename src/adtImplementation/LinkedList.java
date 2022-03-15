package adtImplementation;

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
            while (currentNode.next != null) { // while have not reached the last node
                currentNode = currentNode.next;
            }
            currentNode.next = newNode; // make last node reference new node
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
                newNode.next = firstNode;
                firstNode = newNode;
            } else {								// case 2: list is not empty and newPosition > 1
                Node nodeBefore = firstNode;
                for (int i = 1; i < newPosition - 1; ++i) {
                    nodeBefore = nodeBefore.next;		// advance nodeBefore to its next node
                }
                newNode.next = nodeBefore.next;	// make new node point to current node at newPosition
                nodeBefore.next = newNode;		// make the node before point to the new node
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
        int i = 0;     
        if(!isEmpty()){
            while(currentNode.next!=null && i++<index){
                Node doubleBackNode = currentNode.next.next;
                if(doubleBackNode!=null){
                    currentNode.next = doubleBackNode;
                }
                currentNode.next= null;
                return true;
            }
        }                        
        return false;
    }

    @Override
    public boolean remove(T element) {                
        Node currentNode = firstNode;        
        if(!isEmpty()){
            while(currentNode.next!=null && currentNode.equals(element)){
                Node doubleBackNode = currentNode.next.next;
                if(doubleBackNode!=null){
                    currentNode.next = doubleBackNode;
                }
                currentNode.next= null;
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
                currentNode = currentNode.next;		// advance currentNode to next node
            }
            result = currentNode.data;	// currentNode is pointing to the node at index
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
                currentNode = currentNode.next;		// advance currentNode to next node
            }
            currentNode.data = newElement;	// currentNode is pointing to the node at index
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
            if (element.equals(currentNode.data)) {
                found = true;
            } else {
                currentNode = currentNode.next;
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
        for (Node x = firstNode; x != null; x = x.next)
            result[i++] = x.data;

        if (a.length > size())
            a[size()] = null;

        return a;
    }

    // public Object[] toArray(){
    //     Object[] arr = new Object[size()];
    //     Node currentNode = firstNode;
    //     int i = 0;
    //     while (currentNode != null) {
    //         arr[i] = currentNode.data;            
    //         currentNode = currentNode.next;
    //         i++;
    //     }
    //     return arr;
    // }

    @Override
    public String toString() {
        String outputStr = "";
        Node currentNode = firstNode;
        while (currentNode != null) {
            outputStr += currentNode.data + "\n";
            currentNode = currentNode.next;
        }
        return outputStr;
    }
    
    private class Node {

        private T data;
        private Node next;
    
        private Node(T data) {
            this.data = data;
            this.next = null;
        }
    
        private Node(T data, Node next) {
            this.data = data;
            this.next = next;
        }
    }
}
