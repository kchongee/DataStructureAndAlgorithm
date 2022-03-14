package view;

import java.util.function.Consumer;

import adtImplementation.ArrayList;
import adtImplementation.HashMap;
import adtInterfaces.ListInterface;
import application.App;
import entity.Option;
import entity.Product;
import entity.Room;

public class RoomView {
    public static HashMap<String,Product> catalog = new HashMap<>();
    
    static{
        
    }

    public static void main() {         
        printTitle("");        

        System.out.println("print all the room");
        
        // App.menuHandler(catalog);    
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
