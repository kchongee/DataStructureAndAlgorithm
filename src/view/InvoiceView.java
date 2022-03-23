package view;

import java.util.function.Consumer;

import adtImplementation.ArrayList;
import adtInterfaces.ListInterface;
import application.App;
import application.Option;
import entity.Account;
import entity.Buyer;
import entity.Invoice;
//import entity.Seller;
//import entity.ViewPage;
import entity.Seller;

public class InvoiceView {
    public static ListInterface<Option> menuOptions = new ArrayList<Option>();  

    public static void main() {                
        printTitle("Invoices");
            
        if(App.currentUser instanceof Buyer){
            System.out.println(Invoice.displayAll(((Buyer)App.currentUser).getInvoices()));
        }else if(App.currentUser instanceof Seller){            
            System.out.println(Invoice.displayAll(((Seller)App.currentUser).getInvoices()));
        }else{
            System.out.println("Please login first!");
        }
                
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
