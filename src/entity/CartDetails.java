package entity;

import UtilityClasses.jdbcUtil;
import adtImplementation.ArrayList;
import adtImplementation.HashMap;
import adtInterfaces.ListInterface;

import static UtilityClasses.jdbcUtil.readAll;
import static UtilityClasses.jdbcUtil.readOne;


public class CartDetails {

    private ListInterface<CartDetails> cartDetailsList;
    HashMap<String, ArrayList<Product>>getProductFromDb = new HashMap<>();
    static ArrayList<HashMap<String, Object>> productData = new ArrayList<HashMap<String, Object>>();
    private String cartID;
    private String productID;
    Product product;
    private static int productQty;
    private double cost;
    Account account;

    public CartDetails(ListInterface<CartDetails> cartDetailsList, String cartID, String productID, Product product,
            int productQty, double cost, Account account) {
        this.cartDetailsList = cartDetailsList;
        this.cartID = cartID;
        this.productID = productID;
        this.product = product;
        this.productQty = productQty;
        this.cost = cost;
        this.account = account;
    }

    public ListInterface<CartDetails> getCartDetailsList() {
        return cartDetailsList;
    }

    public void setCartDetailsList(ListInterface<CartDetails> cartDetailsList) {
        this.cartDetailsList = cartDetailsList;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public static int getProductQty() {
        return productQty;
    }

    public void setProductQty(int productQty) {
        this.productQty = productQty;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public boolean addProduct(CartDetails inputCartDetails){
        for (int i=0; i<cartDetailsList.size(); i++){
            if((inputCartDetails.getProductID().equals(cartDetailsList.get(i).getProductID())) &&
                (inputCartDetails.getAccount().equals(account.getIsSeller()))) { //isSeller
                return false;
            } 
        }
        boolean added = cartDetailsList.add(inputCartDetails);
        System.out.println(added);
        if(added){
            inputCartDetails.toString();
        } else{
            return false;
        }
        return true;
    }

    /*
    private Product fetchLatestCartFromDB()
    {

        String query = String.format
                (
                        "SELECT P.title, P.price, CD.productQty\n" +
                        "FROM Product P, CartDetails CD, Cart C\n" +
                        "WHERE P.productID = CD.productID AND CD.cartID=C.cartID AND isCheckout ='False';"
                );


        HashMap<String,Object> ProductData = readOne(query);
        return product;
        
    }
    */

    ArrayList<HashMap<String,Object>>productTable =jdbcUtil.readAll("SELECT title, price FROM Product");
    HashMap<String,Object>record = productTable.get(0);
    Product p = new Product ((String)record.get("title"), (String)record.get("price"), (String)record.get("productDesc"));


    
}
