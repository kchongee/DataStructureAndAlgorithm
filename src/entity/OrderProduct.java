package entity;

import adtImplementation.ArrayList;
import adtInterfaces.ListInterface;
import adtInterfaces.SortedListInterface;
import adtImplementation.SortedArrayList;

public class OrderProduct implements Comparable<OrderProduct>{
    private Product product;
    private int quantity;

    public OrderProduct(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public OrderProduct(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSubtotal(){
        return product.getPrice() * quantity;
    }

    @Override
    public String toString() {
        String str = "";        
        str += String.format("|%-24s|x%-11d|%-12.2f|%-14.2f|",  product.getTitle(), quantity, product.getPrice(), getSubtotal());
        return str;
        // return String.format("(Product)\n%s\nQuantity Ordered: %d\nSubtotal: %.2f",  product.toString(), quantity, getSubtotal());
    }

    public static String displayAll(SortedListInterface<OrderProduct> orderProducts){
        String str = "";
        str += String.format("+%s+\n", "-".repeat(65));
        str += String.format("|%-24s|%-12s|%-12s|%-14s|\n", "Product","Quantity","Price(RM)","Subtotal(RM)");
        str += String.format("|%-24s+%-12s+%-12s+%-14s|\n", "-".repeat(24),"-".repeat(12),"-".repeat(12),"-".repeat(14));
        str += orderProducts;
        str += String.format("+%s+\n", "-".repeat(65));
        return str;
    }

    @Override
    public int compareTo(OrderProduct o) {
        return product.getTitle().compareTo(o.product.getTitle());
    }

    
    
    /*
    public static void main(String[] args) {
        OrderProduct orderProduct1 = new OrderProduct(
            new Product("title1", 20.5, "description1"), 100);
        OrderProduct orderProduct2 = new OrderProduct(
            new Product("title2", 9.5, "description2"), 50);
        OrderProduct orderProduct3 = new OrderProduct(
            new Product("title3", 21.5, "description3"), 80);

        ListInterface<OrderProduct> orderList = new ArrayList<OrderProduct>();
        orderList.add(orderProduct1);
        orderList.add(orderProduct2);
        orderList.add(orderProduct3);

        System.out.println(OrderProduct.displayAll(orderList));
    }
    */

}