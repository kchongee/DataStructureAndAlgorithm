package view;

import java.util.function.Consumer;

import adtImplementation.ArrayList;
import adtInterfaces.ListInterface;
import application.App;
import entity.Option;
import entity.Seller;

public class SellerProductsView{

    public static ListInterface<Option> menuOptions = new ArrayList<Option>();
    
    static {
        menuOptions.add(new Option("Add Product", i -> System.out.println("Add Product")));
        menuOptions.add(new Option("Remove Product", i -> System.out.println("Remove Product")));        
    }
    
    public static void main() {        
        printTitle("Products");
                                 
        App.menuHandler(menuOptions);
    }

    public static void printTitle(String title){
        App.clearScreen();
        System.out.println(title);        
        System.out.println();
    }
}
