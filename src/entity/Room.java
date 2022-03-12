package entity;
import adtInterfaces.ListInterface;

public class Room{
    private String roomId,roomTitle;
    private int roomMemberCount;        
    private ListInterface<Like> likes;
    private ListInterface<Comment> comments;
    private Catalog catalog;
    // private ListInterface<Customer> customers;

    public Room(){
        
    }
    
}