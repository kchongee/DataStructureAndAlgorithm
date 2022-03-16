package view;

import application.App;

public class NotificationView {
    public static void main() {
        for(int i=0;i<App.notificationList.getNotificationHolder().size();i++){
            if(App.currentUser.getAccountID().equals(App.notificationList.grabNotification(i).getAccountID()) && 
            App.notificationList.grabNotification(i).getHasBeenRead()==0){
                App.inbox.push(App.notificationList.grabNotification(i));
            }
        }

        App.inbox.toString();

        System.out.println("Press any key to clear out your inbox");

        
    }
}
