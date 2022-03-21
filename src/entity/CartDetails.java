package entity;

import UtilityClasses.jdbcUtil;
import UtilityClasses.SortingAlgorithm.BubbleSort;
import adtImplementation.ArrayList;
import adtImplementation.HashMap;

import javax.swing.*;

import com.mysql.cj.x.protobuf.MysqlxCrud.Order;

public class CartDetails implements Comparable<CartDetails> {
    Cart cart;
    ArrayList<OrderProduct> cartDetails;

    public CartDetails(Cart cart) {
        this.cart = cart;
        this.cartDetails = new ArrayList<OrderProduct>();
    }

    public ArrayList<OrderProduct> getCartDetails() {
        return cartDetails;
    }

    public void setCartDetails(ArrayList<OrderProduct> cartDetails) {
        this.cartDetails = cartDetails;
    }

    public boolean addQuantity(int index, int quantity) {
        boolean valid = quantity > 0;
        if (valid) {
            int qty = cartDetails.get(index).getQuantity();
            cartDetails.get(index).setQuantity(quantity + qty);
        }
        return valid;
    }


    public boolean decreaseQuantity(int quantity, int index)
    {
        if (quantity > cartDetails.get(index).getQuantity())
        {   // remove more than available amount
            return false;
        }
        else if (quantity == cartDetails.get(index).getQuantity())
        {   // remove qty = product qty => remove product
            removeProduct(index);
        }else
        {
            cartDetails.get(index).setQuantity(cartDetails.get(index).getQuantity()-quantity);
        }
        return true;
    }

    public void removeProduct(int index) {
        cartDetails.remove(index);
    }

    public void editQuantity(int index, int quantity) {
        cartDetails.get(index).setQuantity(quantity);
    }

    public ArrayList<HashMap<String, Object>> fetchCartDetailsFromDb() {
        String query = String.format(
                """
                           Select P.title, P.price, CD.productQty
                           From Product P, CartDetails CD
                           Where P.productID = CD.productID AND cartID=%s;

                        """, cart.getCartID());

        // dead bug
        // System.out.println(query);

        return jdbcUtil.readAll(query);

    }

    public void syncCartDetails() {
        cartDetails.clear();
        ArrayList<HashMap<String, Object>> productDetails = fetchCartDetailsFromDb();
        for (int i = 0; i < productDetails.size(); i++) {
            String title = (String) productDetails.get(i).get("title");
            double price = (double) productDetails.get(i).get("price");
            int qty = (Integer) productDetails.get(i).get("productQty");
            OrderProduct op = new OrderProduct(new Product(title, price), qty);
            cartDetails.add(op);
        }
    }

    @Override
    public String toString() {

        String header = String.format("%-5s %-40s %-10s %-10s %-10s", "No.", "Product Title", "Price", "Qty",
                "Subtotal");
        String completeString = header + "\n";
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

    public String strAction() {
        return """
                [1] add product quantity
                [2] decrease product quantity
                [2] remove product
                [3] sort by title
                [4] sort by price
                [5] sort by quantity
                [6] checkout
                [0] go back
                """;
    }

    String title;
    int position;
    int order;

    @Override
    public int compareTo(CartDetails o) {
        for (int i = 0; i < cartDetails.size(); i++) {
            String title = getCartDetails().get(i).getProduct().getTitle();
            int result = title.compareTo(o.title);
            if (result == 0) {
                result = position - o.position;
                result = order - o.order;
            }
        }
        return title.compareTo(o.title);
    }

    public static void bubbleSortQuantity(ArrayList<OrderProduct> op) {
        boolean needNextPass = true;

        for (int k = 1; k < op.size() && needNextPass; k++) {
            needNextPass = false;

            for (int i = 0; i < op.size() - k; i++) {
                if (op.get(i).getQuantity() > op.get(i + 1).getQuantity()) {
                    OrderProduct temp = op.get(i);;
                    OrderProduct temp2 =op.get(i+1);
                    op.replace(i, temp2);
                    op.replace(i + 1, temp);
                    needNextPass = true;
                }
            }
        }
    }

    public static void bubbleSortTitle(ArrayList<OrderProduct> opTitle) {
        boolean needNextPass = true;

        for (int k = 0; k < opTitle.size() && needNextPass; k++) {
            needNextPass = false;

            for (int i = 0; i < opTitle.size() - 1; i++) {
                String first = opTitle.get(i).getProduct().getTitle();
                String sec = opTitle.get(i+1).getProduct().getTitle();
                System.out.print("  first  ="+first);
                System.out.print("  sec  ="+sec);
                System.out.print("Compare =" +first.compareTo(sec));
                if (first.compareTo(sec)> 0) {
                    System.out.print("Compare =" +first.compareTo(sec));
                    OrderProduct temp = opTitle.get(i);;
                    OrderProduct temp2 =opTitle.get(i+1);
                    opTitle.replace(i, temp2);
                    opTitle.replace(i + 1, temp);
                    needNextPass = true;
            
                }
            }
        }
    }

    public static void bubbleSortPrice(ArrayList<OrderProduct> opPrice) {
        boolean needNextPass = true;

        for (int k = 1; k < opPrice.size() && needNextPass; k++) {
            needNextPass = false;

            for (int i = 0; i < opPrice.size() - k; i++) {
                if (opPrice.get(i).getProduct().getPrice()> opPrice.get(i + 1).getProduct().getPrice()) {
                    OrderProduct temp = opPrice.get(i);;
                    OrderProduct temp2 =opPrice.get(i+1);
                    opPrice.replace(i, temp2);
                    opPrice.replace(i + 1, temp);
                    needNextPass = true;
                    
                }
            }
        }
    }

    public static void main(String[] args) {
        ArrayList<OrderProduct> op = new ArrayList<OrderProduct>();
        op.add(new OrderProduct(new Product("milo", 12, "bla"), 6));
        op.add(new OrderProduct(new Product("cookies", 2.50, "bla2"), 4));
        op.add(new OrderProduct(new Product("cup", 5, "bla3"), 1));
        op.add(new OrderProduct(new Product("acccc", 12, "bla"), 6));
        op.add(new OrderProduct(new Product("bo", 2.50, "bla2"), 4));
        op.add(new OrderProduct(new Product("zbr", 5, "bla3"), 1));
        //op = CartDetails.bubbleSortQuantity(op);
        //CartDetails.bubbleSortTitle(op);
        //System.out.println("temp" + new Integer(1));
        //CartDetails.bubbleSortPrice(op);
        // for (int i = 0; i < op.size(); i++) {
        //     System.out.print(op.toString());
        // }
        //System.out.print(op.toString());
    }
    

}
