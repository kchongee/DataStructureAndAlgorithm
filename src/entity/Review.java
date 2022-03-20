package entity;

import UtilityClasses.jdbcUtil;

import javax.swing.*;
import java.time.LocalTime;

public class Review
{
    Account account;
    int star;
    String reviewMsg;
    LocalTime revTime;



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



    // region : static method
    public static Review showReviewGrid()
    {
        final JPanel panel = new JPanel();
        panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.Y_AXIS));

        JRadioButton[] buttons = new JRadioButton[]
            {
                    new JRadioButton("1 star"),
                    new JRadioButton("2 star"),
                    new JRadioButton("3 star"),
                    new JRadioButton("4 star"),
                    new JRadioButton("5 star")
            };
        JTextField reviewMsgField = new JTextField();
        buttons[4].setSelected(true);
        ensureSelectOnlyOne(buttons);

        panel.add(new JLabel("star:"));
        for (JRadioButton btn : buttons){ panel.add(btn); }
        panel.add(new JLabel("<html><br>Review Message:</html>"));
        panel.add(reviewMsgField);
        JOptionPane.showMessageDialog(null, panel, "Review Form", JOptionPane.INFORMATION_MESSAGE);

        int count = 1;
        for (JRadioButton btn : buttons) if (!btn.isSelected()){ count++;}
        return new Review(count, reviewMsgField.getText());
    }

    public void sendToDatabase(Room room){
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
    // endregion



    // region : ui utility function
    private static void ensureSelectOnlyOne(JRadioButton[]btns)
    {
        for (int i = 0 ; i < btns.length ; i++)
        {
            JRadioButton temp = btns[i];
            int finalI = i;
            temp.addActionListener(e-> onlyThisSelected(finalI, btns));
        }
    }

    private static void onlyThisSelected(int i, JRadioButton[]btns)
    {
        int index =0;
        for (JRadioButton btn : btns)
        {
            if (index++ != i){
                btn.setSelected(false);
            }
        }
    }
    // endregion



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
        Review rev = Review.showReviewGrid();
        System.out.println(rev.toString());
    }
}
