package entity;

import UtilityClasses.jdbcUtil;
import adtImplementation.ArrayList;
import adtImplementation.LinkedStack;
import adtInterfaces.ListInterface;
import adtInterfaces.StackInterface;

public class Inbox{
    private StackInterface<Notification> notificationStack;    

    public Inbox(){    
        this.notificationStack = new LinkedStack<Notification>();
    }    
    
    public StackInterface<Notification> getNotifications() {
        return notificationStack;
    }

    public void setNotifications(StackInterface<Notification> notifications) {
        this.notificationStack = notifications;
    }

    public void pushNotification(Notification notification){
        this.notificationStack.push(notification);
    }    

    @Override
    public String toString() {        
        return notificationStack.size()>0?Notification.displayAll(notificationStack):"There is nothing inside inbox.\n";
    }

    /*public static void main(String[] args) {
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

    }*/
}
