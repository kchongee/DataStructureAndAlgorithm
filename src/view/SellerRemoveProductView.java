package view;

import java.util.Iterator;
import java.util.function.Consumer;

import adtImplementation.ArrayList;
import adtInterfaces.ListInterface;
import application.App;
import application.Option;
import entity.Account;
import entity.Product;
import entity.Seller;

public class SellerRemoveProductView {
    public static ListInterface<Product> productOptions = new ArrayList<Product>();
    public static ListInterface<Option> menuOptions = new ArrayList<Option>();

    public static void main(String[] args) {
        main();        
    }
    
    public static void main() {        
        printTitle("Remove Product");                        
                
        promptRemoveProduct();
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

    public static void promptRemoveProduct(){
        ListInterface<Product> products = ((Seller)App.currentUser).getProducts();        

        for (int i = 0; i < products.size(); i++) {
            System.out.printf("%2d    %-5s\n",i+1,products.get(i).getTitle());
        }

        int productIndex = App.promptIntInputSkippable("Product you want to remove (enter Enter to back): ")-1;
        if(productIndex<0 || productIndex>=products.size()){
            System.out.println("Numebering entered is out of range. Please try again!");
            System.out.println();
            promptRemoveProduct();
        }
        
        ((Seller)App.currentUser).removeProductByIndex(productIndex);        
        
        boolean wannaRemove = App.promptYesOrNo("Continue remove product? (Y|N): ");
        if(wannaRemove)
            main();
        else
            App.goBack();
    }
}
