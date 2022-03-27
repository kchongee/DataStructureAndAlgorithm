package view;

import java.util.function.Consumer;

import adtImplementation.ArrayList;
import adtInterfaces.ListInterface;
import application.App;
import application.Option;

public class SellerHomeView{

    public static ListInterface<Option> menuOptions = new ArrayList<Option>();  

    static {
        menuOptions.add(new Option("Create & Open Live Room", i -> goToPage(ii -> CatalogEditorView.main(new String[]{}))));        
        menuOptions.add(new Option("Manage Product", i -> goToPage(ii->SellerManageProductView.main())));
        menuOptions.add(new Option("Invoices", i -> goToPage(ii->InvoiceView.main())));        
        menuOptions.add(new Option("Manage Voucher", i -> goToPage(ii->SellerVoucherManageView.main())));
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
