package entity;

import UtilityClasses.DateTimeUtil;
import UtilityClasses.jdbcUtil;
import adtImplementation.ArrayList;
import adtImplementation.LinkedHashMap;
import adtInterfaces.ListInterface;
import adtInterfaces.MapInterface;

import java.util.Iterator;

public class ReviewList implements Comparable<ReviewList> {
    private Room room;
    private MapInterface<Integer, ListInterface<Review>> classifiedReview;
    private ListInterface<MapInterface<String, Object>> reviewDBdata;


    // region : constructors
    public ReviewList(Room room) {
        this.reviewDBdata = new ArrayList<>();
        this.classifiedReview = new LinkedHashMap<>();
        this.room = room;
        updateData();
    }
    // endregion


    // region : public method
    public void updateData() {
        syncReviewDataFromDb();
        classifyReview();
    }


    public int get1StarQty() {
        return classifiedReview.get(1).size();
    }


    public int get2StarQty() {
        return classifiedReview.get(2).size();
    }


    public int get3StarQty() {
        return classifiedReview.get(3).size();
    }


    public int get4StarQty() {
        return classifiedReview.get(4).size();
    }


    public int get5StarQty() {
        return classifiedReview.get(5).size();
    }


    public int getMajorityReview() {
        int max = 1;
        int qty = classifiedReview.get(1).size();

        for (int i = 2; i < 6; i++) {
            int quantity = classifiedReview.get(i).size();
            if (quantity > qty) {
                max = i;
                qty = quantity;
            }
        }

        return max;
    }


    public String getMajorityStarStr() {
        return new String(new char[getMajorityReview()]).replace("\0", "*");
    }


    public void syncReviewDataFromDb() {
        String query = String.format("""
                SELECT r.accountID, acc.isSeller, star, reviewMsg, revTime ,acc.userName
                FROM   RoomReview r, account acc
                WHERE  roomID=%s AND r.accountID = acc.accountID;
                """, room.getRoomID());
        reviewDBdata = jdbcUtil.readAll(query);
    }
    // endregion


    // region : utility method
    private void classifyReview() {
        reinitializeClassifiedReview();
        if (!reviewDBdata.isEmpty()) {
            for (int i = 0; i < reviewDBdata.size(); i++) {
                MapInterface<String, Object> tempMap = reviewDBdata.get(i);

                Account tempAcc = new Account((String) tempMap.get("accountID"), (String) tempMap.get("userName"), (Integer) tempMap.get("isSeller"));

                Review tempReview = new Review(tempAcc, (Integer) tempMap.get("star"), (String) tempMap.get("reviewMsg"), DateTimeUtil.sqlTimeToLocalTime(tempMap.get("revTime")));

                classifiedReview.get(tempReview.getStar()).add(tempReview);
            }
        }
    }

    private void reinitializeClassifiedReview() {
        classifiedReview = new LinkedHashMap<>();
        classifiedReview.put(1, new ArrayList<Review>());
        classifiedReview.put(2, new ArrayList<Review>());
        classifiedReview.put(3, new ArrayList<Review>());
        classifiedReview.put(4, new ArrayList<Review>());
        classifiedReview.put(5, new ArrayList<Review>());
    }
    // endregion


    // region : getter setter

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public MapInterface<Integer, ListInterface<Review>> getClassifiedReview() {
        return classifiedReview;
    }

    public void setClassifiedReview(MapInterface<Integer, ListInterface<Review>> classifiedReview) {
        this.classifiedReview = classifiedReview;
    }

    public ListInterface<MapInterface<String, Object>> getReviewDBdata() {
        return reviewDBdata;
    }

    public void setReviewDBdata(ListInterface<MapInterface<String, Object>> reviewDBdata) {
        this.reviewDBdata = reviewDBdata;
    }

    // endregion


    public int compareTo(ReviewList otherList) {
        if (this.getMajorityReview() > otherList.getMajorityReview()) {
            return 1;
        } else if (this.getMajorityReview() == otherList.getMajorityReview()) {
            return 0;
        } else {
            return -1;
        }
    }

    public String toString() {
        String str = "";
        Iterator<MapInterface.Entry<Integer, ListInterface<Review>>> itr = this.classifiedReview.iterator();

        while (itr.hasNext()) {
            ListInterface<Review> reviewList = itr.next().getValue();
            for (int i = 0; i < reviewList.size(); i++) {
                str = str + "Start: " + reviewList.get(i).getStar() + "\n";
                str = str + "Time : " + DateTimeUtil.localTimeToString(reviewList.get(i).getRevTime()) + "\n";
                str = str + "Comment : " + reviewList.get(i).getReviewMsg() + "\n" + "\n";
            }
        }
        return str;
    }

    public static void main(String[] args) {
        ReviewList list = new ReviewList(new Room("1"));
        list.toString();
    }
}
