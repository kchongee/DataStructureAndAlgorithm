package entity;

import adtImplementation.StackLinkedList;
import adtInterfaces.StackInterface;

public class Inbox {
    private StackInterface<Notification> inboxStack;

    public Inbox(int size){
        inboxStack=new StackLinkedList<Notification>();
    }

    
}
