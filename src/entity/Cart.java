package entity;

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

    public Cart(Account account, Account seller, int cartID, CartDetails cartDetails)
    {
        this.account = account;
        this.seller = seller;
        this.cartID = cartID;
        this.cartDetails = cartDetails;
    }

    public Cart(int cartID, Account seller) {
        this.cartID = cartID;
        this.seller= seller;
    }

    public Cart (int cartID){
        this.cartID = cartID;
    }


    public CartDetails getCartDetails() {
        return cartDetails;
    }

    public void setCartDetails(CartDetails cartDetails) {
        this.cartDetails = cartDetails;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getSeller() {
        return seller;
    }

    public void setSeller(Account seller) {
        this.seller = seller;
    }

    public int getCartID() {
        return cartID;
    }

    public void setCartID(int cartID) {
        this.cartID = cartID;
    }

    
    /*
    public static void main(String[] args) {
        Account acc = new Account("A01");
        Account accSeller  = new Account ("S01");
        ArrayList<OrderProduct> cp = new ArrayList<OrderProduct>();
        cp.add(new OrderProduct(new Product("Milo", 12, "bla"),1 ));
        cp.add(new OrderProduct(new Product("cookies", 2.50, "bla2"),1 ));
        cp.add(new OrderProduct(new Product("cup", 5, "bla3"),1 ));
        Cart cart1 = new Cart(acc, accSeller, 1, cp);
        System.out.print(cart1.toString());

    }
    */

}
