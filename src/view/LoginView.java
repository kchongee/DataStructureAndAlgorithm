package view;

import adtImplementation.Account;
import application.App;
import entity.Buyer;
import entity.Seller;

public class LoginView{     
    public static void main() {
        printTitle("Login");
                     
        String uname = App.promptStringInput("Enter username: ");
        String pwd = App.promptStringInput("Enter password: ");

        if(uname.equals(pwd)){
            System.out.println("Login Successful");
            App.currentUser = new Account("name", "address", "email", "userName", "userPwd");
            if(App.currentUser instanceof Seller){
                SellerHomeView.main();
            }else if(App.currentUser instanceof Buyer){
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
        System.out.println(title);        
        System.out.println();
    }
}
