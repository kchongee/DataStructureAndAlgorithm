package entity;
public class Product{
    private String productId;
    private String title;
    private double price;
    private String description;    
    private String category;
    private static int id = 0;

    public Product(String title, double price, String description){
        this.productId = String.format("PROD%4s", id).replace(' ', '0');   
        this.title = title;
        this.price = price;
        this.description = description;
        id++;
    }    

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
