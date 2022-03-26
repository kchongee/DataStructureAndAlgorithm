package view;
import java.util.function.Consumer;

import javax.swing.JOptionPane;

import adtImplementation.ArrayList;
import adtInterfaces.ListInterface;
import application.App;
import entity.Buyer;
import entity.Payment;
import entity.Seller;


public class PaymentView {
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
        main();
    }

    public static void main() {        
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
        App.history.push(i -> main());
        page.accept("t");
    }    

    public static void promptOptions(){               
        int method = App.promptIntInput("Enter your option: ");                
        switch(method){
            case 0: App.goBack();                
                break;
            case 1: getPaymentMethod(creditCard);
                break;
            case 2: getPaymentMethod(debitCard);
                break;
            case 3: getPaymentMethod(eWallet);
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
        JOptionPane.showMessageDialog(null, "Payment proceed successfully");

        // BuyerHomeView.main();
        App.goToHome();
    }

    public static void updateCart (int cartID){
        //Payment.updateCart(cartID);
    }
}