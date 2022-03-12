package entity;
import java.io.Console;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.function.Consumer;

import adtImplementation.ArrayListEe;
import application.App;
// import adtInterfaces.ListInterface;
import adtInterfaces.ListInterfaceEe;

public class ConsoleMenu<T>{    
    public final int DEFAULT_INT_VALUE = -1;
    private int userInput = DEFAULT_INT_VALUE;
    private String title;
    private ListInterfaceEe<ConsoleMenuOption> options;
    private ListInterfaceEe<T> additionalDetails;
    private Consumer<T> customPrinting;
    private int customPrintingOrder;

    public ConsoleMenu(){
        reset();
    }

    public ConsoleMenu(String title){
        this();
        this.title = title;                
    }

    public ConsoleMenu(ConsoleMenuOption... options){
        this();
        this.options.addAll(options);
    }

    public ConsoleMenu(String title,ConsoleMenuOption... options){
        this();
        this.title = title;        
        this.options.addAll(options);
    }    

    public ConsoleMenu(String title, T[] additionalDetails, ConsoleMenuOption... options){
        this(title, options);
        this.additionalDetails.addAll(additionalDetails);
    }

    public ConsoleMenu(String title, ListInterfaceEe<T> additionalDetails, ConsoleMenuOption... options){
        this(title, options);
        this.additionalDetails = additionalDetails;
    }

    public ConsoleMenu<T> reset(){
        this.title = null;        
        this.options = new ArrayListEe<ConsoleMenuOption>();
        this.additionalDetails = new ArrayListEe<T>();        
        this.customPrinting = null;
        this.customPrintingOrder = 1;
        return this;
    }

    public ConsoleMenu<T> setTitle(String title) {
        this.title = title;
        return this;
    }    

    public ListInterfaceEe<ConsoleMenuOption> getOptions() {
        return options;
    }    

    public ConsoleMenu<T> setCustomPrintingAtOrder(Consumer<T> customPrinting, int order) {
        this.customPrinting = customPrinting;
        this.customPrintingOrder = order;
        return this;
    }

    public void printAPage(){   
        clearScreen();        

        if(title!=null){
            System.out.println(title);
            System.out.println();
        }
        
        if(customPrinting!=null && customPrintingOrder==1){
            customPrinting.accept((T)customPrinting);
            System.out.println();
        }

        printAdditionalDetails();

        if(customPrinting!=null && customPrintingOrder==2){
            customPrinting.accept((T)customPrinting);
            System.out.println();
        }

        printMenu();        

        promptUserInputOption();
    }

    public ConsoleMenu<T> printAdditionalDetails() {
        if(!this.additionalDetails.isEmpty()){
            for (int i=0;i<this.additionalDetails.size();i++) {        
                System.out.printf("%2d.   %s\n",i+1,additionalDetails.retrieve(i).toString());
            }            
        }    
        System.out.println();         
        return this;
    }
    
    public ConsoleMenu<T> printMenu() {           
        System.out.println("========= Menu =========");
        if(!this.options.isEmpty()){
            for (int i=0;i<this.options.size();i++) {        
                System.out.printf("%2d    %s\n",i+1,options.retrieve(i).getText());
            }            
        }    
        System.out.printf("%2d    %s\n",0,(App.history.isEmpty()?"Exit":"Back"));                          
        System.out.println("========================");
        return this;
    }            
    
    public ConsoleMenu<T> promptUserInputOption() {     
        System.out.print("Enter your option: ");
        int inputOption = -1;
        try {
            inputOption = App.scanner.nextInt();
            goToUserOption(inputOption);         
        } catch (InputMismatchException ime) {
            App.scanner.next();
            System.out.println("Invalid input! Please enter number only.");
            System.out.println();
            promptUserInputOption();
        }        
        return this;
    }

    public void goToUserOption(int optionNum){        
        if(optionNum<0 || optionNum>this.options.size()){
            System.out.println("Invalid numbering! Please enter the numbering provided.");
            System.out.println();
            promptUserInputOption();   
        }else if(optionNum==0){
            if(App.history.isEmpty()){        
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
            App.history.pop().accept("Back to previous page");
        }else{
            App.history.push(i -> this.printAPage());
            System.out.println(this.options.size());
            this.options.retrieve(optionNum-1).execFunction();
        }
    }

    public int promptUserInputOptionBasedOnAdditionalDetails() {     
        System.out.print("Enter your option: ");
        int inputOption = -1;
        try {
            inputOption = App.scanner.nextInt();
        } catch (InputMismatchException ime) {
            App.scanner.next();
            System.out.println("Invalid input! Please enter number only.");
            System.out.println();
            promptUserInputOptionBasedOnAdditionalDetails();
        }        
        return inputOption;
    }

    public int askUserInput(String title){
        System.out.println(title);
        userInput = App.scanner.nextInt();
        return userInput;
    }    

    public String promptStringInput(String text){
        System.out.print(text);
        String inputStr = "";
        try {
            inputStr = App.scanner.nextLine();
        } catch (InputMismatchException ime) {            
            System.out.println("Invalid input! Please enter string only.");
            System.out.println();
            promptStringInput(text);
        }        
        return inputStr;
    }
    
    public Integer promptIntInput(String text){
        System.out.print(text);
        int inputInt = -1;
        try {
            inputInt = App.scanner.nextInt();                                    
        } catch (InputMismatchException ime) {
            App.scanner.next();            
            System.out.println("Invalid input! Please enter number only.");
            System.out.println();            
            promptIntInput(text);
        }        
        return inputInt;
    }
    
    public Double promptDoubleInput(String text){
        System.out.print(text);
        double inputDouble = -1;                
        try {
            inputDouble = App.scanner.nextDouble();            
        } catch (InputMismatchException ime) {   
            App.scanner.next();         
            System.out.println("Invalid input! Please enter (decimal) number only.");
            System.out.println();
            promptDoubleInput(text);
        }
        return inputDouble;
    }

    public LocalDate promptDateInput(String text){
        String format = "(yyyy-MM-dd)";
        System.out.print(text+" "+format);
        String inputDate = "";                
        LocalDate date = LocalDate.now();
        try {
            inputDate = App.scanner.nextLine();
            date = LocalDate.parse(inputDate); 
        } catch (DateTimeParseException dtpe) {
            System.out.println("Invalid format inserted, please follow the format given "+format);
            System.out.println();
            promptDateInput(text);
        }                               
        return date;
    }

    public LocalTime promptTimeInput(String text){
        String format = "(hh:mm:ss)";
        System.out.print(text+" "+format);
        String inputTime = ""; 
        LocalTime time = LocalTime.now();                  
        try {
            inputTime = App.scanner.nextLine();
            time = LocalTime.parse(inputTime);
        } catch (DateTimeParseException dtpe) {            
            System.out.println("Invalid format inserted, please follow the format given "+format);
            System.out.println();
            promptTimeInput(text);
        }                                
        return time;
    }    

    // public String confirmQuitFromInput(){
    //     System.out.print("Are you sure you want to quit the progress? (Y|N): ");
    //     String inputStr = App.scanner.nextLine();
    //     if(inputStr=="Y"){
    //         goToUserOption(0);
    //     }else if(inputStr=="N"){

    //     }else{
    //         System.out.println("Invalid input! Please enter Y or N only.");
    //         System.out.println();
    //         confirmQuitFromInput();            
    //     }        
    //     return inputStr;
    // }

    public void clearScreen(){
        System.out.print("\033[H\033[2J");  
        System.out.flush();
    }

    // public ConsoleMenu addLastOption(ConsoleMenuOption option){
    //     this.options.addLast(option);
    //     return this;
    // }

    // public ConsoleMenu removeLastOption(ConsoleMenuOption option){
    //     this.options.removeLast();
    //     return this;
    // }
}
