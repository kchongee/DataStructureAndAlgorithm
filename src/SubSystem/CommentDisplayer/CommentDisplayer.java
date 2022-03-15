package SubSystem.CommentDisplayer;

import UtilityClasses.CMD;
import UtilityClasses.ProjectCompileUtil;
import adtImplementation.ArrayList;
import adtImplementation.CircularQueue;
import adtImplementation.HashMap;
import entity.Account;
import entity.Comment;
import entity.Launchable;
import entity.Room;
import static UtilityClasses.jdbcUtil.*;


public class CommentDisplayer implements Launchable
{
    Room room;
    Account account;
    CommentData commentData;


    // region 001 : constructor
    public CommentDisplayer(){}

    public CommentDisplayer(Room room, Account account)
    {
        this.room = room;
        this.account = account;
        this.commentData = new CommentData();
    }
    // endregion


    // region 002 : public methods
    public void displayComments()
    {
        while (!commentData.commentQueue.isEmpty())
        {
            System.out.println(
                    commentData
                            .commentQueue
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
    public CommentData getCommentData() {
        return commentData;
    }

    public void setCommentData(CommentData commentData) {
        this.commentData = commentData;
    }
    // endregion


    // region 004 inner class
    public class CommentData
    {
        ArrayList<HashMap<String, Object>> commentsMap;
        CircularQueue<Comment> commentQueue;
        Comment latestComment;


        // constructor
        public CommentData()
        {
            commentQueue = new CircularQueue<Comment>(50);
            updateData();
        }


        // region 001 : getter setter
        public ArrayList<HashMap<String, Object>> getCommentsMap() {
            return commentsMap;
        }

        public void setCommentsMap(ArrayList<HashMap<String, Object>> commentsMap) {
            this.commentsMap = commentsMap;
        }

        public CircularQueue<Comment> getCommentQueue() {
            return commentQueue;
        }

        public void setCommentQueue(CircularQueue<Comment> commentQueue) {
            this.commentQueue = commentQueue;
        }

        public Comment getLatestComment() {
            return latestComment;
        }

        public void setLatestComment(Comment latestComment) {
            this.latestComment = latestComment;
        }
        // endregion


        // region 002 : public method
        public void updateData()
        {
            fillMap50Comment();

            // debug
            // System.out.println(commentsMap.toString());

            fillQueue50Comment();
            trackLatestComment();
        }


        public boolean newCommentDetected()
        {
            Comment latestComment = fetchLatestCommentFromDB();
            Comment lastFetchedLatest = commentData.latestComment;
            if (latestComment.compareTo(latestComment) == 1){
                return false;
            }
            return true;
        }
        // endregion


        // region 003 : utility method
        private void fillQueue50Comment()
        {
            for (int i = commentsMap.size()-1 ; i > 0 ; i--)
            {

                /*
                * Problem
                * Discuss   : Nathan
                * Statement :  Account constructor for, accountID,
                *
                */


                /*
                * Problem
                * Discuss : Nathan
                * Statement : request Account schema
                * Data generation
                *
                */


                Account account = new Account();

                account.setUserName((String) commentsMap.get(i).get("username"));
                account.setIsSeller((int) commentsMap.get(i).get("isSeller"));

                Comment tempComment = new Comment(account, room, commentsMap.get(i));

                // debug
                // System.out.println(tempComment.toString());

                commentQueue.add(tempComment);
            }
        }


        /*
         * Problem
         * Discuss : Nathan
         * Statement : Account Table "accountType" field schema
         * Data generation
         *
         */
        private void fillMap50Comment()
        {
            String query =
                    "SELECT acc.accountID, username, accountType, commentDate, commentTime, roomID, content\n" +
                    "FROM Account acc, Comment comm\n" +
                    String.format("WHERE acc.accountID = comm.accountID AND roomID='%s' \n", room.getRoomId()) +
                    "ORDER BY commentSeq desc\n" +
                    "LIMIT 50;\n";

            // debug
            //System.out.println(query);
            commentsMap = readAll(query);

            // debug
            // System.out.println(commentsMap.toString());
        }


        private void trackLatestComment() {
            latestComment = new Comment(account, room, commentsMap.get(0));
        }


        /*
         * Problem
         * Discuss : Nathan
         * Statement : Account Table "accountType" field schema
         * Data generation
         *
         */
        private Comment fetchLatestCommentFromDB()
        {
            String subquery = String.format
                    (
                            "(SELECT max(commentSeq)\n" +
                            "FROM   Comment\n" +
                            "WHERE  roomID='%s')", room.getRoomId()
                    );

            String query = String.format
                    (
                            "SELECT acc.accountID, username, accountType, commentDate, commentTime, roomID, content\n" +
                            "FROM Account acc, Comment comm\n" +
                            "WHERE acc.accountID = comm.accountID AND roomID='%s' AND commentSeq=%s;\n", room.getRoomId(), subquery
                    );

            // debug
            // System.out.println(query);

            return new Comment(account, room, readOne(query));
        }
        // endregion
    }


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
            if (cPlayer.getCommentData().newCommentDetected())
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