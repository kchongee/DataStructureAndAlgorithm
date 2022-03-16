package entity;

import adtImplementation.HashMap;

public class Product{

    String title;
    double price;
    String description;    
    String category;

    public Product(String title, double price, String description){
        this.title = title;
        this.price = price;
        this.description = description;
    }

    public Product(HashMap<String, Object> productMap)
    {
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
            
}
