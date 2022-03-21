package view;

import java.util.function.Consumer;

import adtImplementation.ArrayList;
import adtInterfaces.ListInterface;
import application.App;
import entity.Option;

public class WelcomeView{

    public static ListInterface<Option> menuOptions = new ArrayList<Option>();
    
    static{
        menuOptions.add(new Option("Login", i -> goToPage(ii -> LoginView.main())));
        menuOptions.add(new Option("Register", i -> goToPage(ii -> RegisterView.main())));
        menuOptions.add(new Option("Forgot My Password", i -> goToPage(ii -> PasswordView.main())));
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
