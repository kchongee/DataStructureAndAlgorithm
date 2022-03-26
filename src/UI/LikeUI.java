package UI;


import entity.Like;

import javax.swing.*;

public class LikeUI
{
    Like like;

    public LikeUI(Like like) {
        this.like = like;
    }

    public void showDiscourage(){
        JOptionPane.showMessageDialog(null, Like.getDiscourageStr());
    }

    public void showAppreciate(){
        JOptionPane.showMessageDialog(null, Like.getAppreciateStr());
    }

    public void showEffort(){
        JOptionPane.showMessageDialog(null, Like.getEffortStr());
    }

    public static String showOptions() {
        final JPanel panel = new JPanel();

        JRadioButton[] buttons = new JRadioButton[]
                {
                        new JRadioButton("LIKE"),
                        new JRadioButton("NO COMMENT"),
                        new JRadioButton("UNLIKE")
                };
        buttons[0].setSelected(true);

        for (JRadioButton btn : buttons) {
            panel.add(btn);
        }
        ensureSelectOnlyOne(buttons);

        JOptionPane.showMessageDialog(null, panel, "Like my room", JOptionPane.INFORMATION_MESSAGE);


        for (JRadioButton btn : buttons) {
            if (btn.isSelected()) {
                return btn.getText();
            }
        }
        return "NO COMMENT"; // default = like
    }

    private static void ensureSelectOnlyOne(JRadioButton[] btn) {
        for (int i = 0; i < btn.length; i++) {
            JRadioButton temp = btn[i];
            int finalI = i;
            temp.addActionListener(e -> onlyThisSelected(finalI, btn));
        }
    }

    private static void onlyThisSelected(int i, JRadioButton[] btns) {
        int index = 0;
        for (JRadioButton btn : btns) {
            if (index++ != i) {
                btn.setSelected(false);
            }
        }
    }
}