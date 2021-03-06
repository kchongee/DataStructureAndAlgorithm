package view;

import java.util.regex.Pattern;

import UtilityClasses.CMD;
import UtilityClasses.jdbcUtil;
import application.App;
import entity.Account;

public class RegisterView {
    public static void main() {
        String name;
        String address;
        String email;
        String uname;
        String pwd;
        int isSeller;

        printTitle("Register");

        boolean valid = false;

        String emailRegEx = "^[a-zA-Z]\\w+@(\\S+)$";
        Pattern emailPattern = Pattern.compile(emailRegEx);

        do{
            isSeller = App.promptIntInput("Seller or Buyer (1 for Seller, 0 for Buyer): ");
            name = App.promptStringInput("Full name: ");
            address = App.promptStringInput("Address: ");
            email = App.promptStringInput("Email: ");
            uname = App.promptStringInput("Enter username: ");
            pwd = App.promptStringInput("Enter password: ");

            if (!App.accountList.checkAccount(uname)){
                valid=true;
            } else{
                //System.out.println("This username is already in use, please try again");
                CMD.pauseWithCustomScript("This username is already in use, press any key to try again...");
                App.clearScreen();
                RegisterView.main();
            }

            if(emailPattern.matcher(email).find()){
                valid=true;
            } else{
                //System.out.println("This email is invalid, please try again");
                CMD.pauseWithCustomScript("This email is invalid, press any key to try again...");
                App.clearScreen();
                RegisterView.main();
            }

        }while(!valid);

        Account newAcc = new Account(uname, pwd, name, address, email, isSeller);
        App.accountList.addAccount(newAcc);

        jdbcUtil.executeCUD(String.format("INSERT INTO Account VALUES('%s','%s','%s','%s','%s','%s',%s);", newAcc.getAccountID(), uname, pwd, name, address, email, isSeller));

        System.out.println("You will now be redirected to proceed to log in to your new account");

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
