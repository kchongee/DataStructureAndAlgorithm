package view;

import adtImplementation.ArrayList;
import adtInterfaces.ListInterface;
import application.App;
import entity.Option;

public class BuyerHomeView{

    public static ListInterface<Option> menuOptions = new ArrayList<Option>();
    
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
