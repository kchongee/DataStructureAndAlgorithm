package entity;

import adtImplementation.ArrayList;
import adtInterfaces.ListInterface;

public class NotificationHolder {
    private ListInterface<Notification> notificationHolder;

    public NotificationHolder(int size){
        notificationHolder = new ArrayList<Notification>(size);
    }

    public boolean addNotification(Notification inputNotification){
        boolean successful = notificationHolder.add(inputNotification);
        System.out.println(successful);
        if(successful){
            inputNotification.toString();
        } else{
            return false;
        }
        return true;
    }

}
