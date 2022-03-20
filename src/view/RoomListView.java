package view;

import UI.RoomListUI;
import UtilityClasses.CMD;
import adtImplementation.ArrayList;
import application.App;
import entity.Option;
import entity.RoomList;

public class RoomListView
{
    RoomList roomList;
    RoomListUI roomListUI;


    ArrayList<Option> ROOM_LIST_OPTIONS = new ArrayList<Option>
    (
            new Option[]
            {
                    new Option(i->displayOrderByTitle()),
                    new Option(i->displayOrderByLike()),
                    new Option(i->displayOrderByReview())
            }
    );

    public RoomListView(RoomList roomList)
    {
        this.roomList = roomList;
        this.roomListUI = new RoomListUI(roomList);
    }


    public void displayOrderByTitle()
    {
        CMD.cls();
        roomList.sortByTitle(roomListUI.askSortPreferences());
        roomListUI.displayRoomList();
    }

    public void displayOrderByLike()
    {
        CMD.cls();
        roomList.sortByLikeCount(roomListUI.askSortPreferences());
        roomListUI.displayRoomList();
    }

    public void displayOrderByReview()
    {
        CMD.cls();
        roomList.sortByReview(roomListUI.askSortPreferences());
        roomListUI.displayRoomList();
    }

    public static void main()
    {
        RoomListView view = new RoomListView(new RoomList());
        view.roomListUI.displayRoomList();
        int number = 0;
        while (number != 4)
        {
            number = App.promptIntInput("Select an action >>> ");
            CMD.cls();
            App.goToUserOption(number, view.ROOM_LIST_OPTIONS);
        }
    }

    public static void main(String[] args)
    {
        RoomListView.main();
    }
}
