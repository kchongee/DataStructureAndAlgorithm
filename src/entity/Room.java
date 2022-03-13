package entity;
import adtImplementation.Account;
import adtInterfaces.ListInterface;
import adtInterfaces.ListInterfaceEe;
import adtInterfaces.QueueInterface;

public class Room{    
    private String roomId,roomTitle;
    private int roomMemberCount;        
    private ListInterfaceEe<Buyer> likes;
    private ListInterfaceEe<Buyer> buyers;
    private QueueInterface<Comment> comments;
    private Catalog catalog;
    private boolean isOpen;
    private static int id = 0;

    public Room(){
        this.roomId = String.format("ROOM%4s", id).replace(' ', '0');
        this.roomMemberCount = 0;
        this.isOpen = false;
        id++;
    }

    public Room(String roomTitle) {
        this();        
        this.roomTitle = roomTitle;        
    }

    public String getRoomId() {
        return roomId;
    }    

    public boolean addLike(Buyer buyer){
        int buyerIndex = likes.retrieve(buyer);
        if(buyerIndex==-1){
            likes.add(buyer);
            return true;
        }
        return false;
    }

    public boolean removeLike(Buyer buyer){
        int buyerIndex = likes.retrieve(buyer);
        if(buyerIndex!=-1){
            likes.remove(buyerIndex);
            return true;
        }        
        return false;
    }

    public boolean addComment(Account acc, String text){
        Comment comment = new Comment(acc, text);
        comments.add(comment);
        return false;
    }

    public void openRoom(){
        this.isOpen = true;
    }

    public void addBuyerIntoRoom(Buyer buyer){
        buyers.add(buyer);
    }

    public void removeBuyerFromRoom(Buyer buyer){
        buyers.remove(buyer);
    }
}