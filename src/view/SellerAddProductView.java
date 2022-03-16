package view;

import java.util.function.Consumer;

import adtImplementation.ArrayList;
import adtInterfaces.ListInterface;
import application.App;
import entity.Option;
import entity.Product;
import entity.Seller;

public class SellerAddProductView {
    public static ListInterface<Option> menuOptions = new ArrayList<Option>();
    
    static {
        // menuOptions.add(new Option("Add Product", i -> System.out.println("Add Product")));
        // menuOptions.add(new Option("Remove Product", i -> System.out.println("Remove Product")));        
        // menuOptions.add(new Option("View Product in details", i -> System.out.println("Remove Product")));        
    }

    public static void main(String[] args) {
        main();
    }
    
    public static void main() {        
        printTitle("Add Product");
        
        promptAddProduct();
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

    public static void promptAddProduct(){

        String title = App.promptStringInput("Product Title: ");
        double price = App.promptDoubleInput("Product Price: ");        
        String desc = App.promptStringInput("Product Description: ");
        
        ((Seller)App.currentUser).addProduct(new Product(title,price,desc));
        
        System.out.println();
        boolean wannaAddMore = App.promptYesOrNo("Do you want to add more product? (Y|N): ");
        if(wannaAddMore)            
            main();
        else
            App.goBack();
    }
}
