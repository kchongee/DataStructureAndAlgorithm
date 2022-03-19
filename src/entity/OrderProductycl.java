package entity;

import adtImplementation.SortedArrayList;
import java.util.List; //adtImplementation
import java.util.Scanner;
import adtInterfaces.SortedListInterface;

public class OrderProductycl implements Comparable<OrderProductycl> {
  // properties
  private String pname;
  private int qty;
  private double price;
  private double totalPrice;

  // constructor
  OrderProductycl(String pname, int qty, 
              double price, double totalPrice) {
    this.pname = pname;
    this.qty = qty;
    this.price = price;
    this.totalPrice = totalPrice;
  }

  // getter methods
  public String getPname() {
    return pname;
  }
  public int getQty() {
    return qty;
  }
  public double getPrice() {
    return price;
  }
  public double getTotalPrice() {
    return totalPrice;
  }

  // displayFormat
  public static void displayFormat() {
    System.out.print(
        "\nName      Quantity    Price   Total Price\n");
  }

  // display
  public void display() {
    System.out.format("%-9s %8d %10.2f %10.2f\n", 
         pname, qty, price, totalPrice);
  }

@Override
public int compareTo(OrderProductycl o) {
    // TODO Auto-generated method stub
    return 0;
}
}


