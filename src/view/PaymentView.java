package view;
import java.util.function.Consumer;

import javax.swing.JOptionPane;

import adtImplementation.ArrayList;
import adtInterfaces.ListInterface;
import application.App;
import entity.Buyer;
import entity.Cart;
import entity.Payment;
import entity.Seller;


public class PaymentView {
    private static Cart cart=null;
    private static String selectedPaymentMethod="";
    private static final ListInterface<String> options = new ArrayList<String>();
    private static final String creditCard = "Credit Card";
    private static final String debitCard = "Debit Card";
    private static final String eWallet = "E-Wallet";

    static{                       
        options.add(creditCard);
        options.add(debitCard);
        options.add(eWallet);
    }

    public static void main(String[] args) {
        main(cart);
    }

    public static void main(Cart cart) {   
        PaymentView.cart=cart;     
        printTitle("Please select a payment method: ");                        
        
        App.printThroughList(options);  
        System.out.println(" 0    Back");
        promptOptions();
    }

    public static void printTitle(String title){
        App.clearScreen();
        System.out.println(title);        
        System.out.println();
    }

    public static void goToPage(Consumer<String> page){
        App.history.push(i -> main(cart));
        page.accept("t");
    }    

    public static void promptOptions(){               
        int method = App.promptIntInput("Enter your option: ");                
        switch(method){
            case 0: App.goBack();                
                break;
            case 1: getPaymentMethod(creditCard);
                selectedPaymentMethod = creditCard;
                break;
            case 2: getPaymentMethod(debitCard);
                selectedPaymentMethod = debitCard;
                break;
            case 3: getPaymentMethod(eWallet);
                selectedPaymentMethod = eWallet;
                break;
            default: 
                System.out.println("Please enter the option provided only.");
                System.out.println();
                promptOptions();
                break;
        }        
    }

    public static void getPaymentMethod(String paymentMethod){        
        Seller seller = new Seller(); // get seller from room
        seller.setUserName("userName");
        Buyer buyer = (Buyer)App.currentUser;        
        
        buyer.checkoutCart(paymentMethod, seller);
        selectedPaymentMethod = paymentMethod;
        updateCart();
        System.out.print("Error");
        JOptionPane.showMessageDialog(null, "Payment proceed successfully");
        

        // BuyerHomeView.main();
        App.goToHome();
    }

    
    public static void updateCart (){
        Payment py = new Payment(cart,selectedPaymentMethod);
        py.updateCart(PaymentView.cart.getCartID(), selectedPaymentMethod);
        //py.updateCart(PaymentView.cart.getCartID(), PaymentView.getPaymentMethod(selectedPaymentMethod));
    }
    
}