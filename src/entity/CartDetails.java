package entity;

import UtilityClasses.jdbcUtil;
import adtImplementation.ArrayList;
import adtImplementation.HashMap;

import javax.swing.*;


public class CartDetails 
{
    Cart cart;
    ArrayList<OrderProduct> cartDetails;

    public CartDetails(Cart cart) 
    {
        this.cart = cart;
        this.cartDetails= new ArrayList<OrderProduct>();
    }

    public ArrayList<OrderProduct> getCartDetails() {
        return cartDetails;
    }

    public void setCartDetails(ArrayList<OrderProduct> cartDetails) {
        this.cartDetails = cartDetails;
    }

    public boolean addQuantity(int index, int quantity)
    {
        boolean valid = quantity > 0;
        if (valid){
            int qty = cartDetails.get(index).getQuantity();
            cartDetails.get(index).setQuantity(quantity+qty);
        }
        return valid;
    }

//    public boolean decreaseQuantity(){
//        if (quantity > cartDetails.get(index).getQuantity()){
//            return false;
//        }else if (quantity == cartDetails.get(index).getQuantity()){
//            removeProduct(index);
//    }

    public void removeProduct(int index) {
        cartDetails.remove(index);
    }

    public void editQuantity(int index, int quantity) {
        cartDetails.get(index).setQuantity(quantity);
    }

    public ArrayList<HashMap<String, Object>> fetchCartDetailsFromDb(){
        String query = String.format(
                 """
                    Select P.title, P.price, CD.productQty
                    From Product P, CartDetails CD
                    Where P.productID = CD.productID AND cartID=%s;
                    
                 """, cart.getCartID()
        );

        // dead bug
        // System.out.println(query);

        return jdbcUtil.readAll(query);
        
    }

    public void syncCartDetails()
    {
        cartDetails.clear();
        ArrayList<HashMap<String, Object>> productDetails =  fetchCartDetailsFromDb() ;
        for (int i=0; i<productDetails.size(); i++){
            String title = (String)productDetails.get(i).get("title");
            double price = (double)productDetails.get(i).get("price");
            int qty = (Integer)productDetails.get(i).get("productQty");
            OrderProduct op = new OrderProduct(new Product(title,price),qty);
            cartDetails.add(op);
        }
    }

    @Override
    public String toString() {

        String header = String.format("%-5s %-40s %-10s %-10s %-10s","No.", "Product Title", "Price", "Qty", "Subtotal");
        String completeString= header+"\n";
        for (int i = 0; i < cartDetails.size(); i++) {
            OrderProduct tempOrder = cartDetails.get(i);
            String productTitle = tempOrder.getProduct().getTitle();
            double productPrice = tempOrder.getProduct().getPrice();
            int productQty = tempOrder.getQuantity();
            double subtotal = productPrice * productQty;
            String tempString = String.format("%-5s %-40s %-10s %-10s RM %-10.2f",i+1, productTitle, productPrice, productQty, subtotal);
            completeString = completeString+tempString+"\n";
        } 
       
        return completeString + "\n" + strAction();
    }

    public String strAction()
    {
        return
                """
                [1] add product quantity
                [2] decrease product quantity
                [2] remove product
                [3] sort by title
                [4] sort by price
                [5] sort by quantity
                [6] checkout
                [7] exit
                """;
    }

    /*
    public static void main(String[] args) {
        CartDetails cd = new CartDetails( );
        cd.fetchCartDetailsFromDb(1);
        cd.syncCartDetails(1);
        System.out.print(cd.toString());
    }
    */
    
}
