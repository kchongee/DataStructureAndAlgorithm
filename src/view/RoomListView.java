package view;

import UtilityClasses.CMD;
import adtImplementation.ArrayList;
import application.App;
import entity.Option;
import entity.RoomList;

public class RoomListView
{
    RoomList roomList;

    public RoomListView(RoomList roomList) {
        this.roomList = roomList;
    }

    ArrayList<Option> ROOM_LIST_OPTIONS = new ArrayList<Option>
    (
            new Option[]
            {
                    new Option(i->displayOrderByTitle()),
                    new Option(i->displayOrderByLike()),
                    new Option(i->displayOrderByReview())
            }
    );

    public void displayOrderByTitle()
    {
        CMD.cls();
        roomList.sortByTitle(roomList.askSortPreferences());
        roomList.displayRoomList();
    }

    public void displayOrderByLike()
    {
        CMD.cls();
        roomList.sortByLikeCount(roomList.askSortPreferences());
        roomList.displayRoomList();
    }

    public void displayOrderByReview()
    {
        CMD.cls();
        roomList.sortByReview(roomList.askSortPreferences());
        roomList.displayRoomList();
    }

    public static void main()
    {
        RoomListView view = new RoomListView(new RoomList());
        view.roomList.displayRoomList();
        int number = 0;
        while (number != 4)
        {
            number = App.promptIntInput("Select an action >>> ");
            CMD.cls();
            App.goToUserOption(number, view.ROOM_LIST_OPTIONS);
        }
    }

    public static void main(String[] args) {
        RoomListView.main();
    }
}
