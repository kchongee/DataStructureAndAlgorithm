package entity;

public class OrderProduct {
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
        return String.format("(Product)\n%s\nQuantity Ordered: %d\nSubtotal: %.2f",  product.toString(), quantity, getSubtotal());
    }


}