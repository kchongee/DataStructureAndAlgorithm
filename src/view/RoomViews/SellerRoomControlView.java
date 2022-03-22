package view.RoomViews;

import UtilityClasses.CMD;
import UtilityClasses.jdbcUtil;
import adtImplementation.ArrayList;
import application.App;
import entity.LikeList;
import entity.Option;
import entity.ReviewList;
import entity.Room;
import view.SellerHomeView;

import static java.lang.System.out;

public class SellerRoomControlView
{
    Room room;
    ArrayList<Option> OPTIONS = new ArrayList<>(
            new Option[]
                    {
                        new Option("view like",i->viewLike()),
                        new Option("view reviews",i->viewReviews()),
                        new Option("end session",i->endRoom())
                    }
    );

    public SellerRoomControlView(Room room)
    {
        this.room = room;
    }

    public void endRoom()
    {
        jdbcUtil.executeCUD("update room set isOpen=0 where roomID='"+room.getRoomID()+"';");
        App.roomViewExe.terminate();
        CMD.cls();
        SellerHomeView.main();
    }

    public void viewLike()
    {
        CMD.cls();
        LikeList list = new LikeList(room);
        list.syncLikeDataFromDb();
        out.println(list);
        CMD.pauseWithCustomScript("press any key to return...");
        this.main();
    }

    public void viewReviews()
    {
        CMD.cls();
        ReviewList list = new ReviewList(room);
        list.syncReviewDataFromDb();
        out.println(list);
        CMD.pauseWithCustomScript("press any key to return...");
        this.main();
    }

    public void main() {
        SellerRoomControlView.main(new String[]{});
    }

    public static void main(String[] args)
    {
        CMD.cls();
        int roomID = 1;
        if (App.sellerCreatedRoomID == 0)
        {
            roomID = 1;
        }
        else
        {
            roomID = App.sellerCreatedRoomID;
        }
        out.println(roomID);
        SellerRoomControlView view = new SellerRoomControlView(new Room(String.valueOf(roomID)));
        App.menuHandler(view.OPTIONS);
    }

}
