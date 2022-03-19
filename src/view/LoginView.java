package view;

import entity.Account;
import entity.AccountList;
import application.App;
import entity.Buyer;
import entity.Seller;

public class LoginView{     
    public static void main() {
        printTitle("Login");
                     
        String uname = App.promptStringInput("Enter username: ");
        String pwd = App.promptStringInput("Enter password: ");

        
        if(App.accountList.loginAccount(new Account(uname, pwd))){
            System.out.println("Login Successful");
            App.currentUser = App.accountList.grabAccount(uname);
            if(App.currentUser.getIsSeller()==1){
                SellerHomeView.main();
            }else if(App.currentUser.getIsSeller()==0){
                BuyerHomeView.main();
            }else{
                SellerHomeView.main();
            }
        }else{
            System.out.println("Invalid credential.");
            boolean tryAgain = App.promptTryAgain();
            if(tryAgain){
                main();
            }else{
                App.goBack();
            }
        }   
    }       
    
    public static void printTitle(String title){
        App.clearScreen();
        System.out.println(String.format("========= %s =========",title));       
        System.out.println();
    }
}
