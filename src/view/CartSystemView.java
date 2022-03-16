package view;

import java.util.function.Consumer;

import adtImplementation.ArrayListEe;
import adtInterfaces.ListInterfaceEe;
import application.App;
import entity.Option;
import entity.Seller;
import entity.ViewPage;

public class CartSystemView {
    public static ListInterfaceEe<Option> menuOptions = new ArrayListEe<Option>();  

    static {
        menuOptions.add(new Option("Choose payment method", i -> goToPage(ii->Payment.main())));
        menuOptions.add(new Option("Print invoice", i -> goToPage(ii->Invoice.main())));
    }
    
    public static void main() {                
        printTitle("Check out System");                
        
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

    public static void main(String[] args) {
        main();
    }
    
}
