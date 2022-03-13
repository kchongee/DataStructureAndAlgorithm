package test;

import java.util.function.Consumer;
import java.util.function.IntToDoubleFunction;

import adtImplementation.ArrayListEe;
import adtInterfaces.ListInterfaceEe;
import application.App;
import entity.Option;
import entity.ViewPage;

public class WelcomeView{

    public static ListInterfaceEe<Option> menuOptions = new ArrayListEe<Option>();
    
    static{
        menuOptions.add(new Option("Login", i -> goToPage(ii -> LoginView.main())));
        menuOptions.add(new Option("Register", i -> goToPage(ii -> RegisterView.main())));
    }

    public static void main() {         
        printTitle("Welcome to our live sale system");        
        
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
}
