package view;

import java.util.function.Consumer;

import adtImplementation.ArrayList;
import adtInterfaces.ListInterface;
import application.App;
import entity.Option;

public class SellerRoomsView {    

    public static ListInterface<Option> menuOptions = new ArrayList<Option>();

    static{
        menuOptions.add(new Option("Open & Enter Room", i -> goToPage(ii -> RoomView.main())));
        menuOptions.add(new Option("Create Room", i -> goToPage(ii -> SellerCreateRoomView.main())));
        menuOptions.add(new Option("Schedule Room", i -> goToPage(ii -> SellerCreateRoomView.main())));
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

    public static void goToPage(Consumer<String> page){
        App.history.push(i -> main());
        page.accept("t");
    }
}
