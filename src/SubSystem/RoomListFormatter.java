package SubSystem;

import entity.Room;

public class RoomListFormatter
{
    private final static  int LINE_SIZE = 95;
    private final static String
            NO_COL = "| %-3s |",
            TITLE_COL =" %-48s |",
            LIKE_COL=" %-12s |",
            STAR_COL=" %-19s |";


    public String lineStr()
    {
        return
        "+" + repChar(5,"-") +
        "+" + repChar(50, "-") +
        "+" + repChar(14,"-") +
        "+" + repChar(21, "-")+ "+";
    }


    public String strTableHead(){
        return lineStr()+"\n"+String.format(rowFormat(), "No","Room Title","Like","Most People Reviewed")+"\n"+lineStr();
    }

    public String toRow(int rowNo, Room room)
    {
        return String.format
        (
            rowFormat(),
            Integer.toString(rowNo),
            room.getRoomTitle(),
            room.getLikeList().getLikeString(),
            room.getReviewList().getMajorityStarStr());
    }


    public String strTableTitle(String title)
    {
        int titleChar = title.length();
        String padding = Integer.toString((LINE_SIZE - titleChar) / 2);
        String format = "%"+padding+"s" + "%"+titleChar+"s" + "%"+padding+"s";
        return String.format(format, " ",title," ");
    }

    private static String repChar(int time, String character) {
        return new String(new char[time]).replace("\0",character);
    }

    private String rowFormat(){
        return NO_COL+TITLE_COL+LIKE_COL+STAR_COL;
    }
}
