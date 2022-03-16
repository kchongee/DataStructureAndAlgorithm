package SubSystem.CommentDisplayer;

import UtilityClasses.CMD;
import UtilityClasses.ProjectCompileUtil;
import adtImplementation.ArrayList;
import adtImplementation.CircularQueue;
import adtImplementation.HashMap;
import entity.*;

import static UtilityClasses.jdbcUtil.*;


public class CommentDisplayer implements Launchable
{
    Room room;
    Account account;
    CommentQueue CommentQueue;


    // region 001 : constructor
    public CommentDisplayer(){}

    public CommentDisplayer(Room room, Account account)
    {
        this.room = room;
        this.account = account;
        this.CommentQueue = new CommentQueue(room);
    }
    // endregion


    // region 002 : public methods
    public void displayComments()
    {
        while (!CommentQueue.getCommentQueue().isEmpty())
        {
            System.out.println(
                    CommentQueue.getCommentQueue()
                            .poll()
                            .getFormatter()
                            .toBlockString()
            );
        }
    }

    public boolean roomActive()
    {
        String query =
                "SELECT status\n"   +
                "FROM RoomStatus\n" +
                "WHERE roomID='" + room.getRoomId() +"';";
        return ((String)readOne(query).get("status")).toUpperCase().equals("ACTIVE");
    }
    // endregion


    // region 003 : getters setters
    public entity.CommentQueue getCommentQueue() {
        return CommentQueue;
    }

    public void setCommentQueue(CommentQueue CommentQueue) {
        this.CommentQueue = CommentQueue;
    }
    // endregion


    // region 004 inner class



    private static class Args
    {
        Account account;
        Room room;

        Args(String[]args)
        {
            if (args.length != 2)
            {
                String errMsg =

                """
                Please provide following arguments:\n
                1. accountID\n
                2. roomID\n
                """;

                throw new IllegalArgumentException(errMsg);
            }


            /*
             * Problem
             * Discuss : Nathan && ChongEE
             * Statement : Constructor for account of (AccountID, username)
             *             Constructor for seller of(sellerID) only
             *
             */
            this.account = new Account();
            this.room = new Room();
            account.setAccountID(args[0]);
            room.setRoomId(args[1]);
        }
    }
    // endregion



    // driver
    public static void main(String[] args) throws InterruptedException
    {
        ProjectCompileUtil.compileAndGenerate(new CommentDisplayer());
        Args arg = new Args(args);
        CommentDisplayer cPlayer = new CommentDisplayer(arg.room, arg.account);
        CMD.cls();
        cPlayer.displayComments();
        while (cPlayer.roomActive())
        {

            // debug
            // System.out.println("Loop");

            Thread.sleep(1000);
            if (cPlayer.getCommentQueue().newCommentDetected())
            {

                // debug
                //System.out.println("IN IF");

                CMD.cls();
                cPlayer.displayComments();
            }
        }

        if (!cPlayer.roomActive())
        {
            CMD.cls();
            System.out.println("Session ended");
        }
    }
}



//
//
//class getLatestComment
//{
//    public static void main(String[] args)
//    {
//        CommentDisplayer cd = new CommentDisplayer("roomA");
//        System.out.println(cd.latestCommentFetched.toString());
//        System.out.println(cd.fetchLatestCommentFromDB().toString());
//    }
//}
//
//
//class compareCommentTest
//{
//    public static void main(String[] args)
//    {
//        CommentDisplayer cd = new CommentDisplayer("roomA");
//        Comment a = cd.latestCommentFetched;
//        Comment b = cd.fetchLatestCommentFromDB();
//        System.out.println(a.compareTo(b));
//    }
//}