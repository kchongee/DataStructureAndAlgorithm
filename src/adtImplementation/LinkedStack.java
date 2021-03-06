package adtImplementation;

import java.util.Iterator;

import adtInterfaces.StackInterface;

public class LinkedStack<T> implements StackInterface<T>{    

    private Node topNode;
    private int nodeCount;

    public LinkedStack(){
        clear();
    }    

    @Override
    public boolean push(T element) {                
        Node newNode = new Node(element);
        if(!isEmpty()){
            newNode.nextNode = topNode;
        }        
        topNode = newNode;
        nodeCount++;
        return true;
    }

    @Override
    public T pop() {        
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
            return null;
        }
    }

    @Override
    public T peek() {        
        if(isEmpty()){            
            return null;
        }        
        return topNode.element;
    }

    @Override
    public boolean contains(T element) {
        boolean found = false;
        Node currentNode = topNode;

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
        private final T element;
        private Node nextNode;

        private Node(T element){
            this.element = element;
        }

        private Node(T element, Node nextNode){
            this.element = element;            
            this.nextNode = nextNode;
        }
    }

    public static void main(String[] args) {
        LinkedStack<Integer> intStack = new LinkedStack<Integer>();
        intStack.push(1);
        intStack.push(4);
        intStack.push(3);
        intStack.push(2);
        intStack.push(5);
        System.out.println(intStack);
    }
}
