package view.RoomViews;

import UI.CatalogUI;
import UI.LikeUI;
import UI.ReviewUI;
import UtilityClasses.CMD;
import UtilityClasses.DateTimeUtil;
import UtilityClasses.FileUtil;
import adtImplementation.ArrayList;
import application.App;
import entity.*;
import view.RoomListView;


public class BuyerRoomControlView
{
    Catalog catalog;
    public static Room room;
    Account account;
    CatalogUI catalogUI;
    LikeUI likeUI;

    ArrayList<Option> BUYER_CONTROLS = new ArrayList<Option>
            (
                new Option[]
                {
                    new Option("View Catalog",i->showCatalog()),
                    new Option("Help",i->showCommentInstructions()),
                    new Option("Like",i->likeRoom()),
                    new Option("Review", i->reviewRoom()),
                    new Option("Leave Room", i->leaveRoom())
                }
            );

    public BuyerRoomControlView(Catalog catalog, Room room, Account account)
    {
        this.catalog = catalog;
        this.room = room;
        this.account = account;
        this.catalogUI = new CatalogUI(this.catalog);
    }


    // region : options
    public void showCommentInstructions()
    {
        CMD.cls();
        String instructionDoc = FileUtil.readFileFromStringRes("commentInstruction.txt");
        System.out.println(instructionDoc);
        CMD.pauseWithCustomScript("  Press any key to return...");
        CMD.cls();
        BuyerRoomControlView.main(this.account, this.room);
    }

    public void showCatalog()
    {
        catalogUI.displayCatalogViaJPane();
        CMD.cls();
        BuyerRoomControlView.main(this.account, this.room);
    }


    public void leaveRoom()
    {
        CMD.cls();
        App.roomViewExe.terminate();
        RoomListView.main();
    }

    public void likeRoom()
    {
        String likeVal = Like.showOptions();
        System.out.println(likeVal);
        Like like = new Like(account, likeVal, DateTimeUtil.localTimeNow());
        this.likeUI = new LikeUI(like);

        if (like.getValue().equals("LIKE"))
        {
            likeUI.showAppreciate();
        }else if (like.getValue().equals("NO COMMENT"))
        {
            likeUI.showEffort();
        }
        else
        {
            likeUI.showDiscourage();
        }

        int roomID = Integer.parseInt(room.getRoomID());
        if (like.likedBefore(roomID)){
            like.updateDatabase(roomID);
        }
        else
        {
            like.addNewLikeToDB(roomID);
        }
        BuyerRoomControlView.main(this.account, this.room);
    }

    public void reviewRoom()
    {
        Review reviewData = ReviewUI.showReviewGrid();
        Review completeReview = new Review(account, reviewData.getStar(), reviewData.getReviewMsg(), DateTimeUtil.localTimeNow());
        completeReview.sendToDatabase(room);
        CMD.cls();
        BuyerRoomControlView.main(this.account, this.room);
    }

    /**
     * IMPORTANT INFO:<br>
     * 2 MUST Arguments: [1] Account type [2] Room type<br>
     * Account argument should have att "accountID"<br>
     * Room argument should have att "roomID”<br>
     */
    public static void main(Account buyer, Room room)
    {
        BuyerRoomControlView view = new BuyerRoomControlView(room.fetchCatalogFromDB(), room, buyer);
        App.menuHandler(view.BUYER_CONTROLS);
    }
    // endregion

    // test
    public static void main(String[] args)
    {
        CMD.cls();
        main(App.currentUser,App.chosenRoom);
    }
}