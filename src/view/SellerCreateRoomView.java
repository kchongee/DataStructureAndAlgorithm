package view;

import adtImplementation.ArrayListEe;
import adtImplementation.HashMap;
import adtInterfaces.ListInterfaceEe;
import adtInterfaces.MapInterface;
import application.App;
import entity.Option;
import entity.Product;
import entity.Seller;

public class SellerCreateRoomView {
    private static ListInterfaceEe<Option> menuOptions = new ArrayListEe<Option>();
    private static ListInterfaceEe<Option> productOptions = new ArrayListEe<Option>();    
    private static ListInterfaceEe<Product> products = new ArrayListEe<Product>();    
    private static MapInterface<String,Product> catalogProduct = new HashMap<>();
    private static Product tempProduct = null;

    static{
        menuOptions.add(new Option("Add product from product page", i -> addProductFromProductPage() ));
        menuOptions.add(new Option("Add product by creating new", i -> addProductByCreatingNew() ));
    }

    public static void main() {        
        printTitle("Create Room");
        
        String roomTitle = App.promptStringInput("Enter room title: ");        

        boolean isAddAgain = false;
        do{            
            App.menuHandler(menuOptions);            
            
            String catalogKeyword = App.promptStringInput("Enter catalog keyword: ");

            catalogProduct.put(catalogKeyword, tempProduct);
            
            isAddAgain = App.promptYesOrNo("Add again? (Y|N): ");            
        }while(isAddAgain);

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
                Product currProduct = products.retrieve(i);
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


}
