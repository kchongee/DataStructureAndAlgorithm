package SubSystem.CommentDisplayer;

import UtilityClasses.CMD;
import UtilityClasses.ProjectCompileUtil;
import entity.*;


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

            this.account = new Account(args[0]);
            this.room = new Room(args[1]);
        }
    }
    // endregion



    // driver
    public static void main(String[] args) throws InterruptedException
    {
        ProjectCompileUtil.compileAndGenerate(new CommentDisplayer());
        Args arg = null;

        if (args.length != 2) {
            arg = new Args(new String[]{"A1","1"});
        }else {
            arg = new Args(args);
        }

        ProjectCompileUtil.compileAndGenerate(new CommentDisplayer());
        CommentDisplayer cPlayer = new CommentDisplayer(arg.room, arg.account);
        CMD.cls();
        cPlayer.displayComments();
        while (true)
        {
            // debug
            // System.out.println("Loop");

            Thread.sleep(1000);
            if (cPlayer.getCommentQueue().newCommentDetected())
            {
                // debug
                //System.out.println("IN IF");

                CMD.cls();
                cPlayer.getCommentQueue().updateData();

                cPlayer.displayComments();
            }
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