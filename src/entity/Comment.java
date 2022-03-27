package entity;

import java.time.LocalDate;
import java.time.LocalTime;

import SubSystem.CommentDisplayer.CommentFormatter;
import UtilityClasses.CMD;
import UtilityClasses.DateTimeUtil;
import UtilityClasses.jdbcUtil;
import adtImplementation.ArrayList;
import adtImplementation.HashMap;
import adtInterfaces.ListInterface;
import adtInterfaces.MapInterface;

import javax.swing.*;

import static java.lang.System.out;

public class Comment implements Comparable<Comment> {
    private Account account;
    private Room room;
    private String content;
    private LocalTime commentTime;
    private LocalDate commentDate;

    // region 001 : constructors
    public Comment(Account account, Room room, LocalTime commentTime, LocalDate commentDate, String content) {
        this.account = account;
        this.commentTime = commentTime;
        this.commentDate = commentDate;
        this.room = room;
        this.content = content;
    }

    public Comment(Account account, Room room, MapInterface<String, Object> commentEntry) {
        this.account = account;
        this.room = room;
        this.commentTime = DateTimeUtil.sqlTimeToLocalTime(commentEntry.get("commentTime"));
        this.commentDate = DateTimeUtil.stringObjToLocalDate(commentEntry.get("commentDate"));
        this.content = (String) commentEntry.get("content");
    }
    // endregion


    // region public method

    public int getSellingProductQtyFromDB() {
        Long qty = (Long) jdbcUtil.readOne(String.format("SELECT count(productID) AS productQty " + "FROM roomCatalog WHERE roomID=%s", room.getRoomID())).get("productQty");

        return Math.toIntExact(qty);
    }

    public ListInterface<MsgData> retrieveMsgData() {
        ArrayList<MsgData> msgData = new ArrayList<MsgData>();
        int productListQty = getSellingProductQtyFromDB();
        String[] words = content.toUpperCase().split("\\s+");

        for (int i = 0; i < words.length - 1; i++) {
            String word = words[i];

            if (word.contains("PRODUCT") && stringContainsNumber(word)) {

                int productNo = extractNumber(word);
                String next = words[i + 1].toUpperCase();

                boolean productNoWithinList = productNo > 0 && productNo <= productListQty;
                boolean quantifiedWithXonly = next.replaceAll("\\d|,", "").equals("X");


                // bug
                //out.println("ProductNoWithinList = " + productNoWithinList);
                //out.println("quantifiedWithXonly = " + quantifiedWithXonly);

                int orderQty = extractNumber(next);
                if (productNoWithinList && quantifiedWithXonly && orderQty > 0) {
                    Boolean notAdded = true;

                    for (int j = 0; j < msgData.size(); j++) {
                        MsgData data = msgData.get(j);
                        if (data.productNo == productNo) {
                            data.orderQty += orderQty;
                            notAdded = false;
                            break;
                        }
                    }

                    if (notAdded) {
                        msgData.add(new MsgData(productNo, orderQty));
                    }

                    i++;
                }
            }
        }
        return msgData;
    }

    public boolean buyerEverMadeOrderFromRoomHost() {
        return getCartIDFromDB() != null;
    }

    public void createNewCartWithRoomHost() {
        String query = String.format("""
                insert into cart (buyerID, sellerID, isCheckout)
                values ('%s','%s',0);
                """, account.getAccountID(), room.getSeller().getAccountID());


        // active
        // out.println(query);


        jdbcUtil.executeCUD(query);
    }

    public Integer getCartIDFromDB() {
        String query = String.format("""
                SELECT cartID
                FROM   cart
                WHERE  sellerID='%s' AND buyerID='%s';            
                """, room.getSeller().getAccountID(), account.getAccountID());
        MapInterface<String, Object> cartID = jdbcUtil.readOne(query);


        // active
        // out.println(query);


        if (cartID == null) {
            return null;
        } else {
            return (Integer) cartID.get("cartID");
        }
    }

    public void updateCartDetailsInDB(ListInterface<Comment.MsgData> orderData) {
        Integer cartID = getCartIDFromDB();
        if (cartID != null) {
            room.setCatalog(room.fetchCatalogFromDB());
            if (!orderData.isEmpty() && account.getIsSeller() == 0) {
                for (int i = 0; i < orderData.size(); i++) {
                    int productNo = orderData.get(i).getProductNo() - 1;
                    int orderQty = orderData.get(i).getOrderQty();
                    Product product = room.getCatalog().getProductList().get(productNo);


                    // bug
                    // out.println("Catalog size = " + room.getCatalog().getProductList().size());
                    // out.println("Product = " + product.getTitle() + " ProductID=" + product.getProductId());


                    String query =

                            String.format("""
                                    insert ignore cartdetails values(%s,%s,%s);
                                    """, cartID, product.getProductID(), orderQty);

                    // bug
                    // out.println(query);

                    jdbcUtil.executeCUD(query);
                }
            }
        }
    }

    // endregion


    // region 002 : comparable interface
    public int compareTo(Comment comment) {
        // hidden problem pk issue (accountID vs username as pk)
        boolean identical = (account.getUserName().equals(comment.getAccount().getUserName())) && (commentTime.toString().equals(comment.commentTime.toString())) && (commentDate.toString().equals(comment.commentDate.toString()));
        return identical ? 0 : 1;
    }
    // endregion


    // region 003 : getter setter

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

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


    // endregion


    public static class MsgData {
        private int productNo;
        private int orderQty;

        public MsgData(int productNo, int orderQty) {
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

        @Override
        public String toString() {
            return "MsgData{" +
                    "productNo=" + productNo +
                    ", orderQty=" + orderQty +
                    '}';
        }
    }

    // region : utility methods
    private int extractNumber(String str) {
        return Integer.parseInt(str.replaceAll("[^0-9]", ""));
    }

    private boolean stringContainsNumber(String str) {
        return str.matches(".*[0-9].*");
    }
    // endregion

    public String toString() {
        return account.toString() + "\n" + "commentTime=" + DateTimeUtil.localTimeToString(commentTime) + '\n' + ",commentDate=" + DateTimeUtil.localDateToString(commentDate) + '\n' + "roomID='" + room.toString() + '\'' + '\n' + "content='" + content + '\'' + '\n';
    }

}
