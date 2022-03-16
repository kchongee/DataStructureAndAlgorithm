package view;

import java.util.function.Consumer;

import adtImplementation.ArrayList;
import adtInterfaces.ListInterface;
import application.App;
import entity.Option;
import entity.Seller;

public class SellerHomeView{

    public static ListInterface<Option> menuOptions = new ArrayList<Option>();  

    static {
        menuOptions.add(new Option("Rooms", i -> goToPage(ii->SellerRoomsView.main())));
        menuOptions.add(new Option("Products", i -> goToPage(ii->SellerProductsView.main())));
        menuOptions.add(new Option("Orders", i -> goToPage(ii->SellerOrdersView.main())));
        menuOptions.add(new Option("Followers", i -> goToPage(ii->SellerFollowersView.main())));
    }

    public static void main(String[] args) {
        main();
    }
    
    public static void main() {                
        printTitle("Home");                
        
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
