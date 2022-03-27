package entity;

import UtilityClasses.DateTimeUtil;
import UtilityClasses.jdbcUtil;
import adtImplementation.ArrayList;
import adtImplementation.HashMap;
import adtImplementation.LinkedHashMap;
import adtInterfaces.ListInterface;
import adtInterfaces.MapInterface;

public class LikeList implements Comparable<LikeList> {
    private Room room;
    private MapInterface<String, ArrayList<Like>> classifiedLike = new LinkedHashMap<>();
    private ListInterface<MapInterface<String, Object>> likeDBdata = new ArrayList<>();


    // region : constructors
    public LikeList(Room room) {
        this.room = room;
        updateData();
    }
    // endregion


    // region : public method
    public void updateData() {
        syncLikeDataFromDb();
        classifyLike();
    }

    public int getLikeQty() {
        return classifiedLike.get("LIKE").size();
    }

    public int getUnlikeQty() {
        return classifiedLike.get("UNLIKE").size();
    }

    public int getNoCommentQty() {
        return classifiedLike.get("NO COMMENT").size();
    }

    public String getLikeString() {
        int likeQty = getLikeQty();
        int neutralQty = getNoCommentQty();
        int unlikeQty = getUnlikeQty();
        return String.format("%d\uD83D\uDC4D %d\uD83D\uDC4E %d\uD83D\uDE10", likeQty, unlikeQty, neutralQty);
    }

    public void syncLikeDataFromDb() {
        String query = String.format(

                """
                        SELECT l.accountID, value, acc.isSeller, likeTime, acc.userName
                        FROM   roomlike l, account acc
                        WHERE  roomID=%s AND l.accountID = acc.accountID;
                        """, room.getRoomID());

        likeDBdata = jdbcUtil.readAll(query);
    }
    // endregion


    // region : utility method
    private void classifyLike() {
        reinitializeClassifiedLike();
        if (!likeDBdata.isEmpty()) {
            for (int i = 0; i < likeDBdata.size(); i++) {
                MapInterface<String, Object> tempMap = likeDBdata.get(i);

                Account tempAcc = new Account(
                        (String) tempMap.get("accountID"),
                        (String) tempMap.get("userName"),
                        (Integer) tempMap.get("isSeller")
                );

                Like tempLike = new Like(
                        tempAcc,
                        (String) tempMap.get("value"),
                        DateTimeUtil.sqlTimeToLocalTime(tempMap.get("likeTime"))
                );

                classifiedLike.get(tempLike.getValue()).add(tempLike);
            }
        }
    }

    private void reinitializeClassifiedLike() {
        classifiedLike = new HashMap<>();
        classifiedLike.put("LIKE", new ArrayList<>());
        classifiedLike.put("NO COMMENT", new ArrayList<>());
        classifiedLike.put("UNLIKE", new ArrayList<>());
    }

    public String toString() {
        syncLikeDataFromDb();
        classifyLike();
        return String.format("""
                👍 %s
                👎 %s
                """, classifiedLike.get("LIKE").size(), classifiedLike.get("UNLIKE").size());
    }

    public int compareTo(LikeList otherList) {
        if (this.getLikeQty() > otherList.getLikeQty()) {
            return 1;
        } else if (this.getLikeQty() == otherList.getLikeQty()) {
            return 0;
        } else {
            return -1;
        }
    }
    // endregion


    //region : getter setter
    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public MapInterface<String, ArrayList<Like>> getClassifiedLike() {
        return classifiedLike;
    }

    public void setClassifiedLike(MapInterface<String, ArrayList<Like>> classifiedLike) {
        this.classifiedLike = classifiedLike;
    }

    public ListInterface<MapInterface<String, Object>> getLikeDBdata() {
        return likeDBdata;
    }

    public void setLikeDBdata(ListInterface<MapInterface<String, Object>> likeDBdata) {
        this.likeDBdata = likeDBdata;
    }
    // endregion
}
