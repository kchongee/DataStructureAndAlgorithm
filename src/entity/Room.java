package entity;

import adtImplementation.ArrayList;
import adtImplementation.HashMap;
import adtInterfaces.ListInterface;
import adtInterfaces.MapInterface;
import adtInterfaces.QueueInterface;

public class Room{    
    private String roomId,roomTitle;    
    private ListInterface<Buyer> likes;
    private ListInterface<Buyer> buyers;
    private QueueInterface<Comment> comments;    
    private MapInterface<String,Product> catalog;
    private boolean isOpen;
    private static int id = 0;

    public Room(){        
        this.roomId = String.format("ROOM%4s", id).replace(' ', '0');        
        this.buyers = new ArrayList<Buyer>();
        this.isOpen = false;
        this.catalog = new HashMap<>();
        id++;
    }

    public Room(String roomId, String roomTitle){
        this.roomId = roomId;
        this.roomTitle = roomTitle;
 
    public Room(String roomTitle, MapInterface<String,Product> catalog) {
        this();
        this.roomTitle = roomTitle;
        this.catalog = catalog;
    }

    public String getRoomId() {
        return roomId;
    }    

    public boolean addLike(Buyer buyer){
        int buyerIndex = likes.get(buyer);
        if(buyerIndex==-1){
            likes.add(buyer);
            return true;
        }
        return false;
    }

    public boolean removeLike(Buyer buyer){
        int buyerIndex = likes.get(buyer);
        if(buyerIndex!=-1){
            likes.remove(buyerIndex);
            return true;
        }        
        return false;
    }

//    public boolean addComment(Account acc, String text){
//        Comment comment = new Comment(acc, text);
//        comments.add(comment);
//        return false;
//    }

    public void openRoom(){
        this.setOpen(true);
    }

    public void addBuyerIntoRoom(Buyer buyer){
        buyers.add(buyer);
    }

    public void removeBuyerFromRoom(Buyer buyer){
        buyers.remove(buyer);
    }    

    public String getRoomTitle() {
        return roomTitle;
    }

    public void setRoomTitle(String roomTitle) {
        this.roomTitle = roomTitle;
    }    

    public boolean isOpen() {
        return isOpen;
    }

    private void setOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public static int getId() {
        return id;
    }

    private static void setId(int id) {
        Room.id = id;
    }

    public ListInterface<Buyer> getLikes() {
        return likes;
    }

    public void setLikes(ListInterface<Buyer> likes) {
        this.likes = likes;
    }

    public ListInterface<Buyer> getBuyers() {
        return buyers;
    }

    public void setBuyers(ListInterface<Buyer> buyers) {
        this.buyers = buyers;
    }

    public QueueInterface<Comment> getComments() {
        return comments;
    }

    public void setComments(QueueInterface<Comment> comments) {
        this.comments = comments;
    }

    public MapInterface<String,Product> getCatalog() {
        return catalog;
    }

    public void setCatalog(MapInterface<String,Product> catalog) {
        this.catalog = catalog;
    }

    public void addCatalogProduct(String keywordProduct, Product product) {
        this.catalog.put(keywordProduct, product);
    }

    public int getRoomMemberCount() {
        return buyers.size();
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomTitle() {
        return roomTitle;
    }

    public void setRoomTitle(String roomTitle) {
        this.roomTitle = roomTitle;
    }

    public int getRoomMemberCount() {
        return roomMemberCount;
    }

    public void setRoomMemberCount(int roomMemberCount) {
        this.roomMemberCount = roomMemberCount;
    }

    public ListInterfaceEe<Buyer> getLikes() {
        return likes;
    }

    public void setLikes(ListInterfaceEe<Buyer> likes) {
        this.likes = likes;
    }

    public ListInterfaceEe<Buyer> getBuyers() {
        return buyers;
    }

    public void setBuyers(ListInterfaceEe<Buyer> buyers) {
        this.buyers = buyers;
    }

    public QueueInterface<Comment> getComments() {
        return comments;
    }

    public void setComments(QueueInterface<Comment> comments) {
        this.comments = comments;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Room.id = id;
    }
}