package entity;

public class Notification {
    private String notificationID;
    private String accountID;
    private String userName;
    private String message;
    private String date;
    private int hasBeenRead;

    static int counter=1;

    public Notification(){
        this.notificationID="";
        this.accountID="";
        this.userName="";
        this.message="";
        this.date="";  
        this.hasBeenRead=0;
    }

    public Notification(String accountID, String userName, String message, String date){
        this.notificationID=String.format("N%d", Notification.counter);
        this.accountID=accountID;
        this.userName=userName;
        this.message=message;
        this.date=date;
        this.hasBeenRead=0;
        Notification.counter++;
    }

    public Notification(Object accountID, Object userName, Object message, Object date){
        this.notificationID=String.format("N%d", Notification.counter);
        this.accountID=(String)accountID;
        this.userName=(String)userName;
        this.message=(String)message;
        this.date=(String)date;
        this.hasBeenRead=0;
    }

    public Notification(String notificationID, String accountID, String userName, String message, String date, int hasBeenRead){
        this.notificationID=notificationID;
        this.accountID=accountID;
        this.userName=userName;
        this.message=message;
        this.date=date;
        this.hasBeenRead=hasBeenRead;
    }

    public Notification(Object notificationID, Object accountID, Object userName, Object message, Object date, Object hasBeenRead){
        this.notificationID=(String)notificationID;
        this.accountID=(String)accountID;
        this.userName=(String)userName;
        this.message=(String)message;
        this.date=(String)date;
        this.hasBeenRead=(int)hasBeenRead;
        Notification.counter++;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getHasBeenRead() {
        return hasBeenRead;
    }

    public void setHasBeenRead(int hasBeenRead) {
        this.hasBeenRead = hasBeenRead;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "\nNotificationID: "+notificationID+
        "\nUsername: "+userName+
        "\nMessage: "+message+
        "\nDate: "+date;
    }
}
