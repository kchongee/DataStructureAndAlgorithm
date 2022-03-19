package entity;

import adtImplementation.ArrayList;
import adtInterfaces.ListInterface;
import application.App;

public class Cart {
    private ListInterface<BuyerProduct> cartProducts;
    CartDetails cartID;
    Buyer buyerID;
    Seller sellerID;
    Payment payment;
    

    public Cart(){
        clearProducts();
    }

    public ListInterface<BuyerProduct> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(ListInterface<BuyerProduct> cartProducts) {
        this.cartProducts = cartProducts;
    }

    public void addProduct(BuyerProduct cartProduct){
        this.cartProducts.add(cartProduct);
    }

    public void removeProduct(BuyerProduct cartProduct){
        this.cartProducts.remove(cartProduct);
    }

    public void clearProducts(){
        this.cartProducts = new ArrayList<>();
    }

    public ListInterface<BuyerProduct> checkoutProducts(){
        ListInterface<BuyerProduct> products = this.cartProducts;
        clearProducts();
        return products;
    }

    public Cart(CartDetails cartID, Buyer buyerID, Seller sellerID, Payment payment) {
        this.cartID = cartID;
        this.buyerID = buyerID;
        this.sellerID = sellerID;
        //this.isCheckout = isCheckout;
        //this.commentDate = commentDate;
        //this.commentTime = commentTime;
        this.payment = payment;
    }

    public CartDetails getCartID() {
        return cartID;
    }

    public void setCartID(CartDetails cartID) {
        this.cartID = cartID;
    }

    public Buyer getBuyerID() {
        return buyerID;
    }

    public void setBuyerID(Buyer buyerID) {
        this.buyerID = buyerID;
    }

    public Seller getSellerID() {
        return sellerID;
    }

    public void setSellerID(Seller sellerID) {
        this.sellerID = sellerID;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    
    
   
    

    
    
}
