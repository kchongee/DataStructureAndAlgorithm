package entity;

import adtImplementation.ArrayList;
import adtInterfaces.ListInterface;
import application.App;

public class Cart {
    private ListInterface<BuyerProduct> cartProducts;

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
}
