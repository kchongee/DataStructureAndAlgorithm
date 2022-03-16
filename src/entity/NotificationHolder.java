package entity;

import adtImplementation.ArrayList;
import adtInterfaces.ListInterface;

public class NotificationHolder {
    private ListInterface<Notification> notificationHolder;

    public NotificationHolder(int size){
        notificationHolder = new ArrayList<Notification>(size);
    }

    public ListInterface<Notification> getNotificationHolder() {
        return notificationHolder;
    }

    public int size(){
        return notificationHolder.size();
    }

    public Notification grabNotification(int index){
        Notification grabbed=null;
        if(index>=0 && index<notificationHolder.size()){
            grabbed = notificationHolder.get(index);
        }
        return grabbed;
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
