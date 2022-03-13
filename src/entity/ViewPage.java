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

public abstract class ViewPage{        
    private String title;
    private ListInterfaceEe<Option> options;    

    public ViewPage(){
        reset();
    }

    public ViewPage(String title){
        this();
        this.title = title;
    }

    public ViewPage(String title,Option... options){
        this();
        this.title = title;        
        this.options.addAll(options);        
    }

    public ViewPage setTitle(String title) {
        this.title = title;
        return this;
    }

    public ViewPage setOptions(ListInterfaceEe<Option> options) {
        this.options = options;
        return this;
    }

    public ViewPage addOption(Option menuOption){
        this.options.add(menuOption);
        return this;
    }   

    public ViewPage reset(){
        this.title = null;        
        this.options = new ArrayListEe<Option>();
        return this;
    }    

    public void printAPage(){   
        clearScreen();        
        
        printTitle();                        

        printMenu();        

        promptUserInputOption();
    }

    public void printTitle(){
        if(title!=null){
            System.out.println(title);
            System.out.println();
        }
    }    
    
    public ViewPage printMenu() {           
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
    
    public ViewPage promptUserInputOption() {     
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

    public void goToUserOptionBasedOnList(int optionNum,ListInterfaceEe<Option> list){        
        if(optionNum<0 || optionNum>list.size()){
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
            list.retrieve(optionNum-1).execFunction();
        }
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

    public boolean promptTryAgain(){
        System.out.print("Do you want to try again? (Y|N): ");
        String input = App.scanner.next();             
        return checkPromptAgain(input);
    }

    public boolean checkPromptAgain(String input){
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

    public void clearScreen(){
        System.out.print("\033[H\033[2J");  
        System.out.flush();
    }    
}
