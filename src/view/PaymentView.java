package view;

import java.util.function.Consumer;

import adtImplementation.ArrayList;
import adtInterfaces.ListInterface;
import application.App;
import entity.Option;
//import entity.Seller;
//import entity.ViewPage;

public class PaymentView {
    public static ListInterface<Option> menuOptions = new ArrayList<Option>();  

    public static void main() {                
        printTitle("Please choose your payment method");                
        System.out.println("1. Credit Card");
        System.out.println("2. E-banking");
        System.out.println("3. E-wallet");
        //App.menuHandler(menuOptions);
    }

    public static void printTitle(String title){
        App.clearScreen();
        System.out.println(title);        
        System.out.println();
    }
    
    public static void goToPage(Consumer<String> page){
        App.history.push(i -> main());
        page.accept("t");
    }
}
