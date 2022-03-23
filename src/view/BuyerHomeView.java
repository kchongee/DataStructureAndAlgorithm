package view;

import java.util.function.Consumer;

import adtImplementation.ArrayList;
import adtInterfaces.ListInterface;
import application.App;
import application.Option;

public class BuyerHomeView{

    public static ListInterface<Option> menuOptions = new ArrayList<Option>();
    
    static {
        menuOptions.add(new Option("Rooms", i -> goToPage(ii->RoomListView.main())));
        menuOptions.add(new Option("Carts", i -> goToPage(ii->BuyerHomeView.main())));
        menuOptions.add(new Option("Invoices", i -> goToPage(ii->InvoiceView.main())));
        menuOptions.add(new Option("Inbox", i -> goToPage(ii->BuyerInboxView.main())));
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
