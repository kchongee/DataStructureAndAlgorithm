package view;

import java.util.function.Consumer;

import adtImplementation.ArrayListEe;
import adtInterfaces.ListInterfaceEe;
import application.App;
import entity.Option;
import entity.Seller;
import entity.ViewPage;

public class SellerProductsView{

    public static ListInterfaceEe<Option> menuOptions = new ArrayListEe<Option>();
    
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
