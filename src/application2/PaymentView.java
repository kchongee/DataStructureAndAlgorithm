package application2;
import java.util.function.Consumer;

import adtImplementation.ArrayList;
import adtInterfaces.ListInterface;
import entity.Buyer;
import entity.Seller;


public class PaymentView {
    private static final ListInterface<String> options = new ArrayList<String>();
    private static final String creditCard = "Credit Card";
    private static final String bankAccount = "Bank Account";
    private static final String eWallet = "E-Wallet";

    static{                       
        options.add(creditCard);
        options.add(bankAccount);
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
            case 2: getPaymentMethod(bankAccount);
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
        Seller seller = (Seller)App.seller;        
        Buyer buyer = (Buyer)App.currentUser;        
        
        buyer.checkoutCart(paymentMethod, seller);        

        // BuyerHomeView.main();
        App.goToHome();
    }
}