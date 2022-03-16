package view;

import java.util.function.Consumer;

import adtImplementation.ArrayList;
import adtInterfaces.ListInterface;
import application.App;
import entity.Option;
import entity.Seller;

public class SellerProductsView{

    public static ListInterface<Option> menuOptions = new ArrayList<Option>();
    
    static {
        menuOptions.add(new Option("Add Product", i -> goToPage(ii -> SellerAddProductView.main())));
        menuOptions.add(new Option("Remove Product", i -> goToPage(ii -> SellerRemoveProductView.main())));        
        menuOptions.add(new Option("View/Edit Product Detail", i -> goToPage(ii -> SellerDetailedProductView.main())));
    }

    public static void main(String[] args) {
        main();
    }
    
    public static void main() {        
        printTitle("Products");                
                                 
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
