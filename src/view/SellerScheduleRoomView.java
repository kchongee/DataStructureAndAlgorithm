package view;

import java.util.function.Consumer;

import adtImplementation.ArrayList;
import adtImplementation.HashMap;
import adtInterfaces.ListInterface;
import adtInterfaces.MapInterface;
import application.App;
import entity.Catalog;
import entity.Option;
import entity.Product;
import entity.Room;
import entity.Seller;

public class SellerScheduleRoomView {
    private static ListInterface<Option> menuOptions = new ArrayList<Option>();
    private static ListInterface<Option> productOptions = new ArrayList<Option>();    
    private static ListInterface<Product> products = new ArrayList<Product>();        
    private static Product tempProduct = null;

    static{
        // menuOptions.add(new Option("Add product from product page", i -> addProductFromProductPage() ));
        // menuOptions.add(new Option("Add product by creating new", i -> addProductByCreatingNew() ));
    }

    public static void main() {        
        printTitle("Schedule Room");
        
        String roomTitle = App.promptStringInput("Enter room title: ");

        String roomDateTime = App.promptStringInput("Enter room date time: ");

        MapInterface<String,Product> catalogProduct = new HashMap<>();        
        boolean isAddAgain = false;
        do{            
            App.menuHandler(menuOptions);            
            
            String catalogKeyword = App.promptStringInput("Enter catalog keyword: ");

            catalogProduct.put(catalogKeyword, tempProduct);
            
            isAddAgain = App.promptYesOrNo("Add again? (Y|N): ");            
        }while(isAddAgain);

        Room newRoom = new Room(roomTitle,catalogProduct,(Seller)App.currentUser);
        ((Seller)App.currentUser).createRoom(newRoom);
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

    public static void promptScheduleRoom(){

        String title = App.promptStringInput("Room Title: ");
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
