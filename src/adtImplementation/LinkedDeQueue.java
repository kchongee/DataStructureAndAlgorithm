package adtImplementation;

import java.util.Iterator;

import adtInterfaces.DeQueueInterface;

public class LinkedDeQueue<T> implements DeQueueInterface<T>{
    // public int firstIndex,lastIndex;   
    private Node firstNode, lastNode;
    private int nodeCount;

    public LinkedDeQueue(){
        clear();
    }

    public LinkedDeQueue(T... elements){
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
        
        while (true){
            if (element.equals(frontNode.element) || element.equals(rearNode.element)) {
                return true;
            }else if(frontNode.equals(rearNode)){
                break;
            }
            frontNode = frontNode.nextNode;
            lastNode = lastNode.prevNode;
        }   
        return false;     
    }

    @Override
    public T[] toArray() {        
        Node frontNode = firstNode;
        Node rearNode = lastNode;
        T[] arr = (T[]) new Object[nodeCount];
        for(int i = 0, j = nodeCount; i < nodeCount && j >= 0; i++, j--){
            arr[i] = firstNode.element;
            arr[j-1] = lastNode.element;
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
        if(nodeCount==0){
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
            outputStrFront += frontNode.element + "\n\n";                            
            if(isEven){
                outputStrRear = rearNode.element + "\n" + outputStrRear;
            }
            if (firstNode.equals(rearNode) ){                
                return outputStrFront+"\n"+outputStrRear;
            }            
            frontNode = frontNode.nextNode;
            lastNode = lastNode.prevNode;
        }
        return outputStrFront;
    }

    @Override
    public Iterator<T> iterator() {        
        return new LinkedIterator();
    }

    private class Node{
        private T element;        
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
}

