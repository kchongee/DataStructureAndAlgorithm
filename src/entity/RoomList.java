package entity;

import UtilityClasses.jdbcUtil;
import adtImplementation.ArrayList;
import adtImplementation.HashMap;

import java.util.Comparator;

public class RoomList
{
    ArrayList<HashMap<String, Object>> roomMap = new ArrayList<HashMap<String, Object>>();
    ArrayList<Room> roomList;


    public RoomList()
    {
        updateData();
    }


    public void updateData()
    {
        fetchActiveRoomsFromDb();
        processIntoRoomList();
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
                    (String) roomMap.get(i).get("roomID"),
                    (String) roomMap.get(i).get("roomTitle"),
                    (Boolean)roomMap.get(i).get("isOpen")
                );

                roomList.add(tempRoom);
            }
        }
    }

    // endregion



    public void displayRoomList()
    {

    }


    public void sortByLikeCount()
    {

    }


    public void sortByReview()
    {

    }


    public void sortByTitle()
    {

    }




    /*
    * Problem : MAX CHAR PROBLEM @ table
    * */
}
