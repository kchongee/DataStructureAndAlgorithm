package entity;

import java.util.Iterator;

import adtImplementation.ArrayList;
import adtInterfaces.ListInterface;
import application.App;

public class Notification{    
    private String title,message;
    private Seller seller;
    private boolean isRead;

    public Notification(String title, String message, Seller seller){
        this.isRead = false;
        this.title = title;
        this.message = message;
        this.seller = seller;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }

    @Override
    public String toString() {
        String str = "";                        
        str += String.format("From: %s\n", seller.getName());                
        str += String.format("Title: %s\n", title);                    
        str += String.format("Message: %s\n", message);                
        return str;
    }    

    public static String displayAll(ListInterface<Notification> notifications){
        String str = "";
        str += String.format("+%s+\n", "-".repeat(56));
        str += String.format("|%-6s|%-25s|%-15s|%-7s|\n","No.", "Title","From","Status");
        str += String.format("|%-6s+%-25s+%-15s+%-7s|\n","-".repeat(6), "-".repeat(25), "-".repeat(15),"-".repeat(7));
        Iterator<Notification> notificationIterator = notifications.iterator();
        int i = 1;
        while(notificationIterator.hasNext()){
            Notification n = notificationIterator.next();
            str += String.format("|%-6d|%-25s|%-15s|%-7s|\n",i, App.trimString(n.getTitle(), 25), n.getSeller().getName(), n.isRead()?"Seen":"Unseen");
            i++;
        }
        str += String.format("+%s+\n", "-".repeat(56));
        return str;
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
        System.out.println(displayAll(notifications));

    }
}

