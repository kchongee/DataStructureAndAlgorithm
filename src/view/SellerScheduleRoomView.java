package view;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.function.Consumer;

import adtImplementation.ArrayList;
import adtImplementation.HashMap;
import adtInterfaces.ListInterface;
import adtInterfaces.HashMapInterface;
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
        menuOptions.add(new Option("Add product from product page", i -> addProductFromProductPage() ));
        menuOptions.add(new Option("Add product by creating new", i -> addProductByCreatingNew() ));        
    }

    public static void main(String[] args) {
        main();
    }

    public static void main() {        
        printTitle("Create Room");
        
        String roomTitle = App.promptStringInput("Enter room title: ");

        LocalDate roomDate = App.promptDateInput("Schedule room date");
        LocalTime roomTime = App.promptTimeInput("Schedule room time");

        System.out.println();

        Catalog catalog = new Catalog();
        boolean isAddAgain = false;
        do{            
            App.menuHandler(menuOptions);    
            
            System.out.println();
            
            App.menuHandler(productOptions);

            catalog.add(tempProduct);
            
            isAddAgain = App.promptYesOrNo("Add again? (Y|N): ");   
            System.out.println();         
        }while(isAddAgain);

        Room newRoom = new Room(roomTitle,catalog,(Seller)App.currentUser,roomDate,roomTime);
        ((Seller)App.currentUser).createRoom(newRoom);
        App.goBack();
    }
    
    public static void printTitle(String title){
        App.clearScreen();
        System.out.println(title);        
        System.out.println();
    }

    public static void addProductFromProductPage(){        
        products = ((Seller) App.currentUser).getProducts();
        if(!products.isEmpty()){                        
            if(productOptions.isEmpty()){
                for(int i=0;i<products.size();i++){
                    Product currProduct = products.get(i);
                    productOptions.add(new Option(currProduct.getTitle(), ii -> {
                        System.out.println("title: "+currProduct.getTitle());
                        tempProduct = currProduct;
                    }));
                }
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
