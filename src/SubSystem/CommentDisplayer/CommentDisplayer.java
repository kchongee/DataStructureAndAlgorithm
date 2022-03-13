package SubSystem.CommentDisplayer;

import entity.Comment;
import java.util.*;
import static UtilityClasses.jdbcUtil.*;


public class CommentDisplayer
{
    String roomID;
    CommentData commentData;


    // region 001 : constructor
    public CommentDisplayer(String roomID)
    {
        this.roomID = roomID;
        this.commentData = new CommentData();
    }
    // endregion


    // region 002 : public methods
    public Comment fetchLatestCommentFromDB()
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


    // region 003 : utility method

    // endregion

    // region 004 static class
    private class CommentData
    {
        ArrayList<HashMap<String, Object>> commentsMap;
        Queue<Comment> commentQueue;
        Comment latestComment;


        public CommentData(){
            updateData();
        }


        public void updateData()
        {
            fillMap50Comment();
            fillQueue50Comment();
            trackLatestComment();
        }


        // region : utility
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
            // System.out.println(query);
            commentsMap = readAll(query);
        }

        private void trackLatestComment() {
            latestComment = new Comment(commentsMap.get(0));
        }
        // endregion
    }
}

//testers
//class get50LatestCommentFromDB
//{
//    public static void main(String[] args)
//    {
//        CommentDisplayer cd = new CommentDisplayer("roomA");
//        Queue<Comment> cq = cd.getCommentQueueAndUpdateLatestCommentFetched();
//        for (Comment c : cq){
//            System.out.println(c.toString());
//        }
//    }
//}
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