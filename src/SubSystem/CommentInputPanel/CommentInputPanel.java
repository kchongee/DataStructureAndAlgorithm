package SubSystem.CommentInputPanel;

import UtilityClasses.DateTimeUtil;
import UtilityClasses.jdbcUtil;
import adtImplementation.ArrayList;
import adtImplementation.HashMap;
import application.App;
import entity.*;

import javax.swing.*;


public class CommentInputPanel implements Launchable
{
    Account account;
    Room room;

    // region 001 : constructors
    CommentInputPanel() {}

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
                    "insert into comment" +
                    targetedField +
                    "values('%s','%s','%s','%s','%s','%s')",
                    account.getAccountID(),
                    comment.getCommentDate(),
                    comment.getCommentTime(),
                    room.getRoomId(),
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
            if (arg.length != 2)
            {
                String errMsg =

                """
                Please provide following arguments:\n
                1. accountID\n
                2. roomID\n
                """;

                throw new IllegalArgumentException(errMsg);
            }
            this.account = new Account(arg[0]);
            this.room = new Room(arg[1]);
        }
    }
    // endregion



    // region : getters setters
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



    public static void main(String[] args)
    {
        Args arg = new Args(args);
        CommentInputPanel cip = new CommentInputPanel(arg.account, arg.room);
        Catalog catalog = cip.getRoom().fetchCatalogFromDB();

        String input = App.promptStringInput(">>> ");

        Comment comment = new Comment(
                cip.account,
                cip.room,
                DateTimeUtil.localTimeNow(),
                DateTimeUtil.localDateNow(),
                input);

        ArrayList<Comment.MsgData> orderData = comment.retrieveMsgData();

        if (!orderData.isEmpty())
        {
            String orderItems = "";
            ArrayList<Product> productList = catalog.getProductList();
            for (int i = 0 ; i < orderData.size() ; i++)
            {
                int productNo = orderData.get(i).getProductNo();
                int orderQty  = orderData.get(i).getOrderQty();
                Product product = catalog.getProductList().get(productNo);
                orderItems = orderItems + product.getTitle() + "x " + orderQty + "\n";
            }

            JOptionPane.showMessageDialog(null, "Your order : \n" + orderItems);
        }
    }


}
