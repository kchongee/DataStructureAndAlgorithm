package application;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.Consumer;

import UtilityClasses.jdbcUtil;
import adtInterfaces.MapInterface;
import entity.*;
import adtImplementation.ArrayList;
import adtImplementation.LinkedStack;
import adtInterfaces.ListInterface;
import view.RoomViews.RoomViewExe;
import view.WelcomeView;

public class App {
    public static Scanner scanner = new Scanner(System.in);
    public static LinkedStack<Consumer<String>> history = new LinkedStack<Consumer<String>>();
    public static Account currentUser = new Account();
    public static Account buyer = new Buyer();
    public static Account seller = new Seller();
    public static AccountList accountList = new AccountList(100);    
    public static ListInterface<MapInterface<String, Object>> hashAccount = new ArrayList<>(100);
    public static ListInterface<MapInterface<String, Object>> hashNotifications = new ArrayList<>(100);
    public static Room chosenRoom =new Room();
    public static RoomViewExe roomViewExe;
    public static Integer sellerCreatedRoomID = 0;    

    public static void main(String[] args) throws Exception
    {
        retrieveAccounts();          
        WelcomeView.main();
    }     
    
    public static void retrieveAccounts(){
        hashAccount = jdbcUtil.readAll("SELECT * FROM Account;");

        for(int i=0;i<hashAccount.size();i++){      
            Account a = new Account(hashAccount.get(i).get("accountID"),
            hashAccount.get(i).get("userName"),
            hashAccount.get(i).get("userPwd"),
            hashAccount.get(i).get("name"),
            hashAccount.get(i).get("address"),
            hashAccount.get(i).get("email"),
            hashAccount.get(i).get("isSeller"));         
            accountList.addAccount(a);
        } 
    }

    public static boolean promptYesOrNo(String promptText){        
        System.out.print(promptText);
        String input = scanner.next();
        scanner.nextLine();           
        if(input.equalsIgnoreCase("y")){
            System.out.println();
            return true;
        }else if(input.equalsIgnoreCase("n")){
            return false;
        }else{
            System.out.println("Please enter Y or N only.");                
            System.out.println();
            return promptYesOrNo(promptText);
        }  
    }

    public static boolean promptTryAgain(){
        return promptYesOrNo("Do you want to try again? (Y|N): ");
    }    

    public static void nextLine() {  
        System.out.println();
    }  

    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }

    public static void menuHandler(ListInterface<Option> options){
        printMenuOption(options);
        promptUserInputOptionAndGo(options);
    }

    public static void printThroughList(ListInterface<?> list) {                
        if(!list.isEmpty()){
            for (int i=0;i<list.size();i++) {        
                System.out.printf("%2d    %s\n",i+1,list.get(i).toString());
            }            
        }            
    }  

    public static void printMenuOption(ListInterface<Option> options) {        
        System.out.println("========= Menu =========");
        if(!options.isEmpty()){
            for (int i=0;i<options.size();i++) {        
                System.out.printf("%2d    %s\n",i+1,options.get(i).getText());
            }            
        }    
        System.out.printf("%2d    %s\n",0,(history.isEmpty()?"Exit":"Back"));                          
        System.out.println("========================");        
    }    

    public static void promptUserInputOptionAndGo(ListInterface<Option> options) {     
        System.out.print("Enter your option: ");
        int inputOption = -1;
        try {
            inputOption = scanner.nextInt();            
            scanner.nextLine();
            goToUserOption(inputOption,options);         
        } catch (InputMismatchException ime) {
            scanner.next();
            System.out.println("Invalid input! Please enter number only.");
            System.out.println();
            promptUserInputOptionAndGo(options);
        }        
    }          

    public static void goToUserOption(int optionNum,ListInterface<Option> options){
        if(optionNum<0 || optionNum>options.size()){
            System.out.println("Invalid numbering! Please enter the numbering provided.");
            System.out.println();
            promptUserInputOptionAndGo(options);
        }else if(optionNum==0){
            goBack();
        }else{
            options.get(optionNum-1).execFunction();
        }
    }        

    public static void goToHome(){        
        while(history.size()>2){
            history.pop();
        }
        history.pop().accept("Back to home");
    }

    public static void goBack(){        
        if(history.isEmpty()){        
            System.out.println();        
            System.out.println("Thanks for using our sales system! Come again :)");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {                    
                e.printStackTrace();
            }
            clearScreen();
            System.exit(0);
        }
        history.pop().accept("Back to previous page");
    }
    
    public static int promptIntInput(String text){
        System.out.print(text);
        int inputInt = -1;                
        try {
            inputInt = scanner.nextInt();                                    
            scanner.nextLine();                                 
        } catch (InputMismatchException ime) {
            scanner.next();            
            System.out.println("Invalid input! Please enter number only.");
            System.out.println();            
            return promptIntInput(text);
        }        
        return inputInt;        
    }

    public static int promptIntInputSkippable(String text){
        System.out.print(text);
        int inputInt = -1;
        String inputIntStr = scanner.nextLine();
        if(checkIsNextLine(inputIntStr)){
            App.goBack();
        }else{
            try {
                inputInt = Integer.parseInt(inputIntStr);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter number only.");
                System.out.println();                
                return promptIntInput(text);
            }            
        } 
        return inputInt; 
    }
    
    public static double promptDoubleInput(String text){
        System.out.print(text);
        double inputDouble = -1;                
        try {
            inputDouble = scanner.nextDouble();            
            scanner.nextLine();          
        } catch (InputMismatchException ime) {   
            scanner.next();         
            System.out.println("Invalid input! Please enter (decimal) number only.");                            
                System.out.println();                
            return promptDoubleInput(text);
        }
        return inputDouble;
    }

    public static double promptDoubleInputSkippable(String text){
        System.out.print(text);
        double inputDouble = -1;                        
        String inputDoubleStr = scanner.nextLine();
        if(checkIsNextLine(inputDoubleStr)){
            App.goBack();
        }else{                     
            try {
                inputDouble = Double.parseDouble(inputDoubleStr);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter (decimal) number only.");
                System.out.println();                
                return promptDoubleInput(text);
            }            
        }                    
        return inputDouble;
    }

    public static LocalDate promptDateInput(String text){
        String format = "(yyyy-MM-dd)";
        System.out.print(text+" "+format+": ");
        String inputDate = "";                
        LocalDate date = LocalDate.now();
        try {
            inputDate = scanner.nextLine();
            date = LocalDate.parse(inputDate); 
        } catch (DateTimeParseException dtpe) {
            System.out.println("Invalid format inserted, please follow the format given "+format);
            System.out.println();
            return promptDateInput(text);
        }                               
        return date;
    }

    public static LocalTime promptTimeInput(String text){
        String format = "(hh:mm:ss)";
        System.out.print(text+" "+format+": ");
        String inputTime = ""; 
        LocalTime time = LocalTime.now();                  
        try {
            inputTime = scanner.nextLine();
            time = LocalTime.parse(inputTime);
        } catch (DateTimeParseException dtpe) {            
            System.out.println("Invalid format inserted, please follow the format given "+format);
            System.out.println();
            return promptTimeInput(text);
        }                                
        return time;
    }    

    public static String promptStringInput(String text){
        System.out.print(text);
        String inputStr = "";
        try {            
            inputStr = scanner.nextLine();
        } catch (InputMismatchException ime) {            
            System.out.println("Invalid input! Please enter string only.");
            System.out.println();
            return promptStringInput(text);
        }        
        return inputStr;
    }

    public static boolean checkIsNextLine(String str){
        return str.replaceAll("[\\r\\n]+\\s", "").equals("");
    }

    public static void printTitle(String title){
        clearScreen();
        System.out.println(title);
        System.out.println();
    }

    public static String trimString(String s, int maxlength){
        int trimLength = 3;        
        trimLength += s.length()-maxlength;
        
        return s = 
        ( s.length () > maxlength && maxlength > 3) 
            ? s.substring( 0 , s.length() - trimLength ).concat ( ".." ) 
            : s;        
    }
}