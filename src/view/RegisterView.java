package view;

import java.util.regex.Pattern;

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

        String emailRegEx = "[a-zA-Z]\\w+@(\\S+)";
        Pattern emailPattern = Pattern.compile(emailRegEx);

        do{
            isSeller = App.promptIntInput("Seller or Buyer (1 for Seller, 0 for Buyer): ");
            name = App.promptStringInput("Full name: ");
            address = App.promptStringInput("Address: ");
            email = App.promptStringInput("Email: ");
            uname = App.promptStringInput("Enter username: ");
            pwd = App.promptStringInput("Enter password: ");

            if (!App.accountList.checkAccount(new Account(uname))){
                valid=true;
            } else{
                App.clearScreen();
                System.out.println("This username is already in use, please try again");
            }

            if(!emailPattern.matcher(email).find()){
                valid=true;
            } else{
                App.clearScreen();
                System.out.println("This email is invalid, please try again");
            }

        }while(!valid);

        App.accountList.addAccount(new Account(uname, pwd, name, address, email, isSeller));

        jdbcUtil.executeCUD(String.format("INSERT INTO Account VALUES(%s,%s,%s,%s,%s,%s);", uname, pwd, name, address, email, isSeller));
    }    

    public static void printTitle(String title){
        App.clearScreen();
        System.out.println(title);        
        System.out.println();

    }
}
