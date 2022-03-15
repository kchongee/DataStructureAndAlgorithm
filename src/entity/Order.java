package entity;

import java.time.LocalDateTime;

import adtInterfaces.ListInterface;

public class Order {
    // private String orderId;
    private LocalDateTime orderDateTime;
    private ListInterface<OrderProduct> orderProducts;
    private int totalAmount;

    public Order(){
        
    }

    
}
