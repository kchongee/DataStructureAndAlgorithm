package entity;

import UtilityClasses.DateTimeUtil;
import UtilityClasses.jdbcUtil;
import adtImplementation.ArrayList;
import adtImplementation.HashMap;

public class LikeList implements Comparable<LikeList>
{
    Room room;
    HashMap<String, ArrayList<Like>>classifiedLike = new HashMap<>();
    ArrayList<HashMap<String, Object>> likeDBdata = new ArrayList<HashMap<String,Object>>();



    // region : constructors
    public LikeList(Room room)
    {
        this.room = room;
        updateData();
    }
    // endregion



    // region : public method
    public void updateData()
    {
        fetchLikeDataFromDb();
        classifyLike();
    }


    public int getLikeQty() {
        return classifiedLike.get("LIKE").size();
    }


    public int getUnlikeQty(){
        return classifiedLike.get("UNLIKE").size();
    }


    public int getNoCommentQty(){
        return classifiedLike.get("NO COMMENT").size();
    }

    public String getLikeString()
    {
        int likeQty = getLikeQty();
        int neutralQty = getNoCommentQty();
        int unlikeQty = getUnlikeQty();

        return
        String.format
        (
            "%d\uD83D\uDC4D %d\uD83D\uDC4E %d\uD83D\uDE10",
            likeQty, unlikeQty, neutralQty
        );
    }
    // endregion



    // region : utility method
    private void fetchLikeDataFromDb()
    {
        String query =
                String.format
                (
                    """
                    SELECT l.accountID, value, acc.isSeller, likeTime, acc.userName
                    FROM   roomlike l, account acc
                    WHERE  roomID=%s AND l.accountID = acc.accountID;
                    """, room.getRoomID()
                );

        likeDBdata = jdbcUtil.readAll(query);

        // active bug
        // System.out.println(likeDBdata.size());

        // active bug
        // System.out.println(query);
    }

    private void classifyLike()
    {
        reinitializeClassifiedLike();
        if (!likeDBdata.isEmpty())
        {
            for (int i = 0 ; i < likeDBdata.size() ; i++)
            {
                HashMap<String,Object> tempMap = likeDBdata.get(i);



                Account tempAcc = new Account
                (
                    (String)  tempMap.get("accountID"),
                    (String)  tempMap.get("userName"),
                    (Integer) tempMap.get("isSeller")
                );



                Like tempLike = new Like
                (
                     tempAcc,
                     (String) tempMap.get("value"),
                     DateTimeUtil.sqlTimeToLocalTime(tempMap.get("likeTime"))
                );

                classifiedLike.get(tempLike.value).add(tempLike);
            }
        }
    }

    private void reinitializeClassifiedLike()
    {
        classifiedLike = new HashMap<String, ArrayList<Like>>();
        classifiedLike.put("LIKE", new ArrayList<Like>());
        classifiedLike.put("NO COMMENT", new ArrayList<Like>());
        classifiedLike.put("UNLIKE", new ArrayList<Like>());
    }

    public int compareTo(LikeList otherList)
    {
        if (this.getLikeQty() > otherList.getLikeQty()){
            return 1;
        }else if (this.getLikeQty() == otherList.getLikeQty()){
            return 0;
        }else {
            return -1;
        }
    }
    // endregion
}
