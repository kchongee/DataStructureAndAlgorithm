package entity;

import javax.swing.*;
import java.awt.event.ActionListener;

public class Like
{
    /* Problem
    *  Statement : database accountID
    * */

    String value;

    public void like() {
        this.value = "LIKE";
    }

    public void unlike(){
        this.value = "UNLIKE";
    }

    public void noComment(){
        this.value ="NO COMMENT";
    }


    // region : static method
    public static int showOptions()
    {
        final JPanel panel = new JPanel();

        JRadioButton[] buttons = new JRadioButton[]
        {
            new JRadioButton("NO COMMENT"),
            new JRadioButton("LIKE"),
            new JRadioButton("UNLIKE")
        };
        buttons[0].setSelected(true);

        for (JRadioButton btn : buttons){ panel.add(btn); }
        ensureSelectOnlyOne(buttons);

        JOptionPane.showMessageDialog(null, panel, "Like my room", JOptionPane.INFORMATION_MESSAGE);

        int count = 1;
        for (JRadioButton btn : buttons) if (btn.isSelected()) { count++;}
        return count; // default = no comment
    };
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
    public static void showDiscourage() {
        JOptionPane.showMessageDialog(null, "We are so sorry about that \uD83D\uDE2D");
    }

    public static void showAppreciate() {
        JOptionPane.showMessageDialog(null, "Thanks for liking \uD83D\uDE0A");
    }

    public static void showEffort(){
        JOptionPane.showMessageDialog(null, "We will do it better \uD83D\uDCAA");
    }

    public static void main(String[] args) {
        System.out.println(Like.showOptions());
    }
    // endregion
}
