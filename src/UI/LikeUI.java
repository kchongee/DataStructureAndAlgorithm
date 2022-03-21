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
}