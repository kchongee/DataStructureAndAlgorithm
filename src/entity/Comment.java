package entity;
import java.time.LocalDate;
import java.time.LocalTime;
import SubSystem.CommentDisplayer.CommentFormatter;
import UtilityClasses.DateTimeUtil;
import UtilityClasses.jdbcUtil;
import adtImplementation.ArrayList;
import adtImplementation.HashMap;

import javax.swing.*;

import static java.lang.System.out;

public class Comment implements Comparable<Comment>
{
    Account account;
    Room room;
    String content;
    LocalTime commentTime;
    LocalDate commentDate;
    CommentFormatter formatter;


    // region 001 : constructors
    public Comment(Account account, Room room, LocalTime commentTime, LocalDate commentDate, String content)
    {
        this.account = account;
        this.commentTime = commentTime;
        this.commentDate = commentDate;
        this.room = room;
        this.content = content;
        this.formatter = new CommentFormatter(this);
    }

    public Comment(Account account, Room room, HashMap<String, Object> commentEntry)
    {
        this.account = account;
        this.room = room;
        this.commentTime = DateTimeUtil.sqlTimeToLocalTime(commentEntry.get("commentTime"));
        this.commentDate = DateTimeUtil.stringObjToLocalDate(commentEntry.get("commentDate"));
        this.content = (String) commentEntry.get("content");
        this.formatter = new CommentFormatter(this);
    }
    // endregion


    // region public method
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setFormatter(CommentFormatter formatter) {
        this.formatter = formatter;
    }


    public int getSellingProductQtyFromDB()
    {
        Long qty = (Long)
        jdbcUtil.readOne
        (
            String.format
            (
                "SELECT count(productID) AS productQty " +
                "FROM roomCatalog WHERE roomID=%s",
                 room.getRoomID()
            )
        ).get("productQty");

        return Math.toIntExact(qty);
    }


    public ArrayList<MsgData> retrieveMsgData()
    {
        ArrayList<MsgData> msgData = new ArrayList<MsgData>();
        int productListQty = getSellingProductQtyFromDB();
        String[] words = content.toUpperCase().split("\\s+");

        for (int i = 0 ; i < words.length-1 ; i++)
        {
            String word = words[i];

            if (word.contains("PRODUCT") && stringContainsNumber(word))
            {

                int productNo = extractNumber(word);
                String next = words[i+1].toUpperCase();

                boolean productNoWithinList = productNo > 0 && productNo <= productListQty;
                boolean quantifiedWithXonly = next.replaceAll("\\d|,", "").equals("X");


                // bug
                //out.println("ProductNoWithinList = " + productNoWithinList);
                //out.println("quantifiedWithXonly = " + quantifiedWithXonly);

                int orderQty = extractNumber(next);
                if (productNoWithinList && quantifiedWithXonly && orderQty > 0)
                {
                    Boolean notAdded = true;

                    for (int j = 0 ; j < msgData.size() ; j++)
                    {
                        MsgData data = msgData.get(j);
                        if (data.productNo == productNo)
                        {
                            data.orderQty += orderQty;
                            notAdded = false;
                            break;
                        }
                    }

                    if (notAdded)
                    {
                        msgData.add(new MsgData(productNo, orderQty));
                    }

                    i++;
                }
            }
        }
        return msgData;
    }
    // endregion


    private boolean stringContainsNumber(String str){
        return str.matches(".*[0-9].*");
    }


    // region 002 : comparable interface
    public int compareTo(Comment comment)
    {
        // hidden problem pk issue (accountID vs username as pk)
        boolean identical =
                (account.getUserName().equals(comment.getAccount().getUserName())) &&
                (commentTime.toString().equals(comment.commentTime.toString())) &&
                (commentDate.toString().equals(comment.commentDate.toString()));
        return identical? 1 : 0;
    }
    // endregion



    // region 003 : getter setter

    // (issue : username VS accountID)

    // public String getAccountID() {return accountID;}
    // public void setAccountID(String accountID) {this.accountID = accountID;}
    public LocalTime getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(LocalTime commentTime) {
        this.commentTime = commentTime;
    }

    public LocalDate getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(LocalDate commentDate) {
        this.commentDate = commentDate;
    }


    // public String getRoomID() { return roomID;}
    //public void setRoomID(String roomID) {this.roomID = roomID;}


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CommentFormatter getFormatter(){ return formatter; }
    // endregion


    public boolean buyerEverMadeOrderFromRoomHost()
    {
        return getCartIDFromDB() != null;
    }


    public void createNewCartWithRoomHost()
    {
        String query = String.format
        (
            """
            insert into cart (buyerID, sellerID, isCheckout)
            values ('%s','%s',0);
            """, account.getAccountID(), room.getSeller().getAccountID()
        );


        // active
        // out.println(query);


        jdbcUtil.executeCUD(query);
    }


    public Integer getCartIDFromDB()
    {
        String query = String.format
        (
           """
           SELECT cartID
           FROM   cart
           WHERE  sellerID='%s' AND buyerID='%s';            
           """,room.getSeller().getAccountID(),account.getAccountID()
        );
        HashMap<String,Object> cartID = jdbcUtil.readOne(query);


        // active
        // out.println(query);


        if (cartID == null)
        {
            return null;
        }
        else
        {
            return (Integer) cartID.get("cartID");
        }
    }


    public void updateCartDetailsInDB(ArrayList<Comment.MsgData> orderData)
    {
        Integer cartID = getCartIDFromDB();
        if (cartID!= null)
        {
            room.setCatalog(room.fetchCatalogFromDB());
            if (!orderData.isEmpty() && account.getIsSeller() == 0)
            {
                for (int i = 0 ; i < orderData.size() ; i++)
                {
                    int productNo = orderData.get(i).getProductNo()-1;
                    int orderQty  = orderData.get(i).getOrderQty();
                    Product product = room.getCatalog().getProductList().get(productNo);


                    // bug
                    // out.println("Catalog size = " + room.getCatalog().getProductList().size());
                    // out.println("Product = " + product.getTitle() + " ProductID=" + product.getProductId());


                    String query =

                    String.format
                    (
                        """
                        insert ignore cartdetails values(%s,%s,%s);
                        """,cartID,product.getProductID(),orderQty
                    );

                    // bug
                    // out.println(query);

                    jdbcUtil.executeCUD(query);
                }
            }
        }
    }

    /*
    * Problem : orderQuantity rules
    *
    *
    * */


    public static class MsgData
    {
        int productNo;
        int orderQty;

        public MsgData(int productNo, int orderQty)
        {
            this.productNo = productNo;
            this.orderQty = orderQty;
        }

        public int getProductNo() {
            return productNo;
        }

        public void setProductNo(int productNo) {
            this.productNo = productNo;
        }

        public int getOrderQty() {
            return orderQty;
        }

        public void setOrderQty(int orderQty) {
            this.orderQty = orderQty;
        }
    }


    public String toString()
    {
        return
                account.toString() + "\n" +
                "commentTime=" + DateTimeUtil.localTimeToString(commentTime) + '\n' +
                ",commentDate=" + DateTimeUtil.localDateToString(commentDate) + '\n' +
                "roomID='" + room.toString() + '\'' + '\n' +
                "content='" + content + '\'' + '\n';
    }


    private int extractNumber(String str){
        return Integer.parseInt(str.replaceAll("[^0-9]", ""));
    }

//    public boolean isOrderComment()
//    {
//
//    }


}
