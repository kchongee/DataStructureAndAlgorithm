package view;

import UtilityClasses.jdbcUtil;
import adtImplementation.ArrayList;
import adtInterfaces.ListInterface;
import application.App;
import entity.Notification;
import entity.Option;

public class NotificationView {

    public static ListInterface<Option> menuOptions = new ArrayList<Option>();

    public static void main() {
        App.history.push(i -> BuyerHomeView.main());

        retrieveNotifications();;
        
        for(int i=0;i<App.notificationList.getNotificationHolder().size();i++){
            if(App.currentUser.getAccountID().equals(App.notificationList.grabNotification(i).getAccountID())){
                App.inbox.push(App.notificationList.grabNotification(i));
            }
        }

        printTitle("Inbox");
        System.out.println(App.inbox.toString());;

        App.menuHandler(menuOptions); 
    }

    public static void printTitle(String title){
        App.clearScreen();
        System.out.println(String.format("========= %s =========",title));       
        System.out.println();
    }

    public static void retrieveNotifications(){
        App.hashNotifications = jdbcUtil.readAll(String.format("SELECT * FROM Notification WHERE accountID = '%S';", App.currentUser.getAccountID()));

        for(int i=0;i<App.hashNotifications.size();i++){      
            Notification n = new Notification(App.hashNotifications.get(i).get("notificationID"),
            App.hashNotifications.get(i).get("accountID"),
            App.hashNotifications.get(i).get("userName"),
            App.hashNotifications.get(i).get("message"),
            App.hashNotifications.get(i).get("date"));         
            App.notificationList.addNotification(n);
        } 
    }
}
