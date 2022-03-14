package application;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.Consumer;

import adtImplementation.Account;
import adtImplementation.HashMap;
import adtImplementation.HashMapEe;
import adtImplementation.StackLinkedList;
import adtInterfaces.ListInterface;
import entity.Option;
import view.WelcomeView;

public class App {
    public static Scanner scanner = new Scanner(System.in);
    public static StackLinkedList<Consumer<String>> history = new StackLinkedList<Consumer<String>>();
    public static Account currentUser = new Account();
    public static void main(String[] args) throws Exception {
        WelcomeView.main();
        // HashMapEe<Integer,String> a = new HashMapEe<>();
        // a.put(1, "k");
        // a.put(2, "o");
        // a.put(3, "u");
        // System.out.println(a.get(1));
        // System.out.println(a.get(2));
        // System.out.println(a.get(3));
        // a.remove(1);
        // System.out.println(a.get(1));
        // a.put(1, "u");
        // System.out.println(a.get(1));
        // System.out.println(a.get(2)+"-->"+a.get(1));
    }        

    public static boolean promptYesOrNo(String promptText){        
        System.out.print(promptText);
        String input = scanner.next();
        scanner.nextLine();           
        if(input.toLowerCase().equals("y")){
            System.out.println();
            return true;
        }else if(input.toLowerCase().equals("n")){            
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
    
    // public static void menuHandler(HashMap<String, Product> catalog){
    //     printMenuOption(catalog);
    //     promptUserInputOptionAndGo();
    // }

    // public static void printThroughList(ListInterface<?> list) {                
    //     if(!list.isEmpty()){
    //         for (int i=0;i<list.size();i++) {        
    //             System.out.printf("%2d    %s\n",i+1,list.get(i).toString());
    //         }            
    //     }            
    // }  

    // public static void printMenuOption(HashMap<String, Product> catalog) {        
    //     System.out.println("========= Menu =========");
    //     if(!catalog.getKey()){
    //         for (int i=0;i<catalog.size();i++) {        
    //             System.out.printf("%2d    %s\n",i+1,catalog.get(i).getText());
    //         }            
    //     }    
    //     System.out.printf("%2d    %s\n",0,(history.isEmpty()?"Exit":"Back"));                          
    //     System.out.println("========================");        
    // }    

    // public static void promptUserInputOptionAndGo(ListInterface<Option> options) {     
    //     System.out.print("Enter your option: ");
    //     int inputOption = -1;
    //     try {
    //         inputOption = scanner.nextInt();            
    //         goToUserOption(inputOption,options);         
    //     } catch (InputMismatchException ime) {
    //         scanner.next();
    //         System.out.println("Invalid input! Please enter number only.");
    //         System.out.println();
    //         promptUserInputOptionAndGo(options);
    //     }        
    // }          

    // public static void goToUserOption(int optionNum,ListInterface<Option> options){
    //     if(optionNum<0 || optionNum>options.size()){
    //         System.out.println("Invalid numbering! Please enter the numbering provided.");
    //         System.out.println();
    //         promptUserInputOptionAndGo(options);
    //     }else if(optionNum==0){
    //         goBack();
    //     }else{
    //         options.get(optionNum-1).execFunction();
    //     }
    // }

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
    
    public static Integer promptIntInput(String text){
        System.out.print(text);
        int inputInt = -1;
        try {
            inputInt = scanner.nextInt();                                    
            scanner.nextLine();                                 
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
            scanner.nextLine();          
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
            inputStr = scanner.nextLine();
        } catch (InputMismatchException ime) {            
            System.out.println("Invalid input! Please enter string only.");
            System.out.println();
            promptStringInput(text);
        }        
        return inputStr;
    }
}