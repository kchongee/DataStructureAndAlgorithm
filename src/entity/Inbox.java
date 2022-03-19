package entity;

import adtImplementation.ArrayList;
import adtInterfaces.ListInterface;

public class Inbox{
    private ListInterface<Notification> notifications;

    public Inbox(){
        this.notifications = new ArrayList<>();
    }    

    public ListInterface<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(ListInterface<Notification> notifications) {
        this.notifications = notifications;
    }

    public void addNotification(Notification notification){
        this.notifications.add(notification);
    }

    public void removeNotification(Notification notification){
        this.notifications.remove(notification);
    }

    public void openNotification(Notification notification){
        if(!notification.isRead())
            notification.setRead(true);
    } 
    
    public void openNotificationByIndex(int index){
        Notification notification = this.notifications.get(index);
        this.openNotification(notification);
    } 

    @Override
    public String toString() {        
        return notifications.size()>0?Notification.displayAll(notifications):"There is nothing inside inbox.\n";
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