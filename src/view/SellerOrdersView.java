package view;

import java.util.function.Consumer;

import adtImplementation.ArrayListEe;
import adtInterfaces.ListInterfaceEe;
import application.App;
import entity.Option;
import entity.Seller;
import entity.ViewPage;

public class SellerOrdersView{

    public static ListInterfaceEe<Option> menuOptions = new ArrayListEe<Option>();
    
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
