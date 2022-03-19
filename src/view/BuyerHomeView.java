package view;

import java.util.function.Consumer;

import adtImplementation.ArrayList;
import adtInterfaces.ListInterface;
import application.App;
import entity.Option;

public class BuyerHomeView{

    public static ListInterface<Option> menuOptions = new ArrayList<Option>();
    
    static {
        menuOptions.add(new Option("Rooms", i -> goToPage(ii->BuyerHomeView.main())));
        menuOptions.add(new Option("Cart", i -> goToPage(ii->BuyerHomeView.main())));
        menuOptions.add(new Option("Orders", i -> goToPage(ii->BuyerHomeView.main())));
        menuOptions.add(new Option("Inbox", i -> goToPage(ii->BuyerInboxView.main())));     
    }

    public static void main(String[] args) {
        
    }

    public static void main() {
        System.out.println("Buyer Home");
        
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
