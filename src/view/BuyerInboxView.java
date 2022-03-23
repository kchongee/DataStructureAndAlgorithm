package view;

import java.util.Iterator;
import java.util.function.Consumer;

import UtilityClasses.jdbcUtil;
import adtImplementation.ArrayList;
import adtImplementation.LinkedDeque;
import adtInterfaces.DequeInterface;
import adtInterfaces.ListInterface;
import application.App;
import application.Option;
import entity.Buyer;
import entity.Inbox;
import entity.Notification;

public class BuyerInboxView {
    public static ListInterface<Option> menuOptions = new ArrayList<Option>();
    public static DequeInterface<Notification> notifications = new LinkedDeque<Notification>();
    public static Inbox inbox;
    
    static {   
        retrieveNotifications();    
        
        if(notifications.size()>0){
            menuOptions.add(new Option("Open notification", i-> goToPage(ii->BuyerNotificationView.main())));
        }
    }

    public static void main(String[] args) {
        main();
    }

    public static void main() {
        printTitle("Inbox");

        retrieveNotifications();

        System.out.println(inbox);
        
        App.menuHandler(menuOptions);
    }
    
    public static void printTitle(String title){
        App.clearScreen();
        System.out.println(title);        
        System.out.println();
    }

    public static void goToPage(Consumer<String> page){
        App.history.push(i -> main());
        page.accept("t");
    }

    public static void retrieveNotifications(){
        inbox = ((Buyer)App.currentUser).getInbox(); 
        Iterator<Notification> notificationIterator = inbox.getNotifications().iterator();
        while(notificationIterator.hasNext()){
            Notification n = notificationIterator.next();
            notifications.addLast(n);            
        }
    }
}
