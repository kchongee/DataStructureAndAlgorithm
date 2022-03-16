package adtImplementation;

import java.util.Iterator;

import adtInterfaces.StackInterface;

public class StackLinkedList<T> implements StackInterface<T>{    

    private Node topNode;
    private int nodeCount;

    public StackLinkedList(){
        clear();
    }    

    @Override
    public void push(T element) {                
        Node newNode = new Node(element);
        if(!isEmpty()){
            newNode.nextNode = topNode;
        }        
        topNode = newNode;
        nodeCount++;
    }

    @Override
    public T pop() {
        System.out.println("pop");
        T elementGet = peek();
        if(elementGet!=null){
            if(topNode.nextNode!=null){
                topNode = topNode.nextNode;
                nodeCount--;
            }else{
                clear();
            }            
            return elementGet;   
        }else{            
            System.out.println("Nothing inside! Unable to pop.");
            return null;
        }
    }

    @Override
    public T peek() {        
        if(isEmpty()){
            System.out.println("Nothing inside! Unable to peek.");
            return null;
        }        
        return topNode.element;
    }

    @Override
    public boolean isEmpty() {        
        return nodeCount==0;
    }

    @Override
    public int size() {        
        return nodeCount;
    }

    @Override
    public void clear() { 
        nodeCount = 0;       
        topNode = null;
    }
    
    @Override    
    public String toString() {
        String outputStr = "";
        Node currentNode = topNode;
        while (currentNode != null) {
            outputStr += currentNode.element + "\n";
            currentNode = currentNode.nextNode;
        }
        return outputStr;
    }

    public Iterator<T> iterator(){
        return new LinkedIterator();
    }

    private class LinkedIterator implements Iterator<T>{   
        private Node currentNode = topNode;

        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        public T next() {
            if(hasNext()){
                T data = currentNode.element;
                currentNode = currentNode.nextNode;
                return data;
            }      
            return null;
        }        
    }
    private class Node{
        private T element;        
        private Node nextNode;

        private Node(T element){
            this.element = element;
        }

        private Node(T element, Node nextNode){
            this.element = element;            
            this.nextNode = nextNode;
        }
    }
}
