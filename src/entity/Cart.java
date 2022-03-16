package entity;

import java.time.LocalDate;

public class Cart {
    CartDetails cartID;
    Buyer buyerID;
    Seller sellerID;
    private boolean isCheckout;
    Comment commentDate;
    Comment commentTime;
    private String paymentMethod;

    public Cart(CartDetails cartID, Buyer buyerID, Seller sellerID, boolean isCheckout, Comment commentDate,
            Comment commentTime, String paymentMethod) {
        this.cartID = cartID;
        this.buyerID = buyerID;
        this.sellerID = sellerID;
        this.isCheckout = isCheckout;
        this.commentDate = commentDate;
        this.commentTime = commentTime;
        this.paymentMethod = paymentMethod;
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

    public boolean isCheckout() {
        return isCheckout;
    }

    public void setCheckout(boolean isCheckout) {
        this.isCheckout = isCheckout;
    }

    public Comment getCommentDate() {
        return commentDate;
    }

    public Comment getCommentTime() {
        return commentTime;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
   
    

    
    
}
