package UI;

import entity.Review;

import javax.swing.*;

public class ReviewUI
{
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
}