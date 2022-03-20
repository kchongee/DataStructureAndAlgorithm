package entity;

import java.util.Iterator;

import adtImplementation.HashMap;
import adtInterfaces.ListInterface;
import application.App;

public class Product{
    private String productID;
    private String title;
    private double price;
    private String description;        
    private static int id = 0;

    public Product(String title, double price, String description){
        this.productID = String.format("PROD%4s", id).replace(' ', '0');   
        this.title = title;
        this.price = price;
        this.description = description;
        id++;
    }    

    public String getProductID() {
        return productID;
    }
 
    public void setProductID(String productID) {
        this.productID = productID;
    }                
    
    public Product(String title, double price){
        this.title = title;
        this.price = price;
    }


    public Product(HashMap<String, Object> productMap)
    {
        this.productID = Integer.toString((Integer) productMap.get("productID"));
        this.title = (String) productMap.get("title");
        this.price = (Double) productMap.get("price");
        this.description = (String) productMap.get("productDesc");
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("Title: %s\nPrice: %.2f\nDescription: %s",title,price,description);
    }

    public static String displayAll(ListInterface<Product> products){
        String str = "";
        str += String.format("+%s+\n", "-".repeat(50));
        str += String.format("|%-6s|%-25s|%-15s|\n","No.", "Product Title","Price(RM)");
        str += String.format("|%-6s+%-25s+%-15s|\n","-".repeat(6), "-".repeat(25), "-".repeat(15));
        Iterator<Product> productIterator = products.iterator();
        int i = 1;
        while(productIterator.hasNext()){
            Product p = productIterator.next();
            str += String.format("|%-6d|%-25s|%-15.2f|\n",i, App.trimString(p.getTitle(), 25), p.getPrice());
            i++;
        }
        str += String.format("+%s+\n", "-".repeat(50));
        return str;
    }
            
}
