package adtImplementation;

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
            if (element.equals(frontNode.element)) {                
                return true;
            }else if(element.equals(rearNode.element)){
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
    
    // @Override
    // public boolean remove(Object o) {        
    //     return false;
    // }

    // @Override
    // public boolean add(T e) {        
    //     // if(nodeCount==0){
    //     //     Node newNode = new Node();
    //     //     firstNode = newNode;
    //     //     lastNode = newNode;
    //     // }
    //     return false;
    // }

    // @Override
    // public boolean offer(T e) {        
    //     return false;
    // }

    // @Override
    // public T poll() {        
    //     return null;
    // }

    // @Override
    // public T peek() {        
    //     return null;
    // }

    // @Override
    // public T element() {        
    //     return null;
    // }

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

    // @Override
    // public boolean offerFirst(T element) {        
    //     return false;
    // }

    // @Override
    // public boolean offerLast(T element) {        
    //     return false;
    // }
    
}

