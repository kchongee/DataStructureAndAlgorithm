package entity;

import java.time.LocalDate;
import java.time.LocalTime;

import UtilityClasses.jdbcUtil;
import adtImplementation.ArrayList;
import adtImplementation.HashMap;
import adtInterfaces.ListInterface;
import adtInterfaces.MapInterface;
import adtInterfaces.QueueInterface;

public class Room{    
    private Seller seller;
    private String roomID,roomTitle;    
    private ListInterface<Buyer> likes;
    private ListInterface<Buyer> buyers;
    private QueueInterface<Comment> comments;    
    // private MapInterface<String,Product> catalog;
    private Catalog catalog;
    private LocalDate dateOpen;
    private LocalTime timeOpen;
    private boolean isOpen;
    private static int id = 0;
    private LikeList likeList;
    private ReviewList reviewList;

    /*Problem
     * Statement : likeArr vs likes array??
     */


    // region : constructors
    public Room(){        
        this.roomID = String.format("ROOM%4s", id).replace(' ', '0');        
        this.buyers = new ArrayList<Buyer>();
        this.isOpen = false;
        // this.catalog = new HashMap<>();
        this.catalog = new Catalog();
        this.dateOpen = LocalDate.now();
        this.timeOpen = LocalTime.now();
        id++;
    }

    public Room(String roomID){
        this.roomID = roomID;
    }

    public Room(String roomID, String roomTitle) {
        this.roomID = roomID;
        this.roomTitle = roomTitle;
    }
 
    public Room(String roomTitle, Catalog catalog, Seller seller) {
        this();
        this.roomTitle = roomTitle;
        this.catalog = catalog;
        this.seller = seller;
    }

    public Room(String roomTitle, Catalog catalog, Seller seller, LocalDate dateOpen, LocalTime timeOpen) {
        this();
        this.roomTitle = roomTitle;
        this.catalog = catalog;
        this.seller = seller;
        this.dateOpen = dateOpen;
        this.timeOpen = timeOpen;
    }

    public Room(String roomID, String roomTitle, boolean isOpen)
    {
        this.roomID = roomID;
        this.roomTitle = roomTitle;
        this.isOpen = isOpen;
        this.likeList = new LikeList(this);
        this.reviewList = new ReviewList(this);
    }
    // endregion


    public String getRoomID() {
        return roomID;
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


    //region: getter setters
    public ListInterface<Buyer> getLikes() {
        return likes;
    }

    public ListInterface<Buyer> getBuyers() {
        return buyers;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public LikeList getLikeList() {
        return likeList;
    }

    public void setLikeList(LikeList likeList) {
        this.likeList = likeList;
    }

    public ReviewList getReviewList() {
        return reviewList;
    }

    public void setReviewList(ReviewList reviewList) {
        this.reviewList = reviewList;
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

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }    

    public void setRoomID(String roomID) {
        this.roomID = roomID;
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

    // public ListInterface<Buyer> getBuyers() {
    //     return buyers;
    // }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }
    
    

    public Catalog fetchCatalogFromDB()
    {

        ArrayList<Product> productList = new ArrayList<>();
        String query =
                String.format(
                """
                SELECT title, productDesc, price
                FROM   product p, roomCatalog rc
                WHERE  p.productID = rc.productID AND rc.roomID=%s
                ORDER BY rc.productID;
                """,roomID
                );

        // debug
        // System.out.println(query);



        ArrayList<HashMap<String, Object>> products = jdbcUtil.readAll(query);

        for (int i = 0 ; i < products.size() ; i++) {
            productList.add(new Product(products.get(i)));
        }

        return new Catalog(productList);
    }
    // endregion

    @Override
    public String toString() {
        return "Room{" +
                "roomID='" + roomID + '\'' +
                ", roomTitle='" + roomTitle + '\'' +
                ", likes=" + likes +
                ", buyers=" + buyers +
                ", comments=" + comments +
                ", catalog=" + catalog +
                ", isOpen=" + isOpen +
                ", likeList=" + likeList +
                ", reviewList=" + reviewList +
                '}';
    }


    //
}