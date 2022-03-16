package entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class Payment {
    static Product product;
    CartDetails cartDetails;
    private String paymentID;
    private boolean isCheckout;
    private LocalDate checkOutDate;
    private LocalTime checkOutTime;
    private String paymentMethod;
    private static double overAllPrice;

    public Payment(String paymentID, boolean isCheckout, LocalDate checkOutDate, LocalTime checkOutTime,
            String paymentMethod, double overAllPrice) {
        this.paymentID = paymentID;
        this.isCheckout = isCheckout;
        this.checkOutDate = checkOutDate;
        this.checkOutTime = checkOutTime;
        this.paymentMethod = paymentMethod;
        this.overAllPrice = overAllPrice;
    }

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public boolean isCheckout() {
        return isCheckout;
    }

    public void setCheckout(boolean isCheckout) {
        this.isCheckout = isCheckout;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public LocalTime getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(LocalTime checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public double getOverAllPrice() {
        return overAllPrice;
    }

    public void setOverAllPrice(double overAllPrice) {
        this.overAllPrice = overAllPrice;
    }

    public static void main() {

        Scanner scan = new Scanner(System.in);
        overAllPrice += (product.getPrice() * CartDetails.getProductQty());
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
        scan.close();

    }
  
}


