package entity;

import adtImplementation.ArrayList;
import adtInterfaces.ListInterface;

public class BuyerProduct {
    private Product product;
    private int quantity;    

    public BuyerProduct(Product product, int quantity) {
        this.product = product;
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
        str += String.format("|%-12s|x%-11d|%-12.2f|%-14.2f|",  product.getTitle(), quantity,product.getPrice(), getSubtotal());
        return str;
    }

    public static String displayAll(ListInterface<BuyerProduct> orderProducts){
        String str = "";
        str += String.format("+%s+\n", "-".repeat(53));
        str += String.format("|%-12s|%-12s|%-12s|%-14s|\n", "Product","Quantity","Price(RM)","Subtotal(RM)");
        str += String.format("|%-12s+%-12s+%-12s+%-14s|\n", "-".repeat(12),"-".repeat(12),"-".repeat(12),"-".repeat(14));
        str += orderProducts;
        str += String.format("+%s+\n", "-".repeat(53));
        return str;
    }

    public static void main(String[] args) {
        BuyerProduct orderProduct1 = new BuyerProduct(new Product("product1", 50.5, "description1"), 21);
        BuyerProduct orderProduct2 = new BuyerProduct(new Product("product2", 20.5, "description2"), 2);
        BuyerProduct orderProduct3 = new BuyerProduct(new Product("product3", 55.5, "description3"), 7);

        ListInterface<BuyerProduct> orderProduts = new ArrayList<>();
        orderProduts.add(orderProduct1);
        orderProduts.add(orderProduct2);
        orderProduts.add(orderProduct3);

        System.out.println(displayAll(orderProduts));
    }

    
}
