package SubSystem.CommentInputPanel;

import UtilityClasses.DateTimeUtil;
import UtilityClasses.jdbcUtil;
import application.App;
import entity.Account;
import entity.Comment;
import entity.Launchable;
import entity.Room;
import jdk.dynalink.beans.StaticClass;

import java.util.Scanner;

public class CommentInputPanel implements Launchable
{
    Account account;
    Room room;

    // region 001 : constructors
    CommentInputPanel() {}

    CommentInputPanel(Account account, Room room)
    {
        this.account = account;
        this.room = room;
    }
    // endregion



    // region 002: public method
    public void sendCommentToDatabase(Comment comment)
    {
        String query =
                String.format("insert into comment values('%s','%s','%s','%s','%s','%s')",
                account.getAccountID(),
                comment.getCommentDate(),
                comment.getCommentTime(),
                room.getRoomId(),
                comment.getContent());
        jdbcUtil.executeCUD(query);
    }
    // endregion



    // region 003: static class
    private static class Args
    {
        Account account;
        Room room;

        Args(String[]arg)
        {
            if (arg.length != 2)
            {
                String errMsg =

                """
                Please provide following arguments:\n
                1. accountID\n
                2. roomID\n
                """;

                throw new IllegalArgumentException(errMsg);
            }
            this.account = new Account(arg[0]);
            this.room = new Room(arg[1]);
        }
    }
    // endregion



    // region : getters setters
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
    // endregion



    public static void main(String[] args)
    {
        Args arg = new Args(args);
        CommentInputPanel cip = new CommentInputPanel(arg.account, arg.room);

        String input = App.promptStringInput(">>> ");

        Comment comment = new Comment(
                cip.account,
                cip.room,
                DateTimeUtil.localTimeNow(),
                DateTimeUtil.localDateNow(),
                input);
    }


}
