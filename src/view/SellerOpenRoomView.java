package view;

import java.util.function.Consumer;

import adtImplementation.ArrayList;
import adtInterfaces.ListInterface;
import application.App;
import entity.Buyer;
import entity.Option;
import entity.Room;
import entity.Seller;

public class SellerOpenRoomView {    

    public static ListInterface<Option> menuOptions = new ArrayList<Option>();
    public static ListInterface<Room> rooms = new ArrayList<Room>();

    static{
        rooms = ((Seller)App.currentUser).getRooms();
    }

    public static void main() {        
        rooms = ((Seller)App.currentUser).getRooms();
        printTitle("Rooms");        
        
        if(rooms.size()>0){
            App.printThroughList(rooms);
        }else{
            System.out.println("There is no rooms created.");
            System.out.println();
            App.promptStringInput("Enter any key to return: ");
            App.goBack();
        }
        int roomOption = App.promptIntInput("Enter the option: ");        

        if(roomOption<0||roomOption>=rooms.size()){
            System.out.println("Please enter the option provided.");
        }else{
            Room room = rooms.get(roomOption);
            goToPage(ii->RoomView.main());
        }
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
