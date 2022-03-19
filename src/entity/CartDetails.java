package entity;

import UtilityClasses.jdbcUtil;
import adtImplementation.ArrayList;
import adtImplementation.HashMap;


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

    public void addQuantity(int index, int quantity) {
        int qty = cartDetails.get(index).getQuantity();
        cartDetails.get(index).setQuantity(quantity+qty);;
    }

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
        return jdbcUtil.readAll(query);
        
    }

    public void syncCartDetails(){
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

        String header = String.format("%-40s %-10s %-10s", "Product Title", "Price", "Qty");
        String completeString= header+"\n";
        for (int i = 0; i < cartDetails.size(); i++) {
            OrderProduct tempOrder = cartDetails.get(i);
            String productTitle = tempOrder.getProduct().getTitle();
            double productPrice = tempOrder.getProduct().getPrice();
            int productQty = tempOrder.getQuantity();
            String tempString = String.format("%-40s %-10s %-10s", productTitle, productPrice, productQty);
            completeString = completeString+tempString+"\n";
        } 
       
        return completeString;
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
