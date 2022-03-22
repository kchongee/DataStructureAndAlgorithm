package SubSystem.CommentInput;

import UtilityClasses.CMD;
import UtilityClasses.DateTimeUtil;
import UtilityClasses.ProjectCompileUtil;
import UtilityClasses.jdbcUtil;
import adtImplementation.ArrayList;
import application.App;
import entity.*;
import javax.swing.*;

public class CommentInputPanel implements Launchable
{
    Account account;
    Room room;


    // region 001 : constructors
    CommentInputPanel()
    {
        this.account = null;
        this.room = null;
    }

    CommentInputPanel(Account account, Room room)
    {
        this.account = account;
        this.room = room;
    }
    // endregion



    // region 002: public method
    public void sendCommentToDatabase(Comment comment)
    {
        String targetedField =
        " (accountID, commentDate, commentTime, roomID, content, isOrder) ";

        String query =
        String.format
        (
            "insert into comment" + targetedField +
            "values('%s','%s','%s','%s','%s',%s)",
            account.getAccountID(),
            DateTimeUtil.localDateToString(comment.getCommentDate()),
            DateTimeUtil.localTimeToString(comment.getCommentTime()),
            room.getRoomID(),
            comment.getContent(),
                comment.retrieveMsgData().isEmpty()
        );

        jdbcUtil.executeCUD(query);
    }
    // endregion



    // region 003: static class
    private static class Args
    {
        Account account;
        Room room;

        Args(String[]arg)
        {
            if (arg.length != 4)
            {
                String errMsg =

                """
                Please provide following arguments:
                1. accountID
                2. isSeller
                3. roomID
                4. sellerID
                """;

                throw new IllegalArgumentException(errMsg);
            }
            this.account = new Account(arg[0], Integer.parseInt(arg[1]));
            this.room = new Room(arg[2]);
            this.room.setSeller(new Seller(new Account(arg[3])));
        }
    }
    // endregion



    // region 004 : getters setters
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
    // endregion


    /** Args
    -------------
    1. accountID
    2. isSeller
    3. roomID
    4. sellerID
    -----------
    */
    public static void main(String[] args)
    {
        CMD.cls();
        Args arg = null;

        if (args.length != 4)
        {   // args invalid = > use default args
            System.out.println("args invalid, applying default args");
            CMD.pauseWithCustomScript("Press any key to continue");
            arg = new Args(new String[]{"A01","1","1","A15"});
        }
        else
        {   // args valid
            arg = new Args(args);
        }

        CommentInputPanel cip = new CommentInputPanel(arg.account, arg.room);
        Catalog catalog = cip.getRoom().fetchCatalogFromDB();

        while (true)
        {
            CMD.cls();
            String input = App.promptStringInput(">>> ");

            Comment comment = new Comment
            (
                cip.account,
                cip.room,
                DateTimeUtil.localTimeNow(),
                DateTimeUtil.localDateNow(),
                input
            );

            ArrayList<Comment.MsgData> orderData = comment.retrieveMsgData();

            if (!orderData.isEmpty() && arg.account.getIsSeller() == 0)
            {
                String orderItems = "";

                for (int i = 0 ; i < orderData.size() ; i++)
                {
                    int productNo = orderData.get(i).getProductNo()-1;
                    int orderQty  = orderData.get(i).getOrderQty();
                    Product product = catalog.getProductList().get(productNo);
                    orderItems = orderItems + product.getTitle() + "x " + orderQty + "\n";
                }

                JOptionPane.showMessageDialog(null, "Your order : \n" + orderItems);

                if (comment.buyerEverMadeOrderFromRoomHost())
                {
                    comment.updateCartDetailsInDB(orderData);
                }
                else
                {
                    comment.createNewCartWithRoomHost();
                    comment.updateCartDetailsInDB(orderData);
                }
            }

            cip.sendCommentToDatabase(comment);
        }
    }
}
