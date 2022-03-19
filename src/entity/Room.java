package entity;

import java.time.LocalDate;
import java.time.LocalTime;

import adtImplementation.ArrayList;
import adtImplementation.HashMap;
import adtInterfaces.ListInterface;
import adtInterfaces.MapInterface;
import adtInterfaces.QueueInterface;

public class Room{    
    private Seller seller;
    private String roomId,roomTitle;    
    private ListInterface<Buyer> likes;
    private ListInterface<Buyer> buyers;
    private QueueInterface<Comment> comments;    
    private MapInterface<String,Product> catalog;
    private LocalDate dateOpen;
    private LocalTime timeOpen;
    private boolean isOpen;
    private static int id = 0;

    public Room(){        
        this.roomId = String.format("ROOM%4s", id).replace(' ', '0');        
        this.buyers = new ArrayList<Buyer>();
        this.isOpen = false;
        this.catalog = new HashMap<>();
        this.dateOpen = LocalDate.now();
        this.timeOpen = LocalTime.now();
        id++;
    }

    public Room(String roomId, String roomTitle) {
        this.roomId = roomId;
        this.roomTitle = roomTitle;
    }
 
    public Room(String roomTitle, MapInterface<String,Product> catalog, Seller seller) {
        this();
        this.roomTitle = roomTitle;
        this.catalog = catalog;
        this.seller = seller;
    }

    public Room(String roomTitle, MapInterface<String,Product> catalog, Seller seller, LocalDate dateOpen, LocalTime timeOpen) {
        this();
        this.roomTitle = roomTitle;
        this.catalog = catalog;
        this.seller = seller;
        this.dateOpen = dateOpen;
        this.timeOpen = timeOpen;
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


    public void setLikes(ListInterface<Buyer> likes) {
        this.likes = likes;
    }


    public void setBuyers(ListInterface<Buyer> buyers) {
        this.buyers = buyers;
    }

    public void setCatalog(MapInterface<String,Product> catalog) {
        this.catalog = catalog;
    }

    public void addCatalogProduct(String keywordProduct, Product product) {
        this.catalog.put(keywordProduct, product);
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }


    public QueueInterface<Comment> getComments() {
        return comments;
    }

    public void setComments(QueueInterface<Comment> comments) {
        this.comments = comments;
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

    public ListInterface<Buyer> getBuyers() {
        return buyers;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }
    
    
}