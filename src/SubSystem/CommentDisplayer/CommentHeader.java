package SubSystem.CommentDisplayer;

import UtilityClasses.DateTimeUtil;
import entity.Comment;

public class CommentHeader
{
    Comment comment;
    final static int LINE_SIZE = 55;

    CommentHeader(Comment comment)
    {
        this.comment = comment;
    }


    public String toString()
    {
        String section1 = comment.getUsername().toUpperCase() + " >>>";
        String section2 = createSection2();
        String section3 = createSection3();
        return section1 + section2 + section3;
    }


    // region : utility method
    private int[] getSectionLength()
    {
        int section1Size = comment.getUsername().length() + 4;
        int section3Size = comment.getAccountType().length() + 9;
        int section2Size = LINE_SIZE - section1Size - section3Size;
        return new int[]{section1Size, section2Size, section3Size};
    }

    private String createSection2()
    {
        int[] sectionLength = getSectionLength();
        return new String(new char[sectionLength[1]]).replace("\0", " ");
    }

    private String createSection3()
    {
        String timeStr = DateTimeUtil.localTimeToString(comment.getCommentTime());
        String capitalizedType = comment.getAccountType().toUpperCase();
        return timeStr + " " + capitalizedType;
    }
    // endregion
}
