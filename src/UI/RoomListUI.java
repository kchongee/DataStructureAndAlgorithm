package UI;

import SubSystem.RoomListFormatter;
import UtilityClasses.CMD;
import entity.RoomList;

import javax.swing.*;

public class RoomListUI
{
    RoomListFormatter formatter = new RoomListFormatter();
    RoomList roomList;

    public RoomListUI(RoomList roomList) {
        this.roomList = roomList;
    }

    public boolean askSortPreferences()
    {
        boolean ascending = true;
        int reply = 0;
        reply = JOptionPane.showConfirmDialog(null,
                "sort ascending?", "Sorting preference", JOptionPane.YES_NO_OPTION);

        if (reply != JOptionPane.YES_OPTION)
        {
            ascending = false;
        }
        return ascending;
    }

    public void displayRoomList()
    {
        CMD.cls();
        System.out.println(roomList.toString() + formatter.optionPane());
    }
}