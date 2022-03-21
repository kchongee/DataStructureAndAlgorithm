package view;

import UtilityClasses.CMD;
import adtImplementation.ArrayList;
import application.App;
import entity.Account;
import entity.Cart;
import entity.CartDetails;
import entity.Option;

public class CartDetailsView
{

    CartDetails cartDetails;

    ArrayList<Option> options = new ArrayList<>
    (
        new Option[]
        {
              new Option(i->addQuantity()),
              new Option(i->decreaseQuantity()),
              new Option(i->removeProduct()),
              new Option(i->sortByTitle()),
              new Option(i->sortByPrice()),
              new Option(i->sortByQty()),
              //new Option(i->checkout()),
              //new Option(i->goBack()),



        }
    );

    public CartDetailsView(CartDetails cartDetails) {
        this.cartDetails = cartDetails;
    }

//    public static void main(Cart cart)
//    {
//        // pass cart to here from cart list [seller ID + cartID + username]
//
//        // test cart
//        Cart cartTest = new Cart(1, new Account("A50","chailey1d"));
//
//
//        CartDetails cartDetails = new CartDetails(cartTest);
//        CartDetailsView view = new CartDetailsView();
//
//
//        System.out.println(cart.getProductList().toString());
//    }
    public void addQuantity()
    {
        int productNo = App.promptIntInput("Select a product >> ");
        if (productNo > 0 && productNo <= cartDetails.getCartDetails().size())
        {
            int productQty = App.promptIntInput("How many want to add? >> ");
            boolean added =this.cartDetails.addQuantity(productNo-1, productQty);
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
            boolean decreased =this.cartDetails.decreaseQuantity(productQty, productNo-1);

            if (!decreased)
            {
                CMD.pauseWithCustomScript("Error quantity! press any key to proceed");
            }
        }else {
            System.out.println("Please select a valid product number");
        }
    }

    public void sortByTitle(){
        CartDetails.bubbleSortTitle(cartDetails.getCartDetails());
    }

    public void sortByPrice(){
        CartDetails.bubbleSortPrice(cartDetails.getCartDetails());
    }

    public void sortByQty(){
        CartDetails.bubbleSortQuantity(cartDetails.getCartDetails());
    }

    public void removeProduct(){
        int productNo = App.promptIntInput("Select a product you want to remove? >> ");
        if (productNo > 0 && productNo <= cartDetails.getCartDetails().size())
        {
            boolean removed =this.cartDetails.removeProductFromCart (productNo-1);
            
            if (!removed)
            {
                CMD.pauseWithCustomScript("Error product index! press any key to proceed");
            }
        }else {
            System.out.println("Please select a valid product number");
        }
    }


    public static void main(String[] args)
    {
        // pass cart to here from cart list [seller ID + cartID + username]

        // test cart
        Cart cartTest = new Cart(1, new Account("A50","chailey1d"));
        CartDetails cartDetails = new CartDetails(cartTest);  // maybe cart no need cart details
        cartDetails.syncCartDetails();

        CartDetailsView view = new CartDetailsView(cartDetails);
        boolean endLoop = false;

        while (!endLoop)
        {
            System.out.println(view.cartDetails.toString());
            int option = App.promptIntInput("Please select an action >> ");

            if (option >= 1 && option<= 6)
            {   // not switch view
                App.goToUserOption(option, view.options);
                CMD.cls();
            }
            else if (option == 7)
            {
                // will switch to payment view
                endLoop = true;
                App.goToUserOption(option, view.options);
            }
            else
            {
                App.goBack();

            }
        }
    }

}
