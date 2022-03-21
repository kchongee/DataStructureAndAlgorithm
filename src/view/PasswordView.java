package view;

import UtilityClasses.jdbcUtil;
import application.App;

public class PasswordView {
    public static void main() {
        String newPwd;
        String userName;

        printTitle("Forgot My Password");

        userName = App.promptStringInput("Enter username: ");
        newPwd = App.promptStringInput("Enter your new password: ");

        if (App.accountList.changePassword(userName, newPwd)){
            jdbcUtil.executeCUD(String.format("UPDATE Account SET userPwd='%s' WHERE userName='%s';",newPwd,userName));
            System.out.println("Your password has been changed. You will now be redirected to proceed to log in");

        } else{
            System.out.println("Your attempt to change your password was not successful. Please try again");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            App.clearScreen();
            PasswordView.main();
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        App.clearScreen();
        App.retrieveAccounts();
        WelcomeView.main();
    }

    public static void printTitle(String title){
        App.clearScreen();
        System.out.println(String.format("========= %s =========",title));       
        System.out.println();
    }
}
