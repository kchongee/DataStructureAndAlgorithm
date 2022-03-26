package entity;

import UI.ReviewUI;
import UtilityClasses.jdbcUtil;

import javax.swing.*;
import java.time.LocalTime;

public class Review
{
    private Account account;
    private int star;
    private String reviewMsg;
    private LocalTime revTime;


    // region : Constructors
    public Review(){}

    public Review(Account account) {
        this.account = account;
    }

    public Review(int star, String reviewMsg)
    {
        this.star = star;
        this.reviewMsg = reviewMsg;
    }

    public Review(Account account, int star, String reviewMsg)
    {
        this.account = account;
        this.star = star;
        this.reviewMsg = reviewMsg;
    }

    public Review(Account account, int star, String reviewMsg, LocalTime revTime) {
        this.account = account;
        this.star = star;
        this.reviewMsg = reviewMsg;
        this.revTime = revTime;
    }

    // endregion

    public void sendToDatabase(Room room)
    {
        String query = String.format(
         """
         insert into roomreview (accountID, roomID, star, reviewMsg, revTime) 
         values ('%s',%s,%s,'%s','%s') on duplicate key update star = %s, reviewMsg='%s', revTime='%s';
         """, account.getAccountID(), room.getRoomID(), star, reviewMsg, revTime, star, reviewMsg, revTime
        );


        // active bug
        // System.out.println(query);

        jdbcUtil.executeCUD(query);
    }


    // region : getter setters
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getReviewMsg() {
        return reviewMsg;
    }

    public void setReviewMsg(String reviewMsg) {
        this.reviewMsg = reviewMsg;
    }

    public LocalTime getRevTime() {
        return revTime;
    }

    public void setRevTime(LocalTime revTime) {
        this.revTime = revTime;
    }
    // endregion


    public String toString()
    {
        return "Review{" +
                "account=" + account +
                ", star=" + star +
                ", reviewMsg='" + reviewMsg + '\'' +
                '}';
    }


    public static void main(String[] args)
    {
        Review rev = ReviewUI.showReviewGrid();
        System.out.println(rev);
    }
}

