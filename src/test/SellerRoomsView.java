package test;

import adtImplementation.ArrayListEe;
import adtInterfaces.ListInterfaceEe;
import application.App;
import entity.Option;

public class SellerRoomsView {    

    public static ListInterfaceEe<Option> menuOptions = new ArrayListEe<Option>();

    static{
         menuOptions.add(new Option("Create Room", i -> System.out.println("Here is gg")));
        menuOptions.add(new Option("Delete Room", i -> System.out.println("Here is gg")));
    }

    public static void main() {        
        printTitle("Rooms");    
        
       
        
        App.menuHandler(menuOptions);
    }
    
    public static void printTitle(String title){
        App.clearScreen();
        System.out.println(title);        
        System.out.println();
    }
}
