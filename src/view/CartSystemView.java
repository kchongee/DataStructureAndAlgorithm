package view;

import java.util.function.Consumer;

import adtImplementation.ArrayList;
import adtInterfaces.ListInterface;
import application.App;
import entity.Invoice;
import entity.Option;
import entity.Payment;
import entity.Seller;
<<<<<<< HEAD
//import entity.ViewPage;
=======
>>>>>>> 85b8a563adc4bfb95fab511383abecd691d41b2e

public class CartSystemView {
    public static ListInterface<Option> menuOptions = new ArrayList<Option>();  

    static {
        menuOptions.add(new Option("Choose payment method", i -> goToPage(ii->PaymentView.main())));
        menuOptions.add(new Option("Print invoice", i -> goToPage(ii->InvoiceView.main())));
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
