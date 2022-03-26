package entity;

import SubSystem.RoomListFormatter;
import UI.RoomListUI;
import UtilityClasses.SortingAlgorithm.BubbleSort;
import UtilityClasses.jdbcUtil;
import adtImplementation.ArrayList;
import adtImplementation.LinkedHashMap;
import adtInterfaces.ListInterface;
import adtInterfaces.MapInterface;


public class RoomList {
    private ListInterface<MapInterface<String, Object>> roomMap = new ArrayList<>();
    private ListInterface<Room> roomList = new ArrayList<Room>();

    public RoomList() {
        updateData();
    }


    // region : public methods

    public void updateData() {
        fetchActiveRoomsFromDb();
        processIntoRoomList();
        iterateUpdateLikeReview();
    }

    public void iterateUpdateLikeReview() {
        fetchActiveRoomsFromDb();
        processIntoRoomList();
        for (int i = 0; i < roomList.size(); i++) {
            roomList.get(i).getReviewList().updateData();
            roomList.get(i).getLikeList().updateData();
        }
    }

    public void sortByLikeCount(Boolean ascending) {
        BubbleSort<LikeList> sorter = new BubbleSort<LikeList>(extractLikeList());

        LikeList[] sorted = sorter.sort(ascending);
        this.roomList.clear();

        for (LikeList likeList : sorted) {
            roomList.add(likeList.getRoom());
        }
    }

    public void sortByReview(Boolean ascending) {
        BubbleSort<ReviewList> sorter = new BubbleSort<ReviewList>(extractReviewList());
        ReviewList[] sorted = sorter.sort(ascending);
        this.roomList.clear();
        for (ReviewList revList : sorted) {
            roomList.add(revList.getRoom());
        }
    }

    public void sortByTitle(Boolean ascending) {
        TitleSort titleSorter = new TitleSort(roomList);
        this.roomList = titleSorter.sort(ascending);
    }

    public LikeList[] extractLikeList() {

        LikeList[] likeLists = new LikeList[roomList.size()];
        for (int i = 0; i < roomList.size(); i++) {
            Room tempRoom = roomList.get(i);
            likeLists[i] = tempRoom.getLikeList();
        }
        return likeLists;
    }

    public ReviewList[] extractReviewList() {
        ReviewList[] revLists = new ReviewList[roomList.size()];
        for (int i = 0; i < roomList.size(); i++) {
            Room tempRoom = roomList.get(i);
            revLists[i] = tempRoom.getReviewList();
        }
        return revLists;
    }

    public Room getRoomGivenIndex(int index) {
        int roomQty = roomList.size();
        if (index > 0 && index <= roomQty) {
            return roomList.get(index - 1);
        } else {
            return null;
        }
    }

    // endregion


    // region : utility

    private void fetchActiveRoomsFromDb() {
        roomMap = jdbcUtil.readAll(String.format("""
                SELECT *
                FROM   room
                WHERE  isOpen=true;
                """));
    }

    private void processIntoRoomList() {
        if (!roomMap.isEmpty()) {
            roomList.clear();
            for (int i = 0; i < roomMap.size(); i++) {
                Room tempRoom = new Room(Integer.toString((Integer) roomMap.get(i).get("roomID")),
                        (String) roomMap.get(i).get("roomTitle"), (Boolean) roomMap.get(i).get("isOpen"),
                        new Seller((String) roomMap.get(i).get("accountID")));
                roomList.add(tempRoom);
            }
        }
    }

    // endregion


    // region : getter setters
    public ListInterface<MapInterface<String, Object>> getRoomMap() {
        return roomMap;
    }

    public void setRoomMap(ListInterface<MapInterface<String, Object>> roomMap) {
        this.roomMap = roomMap;
    }

    public ListInterface<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(ArrayList<Room> roomList) {
        this.roomList = roomList;
    }
    // endregion

    public String toString() {
        RoomListFormatter formatter = new RoomListFormatter();
        String roomListStr = "";
        roomListStr += formatter.strTableHead() + "\n";

        if (!roomList.isEmpty()) {
            for (int i = 0; i < roomList.size(); i++)
                roomListStr += formatter.toRow(i + 1, roomList.get(i)) + "\n";

            roomListStr += formatter.lineStr() + "\n";
        }
        return roomListStr;
    }

    private static class TitleSort {
        ListInterface<Room> roomList;
        MapInterface<String, Room> titleToRoom;
        String[] roomTitles;


        TitleSort(ListInterface<Room> roomList) {
            this.roomTitles = new String[roomList.size()];
            this.titleToRoom = new LinkedHashMap<>();
            this.roomList = roomList;
        }


        public ListInterface<Room> sort(Boolean ascending) {
            mapTitleToRoomAndGetTitleArray();
            sortTitleArray(ascending);
            sortRoomList();
            return roomList;
        }


        // region : utility
        private void mapTitleToRoomAndGetTitleArray() {
            for (int i = 0; i < roomList.size(); i++) {
                Room tempRoom = roomList.get(i);
                roomTitles[i] = tempRoom.getRoomTitle();
                titleToRoom.put(tempRoom.getRoomTitle(), tempRoom);
            }
        }

        private void sortTitleArray(Boolean ascending) {
            BubbleSort<String> sorter = new BubbleSort<>(roomTitles);
            roomTitles = sorter.sort(ascending);
        }

        private void sortRoomList() {
            Room[] sortedRoomList = new Room[roomList.size()];
            for (int i = 0; i < roomList.size(); i++) {
                sortedRoomList[i] = titleToRoom.get(roomTitles[i]);
            }
            this.roomList = new ArrayList<Room>(sortedRoomList);
        }
        // endregion


    }

    public static void main(String[] args) {
        RoomList list = new RoomList();
        RoomListUI ui = new RoomListUI(list);
        list.sortByTitle(true);
        ui.askSortPreferences();
    }
}
