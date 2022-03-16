package entity;
import java.time.LocalDate;
import java.time.LocalTime;
import SubSystem.CommentDisplayer.CommentFormatter;
import UtilityClasses.DateTimeUtil;
import adtImplementation.HashMap;


public class Comment implements Comparable<Comment>
{
    Account account;
    Room room;
    String username, content, accountType;
    LocalTime commentTime;
    LocalDate commentDate;
    CommentFormatter formatter;


    // region 001 : constructors
    public Comment(Account account, Room room, LocalTime commentTime, LocalDate commentDate, String content)
    {
        this.account = account;
        this.commentTime = commentTime;
        this.commentDate = commentDate;
        this.room = room;
        this.content = content;
        this.formatter = new CommentFormatter(this);
    }

    public Comment(Account account, Room room, HashMap<String, Object> commentEntry)
    {
        this.account = account;
        this.room = room;
        this.commentTime = DateTimeUtil.sqlTimeToLocalTime(commentEntry.get("commentTime"));
        this.commentDate = DateTimeUtil.stringObjToLocalDate(commentEntry.get("commentDate"));
        this.content = (String) commentEntry.get("content");
        this.formatter = new CommentFormatter(this);
    }
    // endregion


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

    public void setFormatter(CommentFormatter formatter) {
        this.formatter = formatter;
    }

    // region 002 : comparable interface
    public int compareTo(Comment comment)
    {
        // hidden problem pk issue (accountID vs username as pk)
        boolean identical =
                (account.getUserName().equals(comment.getAccount().getUserName())) &&
                (commentTime.toString().equals(comment.commentTime.toString())) &&
                (commentDate.toString().equals(comment.commentDate.toString()));
        return identical? 1 : 0;
    }
    // endregion


    // region 003 : getter setter

    // (issue : username VS accountID)

    // public String getAccountID() {return accountID;}
    // public void setAccountID(String accountID) {this.accountID = accountID;}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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


    // public String getRoomID() { return roomID;}
    //public void setRoomID(String roomID) {this.roomID = roomID;}


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public CommentFormatter getFormatter(){ return formatter; }
    // endregion


    @Override
    public String toString()
    {
        return
                account.toString() + "\n" +
                "username='" + username + '\'' + '\n' +
                "commentTime=" + DateTimeUtil.localTimeToString(commentTime) + '\n' +
                ",commentDate=" + DateTimeUtil.localDateToString(commentDate) + '\n' +
                "roomID='" + room.toString() + '\'' + '\n' +
                "content='" + content + '\'' + '\n' +
                "accountType='" + accountType + '\'' +'\n';
    }


//    public boolean isOrderComment()
//    {
//
//    }


}
