package entity;

import UtilityClasses.jdbcUtil;
import adtImplementation.ArrayList;
import adtImplementation.CircularQueue;
import adtImplementation.HashMap;

import static UtilityClasses.jdbcUtil.readAll;
import static UtilityClasses.jdbcUtil.readOne;

public class CommentQueue
{
    ArrayList<HashMap<String, Object>> commentsMap;
    CircularQueue<Comment> commentQueue;
    Comment latestComment;
    Room room;


    // constructor
    public CommentQueue(Room room)
    {
        this.room = room;
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

        HashMap<String, Object> firstAcc = commentsMap.get(0);

        /*
        * Problem
        * Statement : account obj info concern + acctype
        * */

        Account acc = new Account((String)firstAcc.get("accountID"), (String)firstAcc.get("username"));

        trackLatestComment(acc);
    }


    public boolean newCommentDetected()
    {
        Comment latestComment = fetchLatestCommentFromDB();
        Comment lastFetchedLatest = this.latestComment;
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


    private void trackLatestComment(Account account) {
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

        /*
        * Problem
        * Statement : schema prob
        * */


        HashMap<String,Object> data = readOne(query);
        Account account = new Account((String)data.get("accountID"), (String)data.get("username"));
        return new Comment(account, room, readOne(query));
    }
    // endregion
}
