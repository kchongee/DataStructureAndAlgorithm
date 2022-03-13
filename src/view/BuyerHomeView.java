package view;

import java.util.function.Consumer;

import adtImplementation.ArrayListEe;
import adtInterfaces.ListInterfaceEe;
import application.App;
import entity.Option;
import entity.Seller;
import entity.ViewPage;

public class BuyerHomeView{

    public static ListInterfaceEe<Option> menuOptions = new ArrayListEe<Option>();
    
    static {
        menuOptions.add(new Option("Rooms", i -> System.out.println("Here is gg")));
        menuOptions.add(new Option("Products", i -> System.out.println("Here is gg")));
        menuOptions.add(new Option("Orders", i -> System.out.println("Here is gg")));
        menuOptions.add(new Option("Followers", i -> System.out.println("Here is gg")));     
    }

    public static void main() {
        System.out.println("Buyer Home");
        
        App.menuHandler(menuOptions);
    }
    
    public static void printTitle() {
        App.clearScreen();        
    }
}
