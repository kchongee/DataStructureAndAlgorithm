package view;
import UtilityClasses.CMD;
import application.App;
import entity.Account;
import entity.CartList;
import entity.Buyer;
import entity.Cart;

import javax.swing.*;

public class CartListView
{
    CartList cartList;
    Account buyer;

    public CartListView(Account account)
    {
        this.buyer = account;
        this.cartList = new CartList(buyer);
        this.cartList.syncCart();
    }

    //String[] args
    public static void main() 
    {
        Buyer buyer = (Buyer)App.currentUser;  
        CartListView view = new CartListView(buyer);
        int maxChoice = view.cartList.getCart().size();
        

        while (true)
        {
            CMD.cls();
            System.out.println(view.cartList.toString());
            int userChoice = App.promptIntInput("Please select a cart >> ");
            boolean containError = userChoice <= 0 || userChoice > maxChoice;

            if (containError)
            {
                JOptionPane.showMessageDialog(null, "Please enter number between 1 and " + maxChoice, "Warning", JOptionPane.WARNING_MESSAGE);
            }
            
            else{
                Cart cartSelected = view.cartList.getCart().get(userChoice-1);
                CartDetailsView.main(cartSelected);
                break;
            }
        }
    }
}