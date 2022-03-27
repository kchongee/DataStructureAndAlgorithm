package view;
import java.util.function.Consumer;
import UtilityClasses.CMD;
import adtImplementation.ArrayList;
import application.App;
import application.Option;
import entity.Buyer;
import entity.Cart;
import entity.CartDetails;


public class CartDetailsView
{

    public static CartDetails cartDetails;
    Cart cart;

    ArrayList<Option> options = new ArrayList<>
    (
        new Option[]
        {
              new Option(i->addQuantity()),
              new Option(i->decreaseQuantity()),
              new Option(i->removeProduct()),
              new Option(i->sortByTitle()),
              new Option(i->sortByPrice()),
              new Option(i->sortByQuantity()),
              new Option(i->goToPage(cart,ii->PaymentView.main(cart))),
        }
    );

    public CartDetailsView(CartDetails cartDetails) {
        CartDetailsView.cartDetails = cartDetails;
    }

    public void addQuantity()
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

    public void decreaseQuantity(){
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

    public void sortByPrice(){                
        CartDetails.bubbleSortPrice(cartDetails.getCartDetails());
    }

    public void sortByQuantity(){
        CartDetails.bubbleSortQuantity(cartDetails.getCartDetails());
    }

    public void sortByTitle(){
        CartDetails.bubbleSortTitle(cartDetails.getCartDetails());
    }

    public void removeProduct(){
        int productNo = App.promptIntInput("Select a product >> ");
        if (productNo > 0 && productNo <= cartDetails.getCartDetails().size())
        {            
            cartDetails.removeProductFromCart(productNo);
        }else {
            System.out.println("Please select a valid product number");
        }
    }

    public static void main(Cart cart)
    {
        
        //Buyer buyer = (Buyer)App.currentUser;
        CartDetails cartDetails = new CartDetails(cart);  // maybe cart no need cart details
        cartDetails.syncCartDetails();
        

        CartDetailsView view = new CartDetailsView(cartDetails);
        view.cart=cart;
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
                App.goToUserOption(option, view.options);                
                CMD.cls();
            }            
            else
            {
                System.out.println("Please select a valid number");
            }
        }
    }

    public static void goToPage(Cart cart, Consumer<String> page){
        App.history.push(i -> main(cart));
        page.accept("t");
    }

}
