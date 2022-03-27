package application2;

import java.util.Iterator;
import java.util.function.Consumer;
import adtImplementation.ArrayList;
import adtInterfaces.DequeInterface;
import adtInterfaces.ListInterface;
import entity.Buyer;
import entity.Notification;

public class BuyerNotificationView {
    public static ListInterface<Option> menuOptions = new ArrayList<Option>();
    public static DequeInterface<Notification> notifications;
    private static Notification currentNotification;
    
    static {
        menuOptions.add(new Option("Next ->", i -> nextPage()));
        menuOptions.add(new Option("Prev <-", i -> prevPage()));

        notifications = BuyerInboxView.notifications;
    }

    public static void main(String[] args) {        
        main();
    }

    public static void main() {
        fetchNotificationList();      

        printCurrentNotification();
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

    public static void fetchNotificationList(){
        notifications.clear();        
        Iterator<Notification> notificationIterator = ((Buyer)App.currentUser).getInbox().getNotifications().iterator();        
        while(notificationIterator.hasNext()){
            notifications.addLast(notificationIterator.next());
        }
    }

    public static void nextPage(){        
        notifications.addLast(notifications.pollFirst());
        clearAndPrintMainAgain();
    }

    public static void prevPage(){
        notifications.addFirst(notifications.pollLast());
        clearAndPrintMainAgain();
    }

    public static void clearAndPrintMainAgain(){
        App.clearScreen();
        printCurrentNotification();
    }

    public static void printCurrentNotification(){
        currentNotification = notifications.peekFirst();
        ((Buyer)App.currentUser)
            .getInbox()
            .openNotification(currentNotification);
        currentNotification.setRead(true);

        App.clearScreen();
        System.out.println(currentNotification);
        
        App.menuHandler(menuOptions);
    }
}
