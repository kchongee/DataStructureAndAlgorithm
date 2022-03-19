package entity;

import java.util.Iterator;

import adtImplementation.ArrayList;
import adtInterfaces.ListInterface;
import application.App;
public class Notification {
    private String notificationID;
    private String accountID;
    private String userName;
    private String message;
    private String date;
    private String title;
    private Seller seller;
    private boolean isRead;
    static int counter=1;      

    public Notification(){
        this.notificationID="";
        this.accountID="";
        this.userName="";
        this.message="";
        this.date="";  
    }

    public Notification(String accountID, String userName, String message, String date){
        this.notificationID=String.format("N%d", Notification.counter);
        this.accountID=accountID;
        this.userName=userName;
        this.message=message;
        this.date=date;
        
        Notification.counter++;
    }

    public Notification(Object accountID, Object userName, Object message, Object date){
        this.notificationID=String.format("N%d", Notification.counter);
        this.accountID=(String)accountID;
        this.userName=(String)userName;
        this.message=(String)message;
        this.date=(String)date;
        
    }

    public Notification(String notificationID, String accountID, String userName, String message, String date){
        this.notificationID=notificationID;
        this.accountID=accountID;
        this.userName=userName;
        this.message=message;
        this.date=date;
        
    }

    public Notification(Object notificationID, Object accountID, Object userName, Object message, Object date){
        this.notificationID=(String)notificationID;
        this.accountID=(String)accountID;
        this.userName=(String)userName;
        this.message=(String)message;
        this.date=(String)date;
       
        Notification.counter++;
    }

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

    public String getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(String notificationID) {
        this.notificationID = notificationID;
    }

    public String getAccountID() {
        return accountID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
    
    // @Override
    // public String toString() {
    //     // TODO Auto-generated method stub
    //     return "\nNotificationID: "+notificationID+
    //     "\nUsername: "+userName+
    //     "\nMessage: "+message+
    //     "\nDate: "+date;
    // }

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
