package adtImplementation;

import UtilityClasses.DateTimeUtil;
import UtilityClasses.jdbcUtil;
import entity.*;

public class ReviewList implements Comparable<ReviewList>
{
    Room room;
    HashMap<Integer, ArrayList<Review>> classifiedReview;
    ArrayList<HashMap<String, Object>> reviewDBdata = new ArrayList<HashMap<String,Object>>();



    // region : constructors
    public ReviewList(Room room) {
        this.room = room;
        updateData();
    }
    // endregion



    // region : public method
    public void updateData()
    {
        fetchReviewDataFromDb();
        classifyReview();
    }


    public int get1StarQty(){ return classifiedReview.get(1).size();}


    public int get2StarQty(){ return classifiedReview.get(2).size();}


    public int get3StarQty(){ return classifiedReview.get(3).size();}


    public int get4StarQty(){ return classifiedReview.get(4).size();}


    public int get5StarQty(){ return classifiedReview.get(5).size();}


    public int getMajorityReview()
    {
        int max = 1;
        int qty = classifiedReview.get(1).size();

        for (int i = 2 ; i < 6 ; i++)
        {
            int quantity = classifiedReview.get(i).size();
            if (quantity > qty)
            {
                max = i;
                qty = quantity;
            }
        }

        return max;
    }
    // endregion


    public String getMajorityStarStr(){
        return new String(new char[getMajorityReview()]).replace("\0","*");
    }

    // region : utility method
    private void fetchReviewDataFromDb()
    {
        reviewDBdata = jdbcUtil.readAll
        (
            String.format
            (
                """
                SELECT r.accountID, star, msg, revTime ,acc.userName
                FROM   RoomReview r, account acc
                WHERE  roomID='%s' AND r.accountID = acc.accountID;
                """, room.getRoomId()
            )
        );
    }

    private void classifyReview()
    {
        reinitializeClassifiedReview();
        if (!reviewDBdata.isEmpty())
        {
            for (int i = 0 ; i < reviewDBdata.size() ; i++)
            {
                HashMap<String,Object> tempMap = reviewDBdata.get(i);


                Account tempAcc = new Account
                (
                    (String)  tempMap.get("accountID"),
                    (String)  tempMap.get("userName"),
                    (Integer) tempMap.get("isSeller")
                );



                Review tempReview = new Review
                (
                    tempAcc,
                    (Integer) tempMap.get("star"),
                    (String)  tempMap.get("reviewMsg"),
                    DateTimeUtil.sqlTimeToLocalTime(tempMap.get("revTime"))
                );

                classifiedReview.get(tempReview.getStar()).add(tempReview);
            }
        }
    }

    private void reinitializeClassifiedReview()
    {
        classifiedReview = new HashMap<Integer, ArrayList<Review>>();
        classifiedReview.put(1, new ArrayList<Review>());
        classifiedReview.put(2, new ArrayList<Review>());
        classifiedReview.put(3, new ArrayList<Review>());
        classifiedReview.put(4, new ArrayList<Review>());
        classifiedReview.put(5, new ArrayList<Review>());
    }
    // endregion



    public int compareTo(ReviewList otherList)
    {
        if (this.getMajorityReview() > otherList.getMajorityReview()){
            return 1;
        }else if (this.getMajorityReview() == otherList.getMajorityReview()){
            return 0;
        }else {
            return -1;
        }
    }
}
