package view;

import java.util.function.Consumer;

import adtImplementation.ArrayList;
import adtInterfaces.ListInterface;
import application.App;
import entity.Option;
import entity.Seller;

public class SellerOrdersView{

    public static ListInterface<Option> menuOptions = new ArrayList<Option>();
    
    public static void main() {        
        printTitle("Orders");
        
        App.menuHandler(menuOptions);
    }

    public static void printTitle(String title){
        App.clearScreen();
        System.out.println(title);        
        System.out.println();
    }
}
