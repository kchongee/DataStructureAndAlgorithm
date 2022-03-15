package entity;

import javax.swing.*;

public class Review
{
    Account account;
    int rating;
    String reviewMsg;


    // region : Constructors
    public Review(){}

    public Review(int rating, String reviewMsg)
    {
        this.rating = rating;
        this.reviewMsg = reviewMsg;
    }

    public Review(Account account, int rating, String reviewMsg)
    {
        this.account = account;
        this.rating = rating;
        this.reviewMsg = reviewMsg;
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

        panel.add(new JLabel("Rating:"));
        for (JRadioButton btn : buttons){ panel.add(btn); }
        panel.add(new JLabel("<html><br>Review Message:</html>"));
        panel.add(reviewMsgField);
        JOptionPane.showMessageDialog(null, panel, "Review Form", JOptionPane.INFORMATION_MESSAGE);

        int count = 1;
        for (JRadioButton btn : buttons) if (!btn.isSelected()){ count++;}
        return new Review(count, reviewMsgField.getText());
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


    public String toString()
    {
        return "Review{" +
                "account=" + account +
                ", rating=" + rating +
                ", reviewMsg='" + reviewMsg + '\'' +
                '}';
    }


    public static void main(String[] args)
    {
        Review rev = Review.showReviewGrid();
        System.out.println(rev.toString());
    }
}
