package application;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.Consumer;

import adtImplementation.Account;
import adtImplementation.ArrayListEe;
import adtImplementation.StackLinkedList;
import adtInterfaces.ListInterfaceEe;
import entity.ConsoleMenu;
import entity.ConsoleMenuOption;
import entity.Follower;
import entity.Order;
import entity.OrderProduct;
import entity.Product;
import entity.Room;

public class App {
    public static Scanner scanner = new Scanner(System.in);
    public static StackLinkedList<Consumer<String>> history = new StackLinkedList<Consumer<String>>();
    public static void main(String[] args) throws Exception {        
        // ConsoleMenuOption backMenuOption = new ConsoleMenuOption("Back", i -> goBack());
        Account currentUser = new Account();

        //#region : Seller Home page
        ListInterfaceEe<ConsoleMenuOption> homeMenuOptions = new ArrayListEe<ConsoleMenuOption>();
        Consumer<String> homePage = i -> {
            clearScreen();
            printMenuOption(homeMenuOptions);
            promptUserInputOptionAndGo(homeMenuOptions);
        };
        //#endregion : Seller Home page
        
        //#region : Welcome page
        ListInterfaceEe<ConsoleMenuOption> welcomeMenuOptions = new ArrayListEe<ConsoleMenuOption>();
        Consumer<String> welcomePage = i -> {
            clearScreen();
            printMenuOption(welcomeMenuOptions);
            promptUserInputOptionAndGo(welcomeMenuOptions);
        };
        welcomeMenuOptions.add(new ConsoleMenuOption("Login", i -> {
            history.push(welcomePage);
            boolean tryAgain = true;
            do{
                String id = promptStringInput("Username: ");
                String password = promptStringInput("Password: ");
                if(id.equals(password)){ 
                    // homePage.accept("t");
                }else{
                    tryAgain = promptTryAgain();   
                }
            }while(tryAgain==true);         
            goBack();
        }));
        welcomeMenuOptions.add(new ConsoleMenuOption("Register", i -> {            
            history.push(welcomePage);            
            boolean tryAgain = true;
            do{
                String id = promptStringInput("Username: ");
                String password = promptStringInput("Password: ");
                if(id.equals(password)){
                    // homePage.accept("t");                  
                }else{
                    tryAgain = promptTryAgain();   
                }                    
            }while(tryAgain==true);
            goBack();        
        }));        
        //#endregion : Welcome page
                
        welcomePage.accept("t");
    }
    
    

    public static void promptContinue(){
        System.out.println("Do you want to ");
    }

    public static boolean promptTryAgain(){
        System.out.print("Do you want to try again? (Y|N): ");
        String input = scanner.next();             
        if(input.toLowerCase().equals("y")){
            System.out.println();
            return true;
        }else if(input.toLowerCase().equals("n")){            
            return false;
        }
        System.out.println("Please enter Y or N only.");                
        System.out.println();
        promptTryAgain();                    
        return false;
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

    public static void nextLine() {  
        System.out.println();
    }  

    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }

    public static void printAndPromptAndGoToUserMenuOption(ListInterfaceEe<ConsoleMenuOption> options) {
        printMenuOption(options);
        promptUserInputOptionAndGo(options);        
    }

    public static int printAndPromptUserMenuOption(ListInterfaceEe<ConsoleMenuOption> options) {
        printMenuOption(options);
        return promptUserInputOption();
    }

    public static void printMenuOption(ListInterfaceEe<ConsoleMenuOption> options) {        
        System.out.println("========= Menu =========");
        if(!options.isEmpty()){
            for (int i=0;i<options.size();i++) {        
                System.out.printf("%2d    %s\n",i+1,options.retrieve(i).getText());
            }            
        }    
        System.out.printf("%2d    %s\n",0,(history.isEmpty()?"Exit":"Back"));                          
        System.out.println("========================");        
    }

    public static int promptUserInputOption() {        
        System.out.print("Enter your option: ");
        int inputOption = -1;
        try {
            inputOption = scanner.nextInt();            
        } catch (InputMismatchException ime) {
            scanner.next();
            System.out.println("Invalid input! Please enter number only.");
            System.out.println();
            promptUserInputOption();
        }       
        return inputOption;         
    }

    public static int promptUserInputOption(ListInterfaceEe<ConsoleMenuOption> options) {
        System.out.print("Enter your option: ");
        int inputOption = -1;
        try {
            inputOption = scanner.nextInt();            
        } catch (InputMismatchException ime) {
            scanner.next();
            System.out.println("Invalid input! Please enter number only.");
            System.out.println();
            promptUserInputOption(options);
        }     
        return inputOption;           
    }

    public static void promptUserInputOptionAndGo(ListInterfaceEe<ConsoleMenuOption> options) {     
        System.out.print("Enter your option: ");
        int inputOption = -1;
        try {
            inputOption = scanner.nextInt();
            clearScreen();
            goToUserOption(inputOption,options);         
        } catch (InputMismatchException ime) {
            scanner.next();
            System.out.println("Invalid input! Please enter number only.");
            System.out.println();
            promptUserInputOptionAndGo(options);
        }        
    }        

    public static void goToUserOption(int optionNum,ListInterfaceEe<ConsoleMenuOption> options){
        if(optionNum<0 || optionNum>options.size()){
            System.out.println("Invalid numbering! Please enter the numbering provided.");
            System.out.println();
            promptUserInputOption();
        }else if(optionNum==0){
            goBack();
        }else{
            options.retrieve(optionNum-1).execFunction();
        }
    }
    
    public static Integer promptIntInput(String text){
        System.out.print(text);
        int inputInt = -1;
        try {
            inputInt = scanner.nextInt();                                    
        } catch (InputMismatchException ime) {
            scanner.next();            
            System.out.println("Invalid input! Please enter number only.");
            System.out.println();            
            promptIntInput(text);
        }        
        return inputInt;
    }
    
    public static Double promptDoubleInput(String text){
        System.out.print(text);
        double inputDouble = -1;                
        try {
            inputDouble = scanner.nextDouble();            
        } catch (InputMismatchException ime) {   
            scanner.next();         
            System.out.println("Invalid input! Please enter (decimal) number only.");
            System.out.println();
            promptDoubleInput(text);
        }
        return inputDouble;
    }

    public static LocalDate promptDateInput(String text){
        String format = "(yyyy-MM-dd)";
        System.out.print(text+" "+format);
        String inputDate = "";                
        LocalDate date = LocalDate.now();
        try {
            inputDate = scanner.nextLine();
            date = LocalDate.parse(inputDate); 
        } catch (DateTimeParseException dtpe) {
            System.out.println("Invalid format inserted, please follow the format given "+format);
            System.out.println();
            promptDateInput(text);
        }                               
        return date;
    }

    public static LocalTime promptTimeInput(String text){
        String format = "(hh:mm:ss)";
        System.out.print(text+" "+format);
        String inputTime = ""; 
        LocalTime time = LocalTime.now();                  
        try {
            inputTime = scanner.nextLine();
            time = LocalTime.parse(inputTime);
        } catch (DateTimeParseException dtpe) {            
            System.out.println("Invalid format inserted, please follow the format given "+format);
            System.out.println();
            promptTimeInput(text);
        }                                
        return time;
    }    

    public static String promptStringInput(String text){
        System.out.print(text);
        String inputStr = "";
        try {            
            inputStr = scanner.next();
        } catch (InputMismatchException ime) {            
            System.out.println("Invalid input! Please enter string only.");
            System.out.println();
            promptStringInput(text);
        }        
        return inputStr;
    }
}