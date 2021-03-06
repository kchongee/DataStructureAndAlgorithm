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
            CommentFormatter formatter =
                    new CommentFormatter(CommentQueue
                    .getCommentQueue()
                    .poll());
            System.out.println(formatter.toBlockString());
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
                Please provide following arguments:
                
                1. accountID
                
                2. roomID
                
                """;

                throw new IllegalArgumentException(errMsg);
            }

            this.account = new Account(args[0]);
            this.room = new Room(args[1]);
        }
    }
    // endregion


    /* Arguments required
    * accountID
    * roomID
    */

    // driver
    public static void main(String[] args) throws InterruptedException
    {
        Args arg = null;

        if (args.length != 2)
        {   // arg count not 2 => default arguments
            arg = new Args(new String[]{"A1","1"});
        }
        else
        {   // arg count is 2
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