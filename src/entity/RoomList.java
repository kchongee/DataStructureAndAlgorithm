package entity;

import SubSystem.RoomListFormatter;
import UtilityClasses.CMD;
import UtilityClasses.SortingAlgorithm.BubbleSort;
import UtilityClasses.jdbcUtil;
import adtImplementation.ArrayList;
import adtImplementation.HashMap;

import javax.swing.*;


public class RoomList
{
    ArrayList<HashMap<String, Object>> roomMap = new ArrayList<HashMap<String, Object>>();
    ArrayList<Room> roomList = new ArrayList<Room>();


    public RoomList() {
        updateData();
    }


    public void updateData()
    {
        fetchActiveRoomsFromDb();
        processIntoRoomList();
        updateDataAlongLikeAndRevList();
    }


    public void updateDataAlongLikeAndRevList()
    {
        fetchActiveRoomsFromDb();
        processIntoRoomList();
        for (int i = 0 ; i < roomList.size() ; i++ )
        {
            roomList.get(i).getReviewList().updateData();
            roomList.get(i).getLikeList().updateData();
        }
    }


    // region : utility
    private void fetchActiveRoomsFromDb()
    {
        roomMap = jdbcUtil.readAll
        (
                String.format
                (
                        """
                        SELECT *
                        FROM   room
                        WHERE  isOpen=true;
                        """
                )
        );
    }


    private void processIntoRoomList()
    {
        if (!roomMap.isEmpty())
        {
            roomList.clear();
            for (int i = 0 ; i < roomMap.size() ; i++)
            {
                Room tempRoom = new Room
                (
                    Integer.toString((Integer) roomMap.get(i).get("roomID")),
                    (String) roomMap.get(i).get("roomTitle"),
                    (Boolean)roomMap.get(i).get("isOpen")
                );

                // debug
                // System.out.println(tempRoom.toString());

                roomList.add(tempRoom);
            }
        }
    }

    // endregion



    public void displayRoomList()
    {
        CMD.cls();
        RoomListFormatter formatter = new RoomListFormatter();
        System.out.println(formatter.strTableHead());
        if (!roomList.isEmpty())
        {
            for(int i = 0 ; i < roomList.size() ; i++)
                System.out.println(formatter.toRow(i+1, roomList.get(i)));
            System.out.println(formatter.lineStr());
            System.out.println(formatter.optionPane());
        }
    }

    public void sortByLikeCount(Boolean ascending)
    {


        BubbleSort<LikeList> sorter = new BubbleSort<LikeList>(extractLikeList());

        LikeList[] sorted = sorter.sort(ascending);
        this.roomList.clear();

        for (LikeList likeList : sorted) {
            roomList.add(likeList.room);
        }
    }


    public void sortByReview(Boolean ascending)
    {

        BubbleSort<ReviewList> sorter = new BubbleSort<ReviewList>(extractReviewList());

        ReviewList[] sorted = sorter.sort(ascending);
        this.roomList.clear();
        for (ReviewList revList : sorted) {
            roomList.add(revList.getRoom());
        }
    }


    public LikeList[] extractLikeList()
    {
        LikeList[] likeLists = new LikeList[roomList.size()];

        for (int i = 0 ; i < roomList.size() ; i++)
        {
            Room tempRoom = roomList.get(i);
            likeLists[i] = tempRoom.getLikeList();
        }

        return likeLists;
    }


    public ReviewList[] extractReviewList()
    {
        ReviewList[] revLists = new ReviewList[roomList.size()];
        for (int i = 0 ; i < roomList.size() ; i++)
        {
            Room tempRoom = roomList.get(i);
            revLists[i] = tempRoom.getReviewList();
        }
        return revLists;
    }


    public void sortByTitle(Boolean ascending)
    {
        TitleSort titleSorter = new TitleSort(roomList);
        this.roomList = titleSorter.sort(ascending);
    }


    private static class TitleSort
    {
        ArrayList<Room> roomList;
        HashMap<String, Room> titleToRoom;
        String[] roomTitles;


        TitleSort(ArrayList<Room> roomList)
        {
            this.roomTitles = new String[roomList.size()];
            this.titleToRoom = new HashMap<String, Room>();
            this.roomList = roomList;
        }


        public ArrayList<Room> sort(Boolean ascending)
        {
            initMapAndUnsortedTitles();
            processToSortedTites(ascending);
            processToSortedRoomArraylist();
            return roomList;
        }



        // region : utility
        private void initMapAndUnsortedTitles()
        {
            for (int i = 0 ; i < roomList.size() ; i++)
            {
                Room tempRoom =  roomList.get(i);
                roomTitles[i] = tempRoom.getRoomTitle();
                titleToRoom.put(tempRoom.getRoomTitle(), tempRoom);
            }
        }

        private void processToSortedTites(Boolean ascending)
        {
            BubbleSort<String> sorter = new BubbleSort<String>(roomTitles);
            roomTitles = sorter.sort(ascending);
        }

        private void processToSortedRoomArraylist()
        {
            Room[] sortedRoomList = new Room[roomList.size()];
            for (int i = 0 ; i < roomList.size() ; i++) {
                sortedRoomList[i] = titleToRoom.get(roomTitles[i]);
            }
            this.roomList = new ArrayList<Room>(sortedRoomList);
        }
        // endregion


    }


    private boolean askSortPreferences()
    {
        boolean ascending = true;
        int reply = 0;
        reply =JOptionPane.showConfirmDialog(null,
                "sort ascending?", "Sorting preference", JOptionPane.YES_NO_OPTION);
        if (reply != JOptionPane.YES_OPTION){
            ascending = false;
        }
        return ascending;
    }
    /*
    * Problem : MAX CHAR PROBLEM @ table
    * */


    public static void main(String[] args)
    {
        RoomList list = new RoomList();
        list.sortByTitle(true);
        list.displayRoomList();
    }
}
