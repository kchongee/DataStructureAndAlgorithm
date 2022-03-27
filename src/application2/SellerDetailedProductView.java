package application2;

import java.util.function.Consumer;

import adtImplementation.ArrayList;
import adtImplementation.LinkedDeque;
import adtInterfaces.DequeInterface;
import adtInterfaces.ListInterface;
import entity.Product;
import entity.Seller;

public class SellerDetailedProductView {
    public static ListInterface<Option> menuOptions = new ArrayList<Option>();
    public static DequeInterface<Product> detailedProducts = new LinkedDeque<>();
    private static Product currentProduct;
    
    static {                
        menuOptions.add(new Option("Next ->", i -> nextPage()));
        menuOptions.add(new Option("Prev <-", i -> prevPage()));
        menuOptions.add(new Option("Edit this Product", i -> editProduct()));
    }

    public static void main(String[] args) {
        main();
    }
    
    public static void main() {
        fetchProductList();

        printCurrentProduct();
    }

    public static void printCurrentProduct(){
        currentProduct = detailedProducts.peekFirst();            
        App.clearScreen();
        System.out.println("Product "+currentProduct.getTitle());
        System.out.printf("Price: RM %.2f\n",currentProduct.getPrice());
        System.out.printf("Description: %s\n",currentProduct.getDescription());
        System.out.println();
        
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

    public static void fetchProductList(){
        detailedProducts.clear();
        Object[] objs = ((ArrayList)((Seller)App.currentUser).getProducts()).toArray();
        for(Object obj: objs){            
            detailedProducts.addLast((Product)obj);
        }        
    }

    public static void nextPage(){        
        detailedProducts.addLast(detailedProducts.pollFirst());
        clearAndPrintMainAgain();
    }

    public static void prevPage(){
        detailedProducts.addFirst(detailedProducts.pollLast());
        clearAndPrintMainAgain();
    }

    public static void clearAndPrintMainAgain(){
        App.clearScreen();
        printCurrentProduct();
    }

    public static void editProduct(){
        App.clearScreen();
        System.out.println("Current Product Title: "+currentProduct.getTitle());
        String newProductTitleStr = checkUserIfUserWannaEdit(
                App.promptStringInput("New Product Title (press Enter to skip): ")
            );        
        currentProduct.setTitle(newProductTitleStr!=null?newProductTitleStr:currentProduct.getTitle());
        System.out.println();

        askForProductPrice();

        System.out.println("Current Product Description: "+currentProduct.getDescription());
        String newProductDescriptionStr = checkUserIfUserWannaEdit(
                App.promptStringInput("New Product Description (press Enter to skip): ")
            );        
        currentProduct.setDescription(newProductDescriptionStr!=null?newProductDescriptionStr:currentProduct.getDescription());
        System.out.println();

        System.out.println(currentProduct);
        printCurrentProduct();
    }

    public static String checkUserIfUserWannaEdit(String str){
        if(str.replaceAll("[\\r\\n]+\\s", "").equals("")){
            return null;
        }else{
            return str;
        }
    }

    public static void askForProductPrice(){
        double newProductPrice = 0;

        System.out.println("Current Product Price: RM "+currentProduct.getPrice());
        String newProductPriceStr = checkUserIfUserWannaEdit(
                App.promptStringInput("New Product Price (press Enter to skip): ")
            );

        if(newProductPriceStr!=null){
            try {
                newProductPrice = Double.parseDouble(newProductPriceStr);
                currentProduct.setPrice(newProductPrice);
            } catch (NumberFormatException e) {
                System.out.println("Please enter number only!");                
                System.out.println();
                askForProductPrice();
                return;
            }            
        }
        System.out.println();
    }
}
