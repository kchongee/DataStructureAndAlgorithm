package entity;

import adtImplementation.ArrayList;
import adtImplementation.LinkedStack;
import adtInterfaces.ListInterface;
import adtInterfaces.StackInterface;

public class Inbox{
    private ListInterface<Notification> notifications;
    private StackInterface<Notification> inboxStack;

    public Inbox(int size){
        inboxStack=new LinkedStack<Notification>();
    }

    public Inbox(){
        this.notifications = new ArrayList<>();
        this.inboxStack = new LinkedStack<Notification>();
    }    

    // public ListInterface<Notification> getNotifications() {
    //     return notifications;
    // }

    // public void setNotifications(ListInterface<Notification> notifications) {
    //     this.notifications = notifications;
    // }

    // public void addNotification(Notification notification){
    //     this.notifications.add(notification);
    // }

    // public void removeNotification(Notification notification){
    //     this.notifications.remove(notification);
    // }

    // public void openNotification(Notification notification){
    //     if(!notification.isRead())
    //         notification.setRead(true);
    // } 
    
    // public void openNotificationByIndex(int index){
    //     Notification notification = this.notifications.get(index);
    //     this.openNotification(notification);
    // } 

    public StackInterface<Notification> getNotifications() {
        return inboxStack;
    }

    public void setNotifications(StackInterface<Notification> notifications) {
        this.inboxStack = notifications;
    }

    public void pushNotification(Notification notification){
        this.inboxStack.push(notification);
    }    

    public void openNotification(Notification notification){
        if(!notification.isRead())
            notification.setRead(true);
    }

    @Override
    public String toString() {        
        return inboxStack.size()>0?Notification.displayAll(inboxStack):"There is nothing inside inbox.\n";
    }

    public static void main(String[] args) {
        Seller newSeller = new Seller();
        newSeller.setName("Khoo");        
        Notification notification1 = new Notification("title", "message111  11", newSeller);
        Notification notification2 = new Notification("title2", "message112  12", newSeller);
        Notification notification3 = new Notification("title3", "message113  13", newSeller);
        
        ListInterface<Notification> notifications = new ArrayList<>();
        notifications.add(notification1);
        notifications.add(notification2);
        notifications.add(notification3);

        Buyer buyer = new Buyer();        
        System.out.println(notifications);

    }
}
