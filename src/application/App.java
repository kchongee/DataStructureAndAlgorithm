package application;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.function.Consumer;

import adtImplementation.StackLinkedList;
import entity.ConsoleMenu;
import entity.ConsoleMenuOption;
import entity.Follower;

public class App {
    public static Scanner scanner = new Scanner(System.in);
    public static StackLinkedList<Consumer<String>> history = new StackLinkedList<Consumer<String>>();
    public static void main(String[] args) throws Exception {        
        clearScreen();

        ConsoleMenu<?> liveRoomMenu = new ConsoleMenu<>("Live Room",
            new ConsoleMenuOption("Create Live Room", i -> System.out.println("Go to Create Live Room")),            
            new ConsoleMenuOption("Edit Live Room", i -> System.out.println("Go to Edit Live Room")),
            new ConsoleMenuOption("Delete Live Room", i -> System.out.println("Go to Delete Live Room")),
            new ConsoleMenuOption("Schedule Live Room", i -> System.out.println("Go to Schedule Live Room"))
        );

        ConsoleMenu<?> productsMenu = new ConsoleMenu<>("Products",
            new ConsoleMenuOption("Create Product", i -> System.out.println("Go to Create Product")),            
            new ConsoleMenuOption("Edit Product", i -> System.out.println("Go to Edit Product")),
            new ConsoleMenuOption("Delete Product", i -> System.out.println("Go to Delete Product")),
            new ConsoleMenuOption("Schedule Product", i -> System.out.println("Go to Schedule Product"))
        );
        
        ConsoleMenu<Follower> ordersMenu = new ConsoleMenu<Follower>("List of order:",
            new Follower[]{new Follower(),new Follower("b")}
        );

        //# region: followers
        
        ConsoleMenu<Follower> followersMenu = new ConsoleMenu<Follower>("List of follower:",
            new Follower[]{new Follower(),new Follower("b")}
        );

        // ConsoleMenu followersMenu = new ConsoleMenu("List of follower:");        


        //#endregion

        ConsoleMenu<?> sellerHome = new ConsoleMenu<>("Seller home screen", 
            new ConsoleMenuOption("Live Room", i -> liveRoomMenu.printAPage()),
            new ConsoleMenuOption("Products", i -> productsMenu.printAPage()),
            new ConsoleMenuOption("Orders", i -> ordersMenu.printAPage()),
            new ConsoleMenuOption("Followers", i -> followersMenu.printAPage())
        );

        sellerHome.printAPage();
        
        scanner.close();
    }

    public static void nextLine() {  
        System.out.println();
    }  

    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }
}
