package entity;

public class CartDetails {
    private String cartID;
    String productID;
    int productQty;
    Product price;

    public CartDetails(String cartID, String productID, int productQty, Product price) {
        this.cartID = cartID;
        this.productID = productID;
        this.productQty = productQty;
        this.price=price;
    }

    public String getCartID() {
        return cartID;
    }

    public void setCartID(String cartID) {
        this.cartID = cartID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public double getProductQty() {
        return productQty;
    }

    public void setProductQty(int productQty) {
        this.productQty = productQty;
    }

    public Product getPrice() {
        return price;
    }

    public void setPrice(Product price) {
        this.price = price;
    }

    public void addToCart()
    { 
        
    }

    
}

