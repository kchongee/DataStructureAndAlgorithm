package view.RoomViews;

import UtilityClasses.CMD;
import UtilityClasses.EXEHandler;
import application.App;

import java.io.FileNotFoundException;

public class RoomViewExe
{
   EXEHandler inputPanelExe = new EXEHandler("CommentInput.exe",getCommentInputArgs(),"exe");
   EXEHandler commentShowerExe = new EXEHandler("CommentDisplay.exe",getCommentDisplayArgs() ,"exe");
   EXEHandler inputPanelShortcut = new EXEHandler("CommentInput.lnk",getCommentInputArgs(),"lnk");
   EXEHandler commentShowerShortcut = new EXEHandler("CommentDisplay.lnk",getCommentDisplayArgs(),"lnk");

    public RoomViewExe() throws FileNotFoundException {
    }

    public String[] getCommentDisplayArgs()
    {
        String roomID = "";
        if (App.currentUser.getIsSeller() == null){
            roomID = "1";
        }
        else if (App.currentUser.getIsSeller() == 1){
            roomID = String.valueOf(App.sellerCreatedRoomID);
        }else {
            roomID = App.chosenRoom.getRoomID();
        }
        return
                new String[]
                        {
                                App.currentUser.getAccountID(),
                                roomID
                        };
    }

    public String[]getCommentInputArgs() {
        String roomID = "";

        if (App.currentUser.getIsSeller() == null){
            roomID = "1";
        }
        else if (App.currentUser.getIsSeller() == 1) {
            roomID = String.valueOf(App.sellerCreatedRoomID);
        } else {
            roomID = App.chosenRoom.getRoomID();
        }

        return
                new String[]
                        {
                                App.currentUser.getAccountID(),
                                App.currentUser.getIsSeller() == null? "0":String.valueOf(App.currentUser.getIsSeller()),
                                roomID,
                                App.chosenRoom.getSeller() == null? "A01":App.chosenRoom.getSeller().getAccountID()
                        };

    }

    public EXEHandler getInputPanelExe() {
        return inputPanelExe;
    }

    public void setInputPanelExe(EXEHandler inputPanelExe) {
        this.inputPanelExe = inputPanelExe;
    }

    public EXEHandler getCommentShowerExe() {
        return commentShowerExe;
    }

    public void setCommentShowerExe(EXEHandler commentShowerExe) {
        this.commentShowerExe = commentShowerExe;
    }

    public EXEHandler getInputPanelShortcut() {
        return inputPanelShortcut;
    }

    public void setInputPanelShortcut(EXEHandler inputPanelShortcut) {
        this.inputPanelShortcut = inputPanelShortcut;
    }

    public EXEHandler getCommentShowerShortcut() {
        return commentShowerShortcut;
    }

    public void setCommentShowerShortcut(EXEHandler commentShowerShortcut) {
        this.commentShowerShortcut = commentShowerShortcut;
    }

    public void terminate()
    {
        String command = EXEHandler.wrapStringWithQuotes(EXEHandler.getExecuteablePath()+"\\"+"TerminateCommentInput.bat");
        String command2 = EXEHandler.wrapStringWithQuotes(EXEHandler.getExecuteablePath()+"\\"+"TerminateCommentDisplay.bat");
        CMD.executeWindowsCommand(command);
        CMD.executeWindowsCommand(command2);
    }

    public static void main(String[] args) {
        System.out.println(App.currentUser.getIsSeller());
    }
}