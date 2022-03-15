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

public class SellerCreateRoomView {
    private static ListInterface<Option> menuOptions = new ArrayList<Option>();
    private static ListInterface<Option> productOptions = new ArrayList<Option>();    
    private static ListInterface<Product> products = new ArrayList<Product>();        
    private static Product tempProduct = null;

    static{
        menuOptions.add(new Option("Add product from product page", i -> addProductFromProductPage() ));
        menuOptions.add(new Option("Add product by creating new", i -> addProductByCreatingNew() ));
    }

    public static void main() {        
        printTitle("Create Room");
        
        String roomTitle = App.promptStringInput("Enter room title: ");        

        MapInterface<String,Product> catalogProduct = new HashMap<>();        
        boolean isAddAgain = false;
        do{            
            App.menuHandler(menuOptions);            
            
            String catalogKeyword = App.promptStringInput("Enter catalog keyword: ");

            catalogProduct.put(catalogKeyword, tempProduct);
            
            isAddAgain = App.promptYesOrNo("Add again? (Y|N): ");            
        }while(isAddAgain);

        Room newRoom = new Room(roomTitle,catalogProduct);
        ((Seller)App.currentUser).createRoom(newRoom);
    }
    
    public static void printTitle(String title){
        App.clearScreen();
        System.out.println(title);        
        System.out.println();
    }

    public static void addProductFromProductPage(){
        products = ((Seller) App.currentUser).getProducts();
        if(!products.isEmpty()){
            for(int i=0;i<products.size();i++){
                Product currProduct = products.get(i);
                productOptions.add(new Option(currProduct.getTitle(), ii -> {
                    tempProduct = currProduct;
                }));
            }
        }else{
            System.out.println("There is no product inside your product page.");
        }
    }

    public static void addProductByCreatingNew(){
        
        tempProduct = new Product(
            App.promptStringInput("Product Title: "), 
            App.promptDoubleInput("Product Price: "), 
            App.promptStringInput("Product Description: ")
        );
        
        System.out.println();
        boolean wannaAdd = App.promptYesOrNo("Do you want to add this product into product page also? (Y|N): ");
        if(wannaAdd)
            ((Seller)App.currentUser).addProduct(tempProduct);
    }

    public static void goToPage(Consumer<String> page){
        App.history.push(i -> main());
        page.accept("t");
    }
}
