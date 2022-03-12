package entity;

import java.time.LocalDateTime;
import adtInterfaces.ListInterfaceEe;

public class Order {
    // private String orderId;
    private LocalDateTime orderDateTime;
    private ListInterfaceEe<OrderProduct> orderProducts;
    private int totalAmount;

    public Order(){
        
    }

    
}
