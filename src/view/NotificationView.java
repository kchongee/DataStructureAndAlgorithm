package view;

import adtImplementation.ArrayList;
import adtInterfaces.ListInterface;
import application.App;
import entity.Option;

public class NotificationView {

    public static ListInterface<Option> menuOptions = new ArrayList<Option>();

    public static void main() {
        App.history.push(i -> BuyerHomeView.main());
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
}
