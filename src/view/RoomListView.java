package view;

import UI.RoomListUI;
import UtilityClasses.CMD;
import adtImplementation.ArrayList;
import application.App;
import application.Option;
import entity.Room;
import entity.RoomList;
import view.RoomViews.BuyerRoomControlView;
import view.RoomViews.RoomViewExe;

import java.io.FileNotFoundException;

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
                    new Option(i->displayOrderByReview()),
                    new Option(i-> {
                        try {
                            enterRoom();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }),
                    new Option(i->goBack())
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

    public void goBack()
    {
        CMD.cls();
        BuyerHomeView.main();
    }

    public void enterRoom() throws FileNotFoundException {
        while (true)
        {
            int roomNumber = App.promptIntInput("Please select a room >> ");
            Room chosenRoom =roomList.getRoomGivenIndex(roomNumber);

            if (chosenRoom != null)
            {
                App.chosenRoom = chosenRoom;
                RoomViewExe roomViewExe = new RoomViewExe();

                roomViewExe.getCommentShowerShortcut().run();
                roomViewExe.getInputPanelShortcut().run();

                App.roomViewExe = roomViewExe;
                CMD.cls();
                BuyerRoomControlView.main(App.currentUser, chosenRoom);
                return;
            }
            else
            {
                System.out.println("Please enter a valid room");
            }
        }
    }

    public static void main()
    {
        RoomListView view = new RoomListView(new RoomList());
        view.roomListUI.displayRoomList();
        int number = 0;
        while (number != 4)
        {
            number = App.promptIntInput("Select an action >>> ");
            if (number != 4) { CMD.cls();}
            App.goToUserOption(number, view.ROOM_LIST_OPTIONS);
        }
    }

    public static void main(String[] args)
    {
        RoomListView.main();
    }
}
