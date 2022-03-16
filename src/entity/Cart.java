package entity;

import java.time.LocalDate;

public class Cart {
    CartDetails cartID;
    Buyer buyerID;
    Seller sellerID;
    Payment payment;

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
