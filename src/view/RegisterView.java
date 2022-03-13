package view;

import application.App;

public class RegisterView {
    public static void main() {
        printTitle("Register");
    }    

    public static void printTitle(String title){
        App.clearScreen();
        System.out.println(title);        
        System.out.println();
    }
}
