package entity;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;

import adtImplementation.Account;
// import Customer;
// import adtInterfaces.ArrayList;
import UtilityClasses.DateTimeUtil;
import adtInterfaces.ListInterface;


public class Comment implements Comparable<Comment>
{
    String accountID;
    String username;
    LocalTime commentTime;
    LocalDate commentDate;
    String roomID;
    String content;
    String accountType;

    // region 001 : constructors
    public Comment(String accountID, String username, LocalTime commentTime, LocalDate commentDate, String roomID, String content, String accountType)
    {
        this.accountID = accountID;
        this.username = username;
        this.commentTime = commentTime;
        this.commentDate = commentDate;
        this.roomID = roomID;
        this.content = content;
        this.accountType = accountType;
    }

    public Comment(HashMap<String, Object> commentEntry)
    {
        this.accountID = (String) commentEntry.get("accountID");
        this.username = (String) commentEntry.get("username");
        this.commentTime = DateTimeUtil.sqlTimeToLocalTime(commentEntry.get("commentTime"));
        this.commentDate = DateTimeUtil.stringObjToLocalDate(commentEntry.get("commentDate"));
        this.roomID = (String) commentEntry.get("roomID");
        this.content = (String) commentEntry.get("content");
        this.accountType = (String) commentEntry.get("accountType");
    }
    // endregion


    // region 002 : comparable interface
    public int compareTo(Comment comment)
    {
        boolean identical =
                (accountID.equals(comment.accountID)) &&
                (commentTime.toString().equals(comment.commentTime.toString())) &&
                (commentDate.toString().equals(comment.commentDate.toString()));
        return identical? 1 : 0;
    }
    // endregion


    @Override
    public String toString()
    {
        return
                "accountID='" + accountID + '\'' + "\n\n" +
                "username='" + username + '\'' + '\n' +
                "commentTime=" + DateTimeUtil.localTimeToString(commentTime) + '\n' +
                ",commentDate=" + DateTimeUtil.localDateToString(commentDate) + '\n' +
                "roomID='" + roomID + '\'' + '\n' +
                "content='" + content + '\'' + '\n' +
                "accountType='" + accountType + '\'' +'\n';
    }
}
