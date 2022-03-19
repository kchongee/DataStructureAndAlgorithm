package entity;

import java.time.LocalDate;
import java.time.LocalTime;

import UtilityClasses.DateTimeUtil;
import UtilityClasses.jdbcUtil;

public class Payment {
    Cart cart;
    private boolean isCheckout;
    private LocalDate checkOutDate;
    private LocalTime checkOutTime;
    private String paymentMethod;

    public Payment(Cart cart) {
        this.cart = cart;
    }

    public Payment(LocalDate checkOutDate, LocalTime checkOutTime, String paymentMethod) {
        this.checkOutDate = checkOutDate;
        this.checkOutTime = checkOutTime;
        this.paymentMethod = paymentMethod;
    }

    public Payment(Cart cart, boolean isCheckout, LocalDate checkOutDate, LocalTime checkOutTime,
            String paymentMethod) {
        this.cart = cart;
        this.isCheckout = isCheckout;
        this.checkOutDate = checkOutDate;
        this.checkOutTime = checkOutTime;
        this.paymentMethod = paymentMethod;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
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

    public double calcTotalPrice(){
        double total=0;
        for (int i=0; i<cart.get.getCartDetails().size(); i++){
            double productPrice = cart.getCartDetails().getCartDetails().get(i).getProduct().getPrice();
            double productQty = cart.getCartDetails().getCartDetails().get(i).getQuantity();
            total += productPrice*productQty;
        }
        return total;
    }

    public void updateCart (int cartID){
        String query = String.format(
                 """
                    update Cart
                    set isCheckout=True, checkoutDate='%s', checkoutTime='%s', paymentMethod='%s'
                    where cartID =%s;

                 """, DateTimeUtil.strDateNow(), DateTimeUtil.strTimeNow(), this.paymentMethod, cartID
        );
        jdbcUtil.executeCUD(query);
    }

    public static void main(String[] args) {
        Payment py = new Payment(new Cart(1));
        py.setPaymentMethod("Credit Card");
        py.updateCart(1);
        
    }

}
