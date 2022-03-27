package application2;
import java.util.function.Consumer;
import UtilityClasses.CMD;
import adtImplementation.ArrayList;
import entity.Buyer;
import entity.Cart;
import entity.CartDetails;
import entity.OrderProduct;
import entity.Product;


public class CartDetailsView
{

    public static CartDetails cartDetails;

    static{        
        Cart cart = ((Buyer)App.currentUser).getCart();
        cartDetails = new CartDetails(cart);
        cartDetails.getCartDetails().add(new OrderProduct(new Product("ctitle", 20.5, "description"),7));        
        cartDetails.getCartDetails().add(new OrderProduct(new Product("atitle", 50.5, "description"),3));
        cartDetails.getCartDetails().add(new OrderProduct(new Product("stitle", 30.5, "description"),4));
        cart.addProduct(new OrderProduct(new Product("ctitle", 20.5, "description"),7));
        cart.addProduct(new OrderProduct(new Product("atitle", 50.5, "description"),3));
        cart.addProduct(new OrderProduct(new Product("stitle", 30.5, "description"),4));
    }

    static ArrayList<Option> options = new ArrayList<>
    (
        new Option[]
        {
              new Option(i->addQuantity()),
              new Option(i->decreaseQuantity()),
              new Option(i->removeProduct()),
              new Option(i->sortByTitle()),
              new Option(i->sortByPrice()),
              new Option(i->sortByQuantity()),
              new Option(i->goToPage(ii->PaymentView.main())),
        }
    );

    
    public static void addQuantity()
    {
        int productNo = App.promptIntInput("Select a product >> ");
        if (productNo > 0 && productNo <= cartDetails.getCartDetails().size())
        {
            int productQty = App.promptIntInput("How many want to add? >> ");
            boolean added = cartDetails.addQuantity(productNo-1, productQty);
            if (!added){
                CMD.pauseWithCustomScript("Error quantity! press any key to proceed");
            }
        }else {
            System.out.println("Please select a  valid product number");
        }
    }

    public static void decreaseQuantity(){
        int productNo = App.promptIntInput("Select a product >> ");
        if (productNo > 0 && productNo <= cartDetails.getCartDetails().size())
        {
            int productQty = App.promptIntInput("How many want to decrease? >> ");
            boolean decreased = cartDetails.decreaseQuantity(productQty, productNo-1);

            if (!decreased)
            {
                CMD.pauseWithCustomScript("Error quantity! press any key to proceed");
            }
        }else {
            System.out.println("Please select a valid product number");
        }
    }

    public static void sortByPrice(){                
        CartDetails.bubbleSortPrice(cartDetails.getCartDetails());
    }

    public static void sortByQuantity(){
        CartDetails.bubbleSortQuantity(cartDetails.getCartDetails());
    }

    public static void sortByTitle(){
        CartDetails.bubbleSortTitle(cartDetails.getCartDetails());
    }

    public static void removeProduct(){
        int productNo = App.promptIntInput("Select a product >> ");
        if (productNo > 0 && productNo <= cartDetails.getCartDetails().size())
        {            
            cartDetails.removeProduct(productNo);
        }else {
            System.out.println("Please select a valid product number");
        }
    }

    public static void main(String[] args) {
        main();
    }

    public static void main()
    {        
        // cartDetails.syncCartDetails();
                
        boolean endLoop = false;

        while (!endLoop)
        {
            System.out.println(CartDetailsView.cartDetails.toString());
            int option = App.promptIntInput("Please select an action >> ");

            if (option == 0 ){                
                App.goBack();
            }
            else if (option >= 1 && option<= 7)
            {   // not switch view
                App.goToUserOption(option, options);                
                CMD.cls();
            }            
            else
            {
                // error
                System.out.println("Please select a valid number");
            }
        }
    }

    public static void goToPage(Consumer<String> page){
        App.history.push(i -> main());
        page.accept("t");
    }

}
