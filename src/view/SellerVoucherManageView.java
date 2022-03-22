package view;

import java.util.function.Consumer;

import adtImplementation.ArrayList;
import adtInterfaces.ListInterface;
import application.App;
import entity.Option;
import entity.Seller;
import entity.Voucher;

public class SellerVoucherManageView{

    public static ListInterface<Option> menuOptions = new ArrayList<Option>();
    public static Voucher voucher;

    static{
        menuOptions.add(new Option("Edit Voucher", i->goToPage(ii->editVoucher())));        
        voucher = ((Seller)App.currentUser).getVoucher();
    }

    public static void main(String[] args) {
        main();
    }
    
    public static void main() {                      
        printTitle("Manage Voucher");
        
        System.out.println(String.format("Voucher release status: %s",(voucher.isReleased()?"On":"Off")));
        System.out.println(String.format("Customer min. spend: RM %.2f",voucher.getMinSpend()));
        System.out.println(String.format("Voucher discount: %.0f%%",voucher.getDiscountPercentage()));
        
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

    public static void editVoucher(){
        App.clearScreen();
        
        askVoucherReleaseStatus();

        askForMinSpend();
                                        
        askForDiscountPercent();

        App.goBack();
    }    

    public static String checkUserIfUserWannaEdit(String str){
        if(str.replaceAll("[\\r\\n]+\\s", "").equals("")){
            return null;
        }else{
            return str;
        }
    }            

    public static void askVoucherReleaseStatus(){
        System.out.println(String.format("Voucher release status: %s",(voucher.isReleased()?"On":"Off")));                
        String newVoucherReleaseStatusStr = checkUserIfUserWannaEdit(
                App.promptStringInput(String.format("Turn %s Voucher release? (Y or N, else press Enter to skip): ",(!voucher.isReleased()?"On":"Off")))            
            );
        
        if(newVoucherReleaseStatusStr == null){
            System.out.println();
            return;
        }
        if(!newVoucherReleaseStatusStr.equalsIgnoreCase("y") && !newVoucherReleaseStatusStr.equalsIgnoreCase("n")){
            System.out.println("Please enter Y or N only! Else press Enter to skip!");
            System.out.println();
            askVoucherReleaseStatus();
        }                    
        System.out.println();
        voucher.setReleased(newVoucherReleaseStatusStr.equalsIgnoreCase("y"));
    }

    public static void askForMinSpend(){
        double newMinSpend = 0;

        System.out.println(String.format("Customer min. spend: RM %.2f",voucher.getMinSpend()));
        String newVoucherMinSpendStr = checkUserIfUserWannaEdit(
                App.promptStringInput("New customer min. spend (press Enter to skip): RM ")
            );

        if(newVoucherMinSpendStr!=null){
            try {
                newMinSpend = Double.parseDouble(newVoucherMinSpendStr);
                voucher.setMinSpend(newMinSpend);
            } catch (NumberFormatException e) {
                System.out.println("Please enter number only!");                
                System.out.println();
                askForMinSpend();
                return;
            }            
        }
        System.out.println();
    }

    public static void askForDiscountPercent(){
        double newDiscountPercent = 0;

        System.out.println(String.format("Voucher discount: %.0f%%",voucher.getDiscountPercentage()));
        String newDiscountPercentStr = checkUserIfUserWannaEdit(
                App.promptStringInput("New voucher discount(%) (press Enter to skip): ")
            );

        if(newDiscountPercentStr!=null){
            try {
                newDiscountPercent = Double.parseDouble(newDiscountPercentStr);
                voucher.setDiscountPercentage(newDiscountPercent);
            } catch (NumberFormatException e) {
                System.out.println("Please enter number only!");
                System.out.println();
                askForDiscountPercent();
                return;
            }            
        }
        System.out.println();
    }
}
