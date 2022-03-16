package entity;

import adtImplementation.SortedArrayList;

import java.util.ArrayList;
import java.util.List; //adtImplementation
import java.util.Scanner;
import adtInterfaces.SortedListInterface;
import entity.OrderProductycl;
import java.util.Date;

public class OrderBill {

    public static void main(String[] args) {

        // variables
        String productName = null;
        int quantity = 0;
        double price = 0.0;
        double totalPrice = 0.0;
        double overAllPrice = 0.0;
        char choice = '\0';

        // create Scanner class object
        Scanner scan = new Scanner(System.in);

        // SortedArrayList<OrderProduct> product = new SortedArrayList<OrderProduct>();
        SortedArrayList<OrderProductycl> product = new SortedArrayList<OrderProductycl>();

        do {
            // read input values
            System.out.println("Enter product details,");
            System.out.print("Name: ");
            productName = scan.nextLine();
            System.out.print("Quantity: ");
            quantity = scan.nextInt();
            System.out.print("Price (per item): ");
            price = scan.nextDouble();

            // calculate total price for that product
            totalPrice = price * quantity;

            // calculate overall price
            overAllPrice += totalPrice;

            // create Product class object and add it to the list
            product.add(new OrderProductycl(
                    productName, quantity, price, totalPrice));

            // ask for continue?
            System.out.print("Want to add more item? (y or n): ");
            choice = scan.next().charAt(0);

            // read remaining characters, don't store (no use)
            scan.nextLine();
        } while (choice == 'y' || choice == 'Y');

        System.out.println("\nTotal Price = " + overAllPrice);
        System.out.println("Please choose one of the payment method below to process: ");
        System.out.println("1. Credit Card ");
        System.out.println("2. Online Banking or Debit Card");
        System.out.println("3. E-wallet ");
        System.out.print("Method selected: ");
        int paymentMethod = scan.nextInt();

        switch (paymentMethod) {
            case 1:
                System.out.println("Process with Credit card");
                System.out.println("Your payment: RM"+ overAllPrice);
                System.out.println("Payment successful");
                break;
            case 2:
                System.out.println("Process with Online Banking or Debit card");
                System.out.println("Your payment: RM"+ overAllPrice);
                System.out.println("Payment successful");
                break;
            case 3:
                System.out.println("E-wallet");
                System.out.println("Your payment: RM"+ overAllPrice);
                System.out.println("Payment successful");
                break;

        }

        // display all product with its properties
        System.out.println();
        System.out.println();
        System.out.println("          Online Sales System        ");
        System.out.println("-------------------------------------------- ");
        System.out.println();
        System.out.println("userID: ");
        System.out.println("Username: ");
        System.out.println("Address: ");
        OrderProductycl.displayFormat();

        int count = product.size();
        for (int i = 0; i < count; ++i) {
            // product.display();
            System.out.format("%-9s %8d %10.2f %10.2f\n",
                    productName, quantity, price, totalPrice);
        }

        System.out.println();
        System.out.println("Thank you. Come Again!");
        //close Scanner
        scan.close();
    }

}

