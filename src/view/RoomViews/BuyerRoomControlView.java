package view.RoomViews;

import UtilityClasses.CMD;
import UtilityClasses.DateTimeUtil;
import UtilityClasses.FileUtil;
import adtImplementation.ArrayList;
import application.App;
import entity.*;


public class BuyerRoomControlView
{
    Catalog catalog;
    Room room;
    Account account;

    public BuyerRoomControlView(Catalog catalog, Room room, Account account) {
        this.catalog = catalog;
        this.room = room;
        this.account = account;
    }

    ArrayList<Option> BUYER_CONTROLS = new ArrayList<Option>
    (
        new Option[]
            {
                new Option("View Catalog",i->showCatalog()),
                new Option("Help",i->showCommentInstructions()),
                new Option("Like",i->likeRoom()),
                new Option("Review", i->reviewRoom())
            }
    );

    // region : options
    public void showCommentInstructions()
    {
        CMD.cls();
        String instructionDoc = FileUtil.readFileFromStringRes("commentInstruction.txt");
        System.out.println(instructionDoc);
        CMD.pauseWithCustomScript("  Press any key to return...");
        CMD.cls();
        this.main();
    }

    public void showCatalog() {
        catalog.displayCatalogOptionPane();
        CMD.cls();
        this.main();
    }


    public void leaveRoom()
    {

    }

    public void likeRoom()
    {
        String likeVal = Like.showOptions();

        System.out.println(likeVal);

        Like like = new Like(account, likeVal, DateTimeUtil.localTimeNow());
        if (like.getValue().equals("LIKE")){
            Like.showAppreciate();
        }else if (like.getValue().equals("NO COMMENT")){
            Like.showEffort();
        }else {
            Like.showDiscourage();
        }
        int roomID = Integer.parseInt(room.getRoomId());
        if (like.likedBefore(roomID)){
            like.updateDatabase(roomID);
        }else {
            like.addNewLikeToDB(roomID);
        }
        this.main();
    }

    public void reviewRoom(){
        Review reviewData = Review.showReviewGrid();
        Review completeReview = new Review(account, reviewData.getStar(), reviewData.getReviewMsg(), DateTimeUtil.localTimeNow());
        completeReview.sendToDatabase(room);
        this.main();
    }



    public void main() {
        App.menuHandler(BUYER_CONTROLS);
    }
    // endregion



    public static void main(String[] args)
    {
        Room testR = new Room("1");
        Account testA = new Account("A01");
        BuyerRoomControlView view = new BuyerRoomControlView(testR.fetchCatalogFromDB(), testR, testA);
        view.main();
    }
}