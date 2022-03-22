package entity;

import UtilityClasses.DateTimeUtil;
import UtilityClasses.jdbcUtil;

import javax.swing.*;
import java.time.LocalTime;

public class Like
{
    /* Problem
    *  Statement : database accountID
    * */
    Account buyer;
    String value;
    LocalTime likeTime;


    // region : constructor
    Like() {  }

    public Like(Account buyer)
    {
        this.buyer = buyer;
        this.value = "NO COMMENT";
    }

    public Like(Account buyer, String value, LocalTime likeTime) {
        this.buyer = buyer;
        this.value = value;
        this.likeTime = likeTime;
    }

    // endregionss



    // region : public method
    public void like() {
        this.value = "LIKE";
    }

    public void unlike(){
        this.value = "UNLIKE";
    }

    public void noComment(){
        this.value ="NO COMMENT";
    }

    public void addNewLikeToDB(int roomID)
    {
        String query = String.format
        (
                """
                INSERT INTO roomlike VALUES(%s, '%s', '%s', '%s');
                """,roomID, buyer.getAccountID(), this.value, DateTimeUtil.localTimeToString(this.likeTime)
        );

        jdbcUtil.executeCUD(query);
    }


    public void updateDatabase(int roomID)
    {
        String query = String.format
        (
                """
                UPDATE roomlike
                SET    value='%s', likeTime='%s'
                WHERE  accountID ='%s' and roomID = %s;
                """,this.value, DateTimeUtil.localTimeToString(this.likeTime), buyer.getAccountID(), roomID
        );
        jdbcUtil.executeCUD(query);
    }


    public boolean likedBefore(int roomID)
    {
        String query =

                String.format
                (
                         """
                         SELECT accountID
                         FROM  roomlike
                         WHERE accountID='%s' AND roomID=%s;
                         """, buyer.getAccountID(),roomID
                );
        return jdbcUtil.readOne(query) != null;
    }
    // endregion



    // region : getters setters
    public Account getBuyer() {
        return buyer;
    }

    public void setBuyer(Account buyer) {
        this.buyer = buyer;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LocalTime getLikeTime() {
        return likeTime;
    }

    public void setLikeTime(LocalTime likeTime) {
        this.likeTime = likeTime;
    }

    // endregion



    // region : static method
    public static String showOptions()
    {
        final JPanel panel = new JPanel();

        JRadioButton[] buttons = new JRadioButton[]
        {
            new JRadioButton("LIKE"),
            new JRadioButton("NO COMMENT"),
            new JRadioButton("UNLIKE")
        };
        buttons[0].setSelected(true);

        for (JRadioButton btn : buttons){ panel.add(btn); }
        ensureSelectOnlyOne(buttons);

        JOptionPane.showMessageDialog(null, panel, "Like my room", JOptionPane.INFORMATION_MESSAGE);


        for (JRadioButton btn : buttons) {
            if (btn.isSelected()){
                return btn.getText();
            }
        }

        return "NO COMMENT"; // default = like
    }
    // endregion



    // region : ui utility function
    private static void ensureSelectOnlyOne(JRadioButton[]btn)
    {
        for (int i = 0 ; i < btn.length ; i++)
        {
            JRadioButton temp = btn[i];
            int finalI = i;
            temp.addActionListener(e-> onlyThisSelected(finalI, btn));
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



    // region : public static method
    public static String getDiscourageStr() {
        return "We are so sorry about that \uD83D\uDE2D";
    }

    public static String getAppreciateStr() {
        return  "Thanks for liking \uD83D\uDE0A";
    }

    public static String getEffortStr(){
        return "We will do it better \uD83D\uDCAA";
    }

    public static void main(String[] args) {
        System.out.println(Like.showOptions());
    }

    // endregion

}