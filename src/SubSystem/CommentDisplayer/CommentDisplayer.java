package SubSystem.CommentDisplayer;

import UtilityClasses.ConsoleFormatter;
import UtilityClasses.ProjectCompileUtil;
import entity.Comment;
import java.util.*;
import static UtilityClasses.jdbcUtil.*;


public class CommentDisplayer
{
    String roomID;
    CommentData commentData;


    // region 001 : constructor
    public CommentDisplayer(){}

    public CommentDisplayer(String roomID)
    {
        this.roomID = roomID;
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
                "WHERE roomID='" + roomID +"';";
        return ((String)readOne(query).get("status")).toUpperCase().equals("ACTIVE");
    }
    // endregion


    // region 003 : getters setters
    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

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
        Queue<Comment> commentQueue;
        Comment latestComment;


        // constructor
        public CommentData()
        {
            commentQueue = new ArrayDeque<Comment>();
            updateData();
        }


        // region 001 : getter setter
        public ArrayList<HashMap<String, Object>> getCommentsMap() {
            return commentsMap;
        }

        public void setCommentsMap(ArrayList<HashMap<String, Object>> commentsMap) {
            this.commentsMap = commentsMap;
        }

        public Queue<Comment> getCommentQueue() {
            return commentQueue;
        }

        public void setCommentQueue(Queue<Comment> commentQueue) {
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
                Comment tempComment = new Comment(commentsMap.get(i));

                // debug
                // System.out.println(tempComment.toString());

                commentQueue.add(tempComment);
            }
        }


        private void fillMap50Comment()
        {
            String query =
                    "SELECT acc.accountID, username, accountType, commentDate, commentTime, roomID, content\n" +
                    "FROM Account acc, Comment comm\n" +
                    String.format("WHERE acc.accountID = comm.accountID AND roomID='%s' \n", roomID) +
                    "ORDER BY commentSeq desc\n" +
                    "LIMIT 50;\n";

            // debug
            //System.out.println(query);
            commentsMap = readAll(query);

            // debug
            // System.out.println(commentsMap.toString());
        }


        private void trackLatestComment() {
            latestComment = new Comment(commentsMap.get(0));
        }


        private Comment fetchLatestCommentFromDB()
        {
            String subquery = String.format
                    (
                            "(SELECT max(commentSeq)\n" +
                            "FROM   Comment\n" +
                            "WHERE  roomID='%s')", roomID
                    );

            String query = String.format
                    (
                            "SELECT acc.accountID, username, accountType, commentDate, commentTime, roomID, content\n" +
                            "FROM Account acc, Comment comm\n" +
                            "WHERE acc.accountID = comm.accountID AND roomID='%s' AND commentSeq=%s;\n", roomID, subquery
                    );

            // debug
            // System.out.println(query);

            return new Comment(readOne(query));
        }
        // endregion
    }
    // endregion

    // driver
    public static void main(String[] args) throws InterruptedException {
        ProjectCompileUtil.compileAndGenerate(new CommentDisplayer());
        String roomID = "";

        if (args.length > 0) {
            roomID = args[0];

            // Debug
            System.out.println(roomID);
        }
        else
        {
            System.out.println("Please pass roomID");
            roomID = "roomA";
        }

        CommentDisplayer cPlayer = new CommentDisplayer(roomID);

        ConsoleFormatter.cls();
        cPlayer.displayComments();

        while (cPlayer.roomActive())
        {
            // debug
            // System.out.println("Loop");

            Thread.sleep(1000);
            if (cPlayer.getCommentData().newCommentDetected())
            {
                // debug
                System.out.println("IN IF");

                ConsoleFormatter.cls();
                cPlayer.displayComments();
            }
        }

        if (!cPlayer.roomActive())
        {
            ConsoleFormatter.cls();
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