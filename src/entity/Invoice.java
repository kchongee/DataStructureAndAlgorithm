package entity;

import java.time.LocalDateTime;
import java.util.Iterator;

import adtImplementation.ArrayList;
import adtInterfaces.ListInterface;
import application.App;

public class Invoice {
    // CE part
    private String invoiceId;
    private ListInterface<BuyerProduct> invoiceProducts;
    private LocalDateTime invoiceDateTime;
    private Buyer buyer;    
    private String paymentMethod;
    private static int id = 0;

    private Invoice(){
        this.invoiceId = String.format("INV%4s", id).replace(' ', '0');   
        this.invoiceProducts = new ArrayList<>();
        this.invoiceDateTime = LocalDateTime.now();
        id++;
    }    

    public Invoice(ListInterface<BuyerProduct> invoiceProducts,Buyer buyer){
        this();
        this.buyer = buyer;
        this.invoiceProducts = invoiceProducts;
    }    

    public String getOrderId() {
        return invoiceId;
    }

    public void setOrderId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public ListInterface<BuyerProduct> getOrderProducts() {
        return invoiceProducts;
    }

    public void setOrderProducts(ListInterface<BuyerProduct> invoiceProducts) {
        this.invoiceProducts = invoiceProducts;
    }

    public LocalDateTime getOrderDateTime() {
        return invoiceDateTime;
    }

    public void setOrderDateTime(LocalDateTime invoiceDateTime) {
        this.invoiceDateTime = invoiceDateTime;
    }

    public double getTotalAmount(){
        Iterator<BuyerProduct> invoiceProductsIterator = invoiceProducts.iterator();
        double totalAmount = 0;
        while(invoiceProductsIterator.hasNext()){
            totalAmount += invoiceProductsIterator.next().getSubtotal();            
        }        
        return totalAmount;
    }    

    public ListInterface<BuyerProduct> getOrderProduct(){
        return this.invoiceProducts;
    }

    public Invoice addOrderProduct(BuyerProduct invoiceProduct){
        invoiceProducts.add(invoiceProduct);
        return this;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public ListInterface<BuyerProduct> getInvoiceProducts() {
        return invoiceProducts;
    }

    public void setInvoiceProducts(ListInterface<BuyerProduct> invoiceProducts) {
        this.invoiceProducts = invoiceProducts;
    }

    public LocalDateTime getInvoiceDateTime() {
        return invoiceDateTime;
    }

    public void setInvoiceDateTime(LocalDateTime invoiceDateTime) {
        this.invoiceDateTime = invoiceDateTime;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String toString() {
        String str = "";                
        str += String.format("+%s+\n", "-".repeat(53));
        str += String.format("|Invoice %-45s|\n", invoiceId);
        str += BuyerProduct.displayAll(invoiceProducts);        
        str += String.format("|%38s|%-14.2f|\n","Total Amount(RM)",getTotalAmount());
        str += String.format("+%s+\n", "-".repeat(53));
        return str;
    }

    public static String displayAll(ListInterface<Invoice> invoices) {
        String str = "";                
        str += invoices;
        return str;
    }

    public static void main(String[] args) {
        Invoice invoice = new Invoice()
            .addOrderProduct(
                new BuyerProduct(
                    new Product("title1", 50.5, "description1"), 
                    5
                )
            )
            .addOrderProduct(
                new BuyerProduct(
                    new Product("title2", 20.5, "description2"), 
                    2
                )
            )
            .addOrderProduct(
                new BuyerProduct(
                    new Product("title3", 30.5, "description3"), 
                    3
                )
            )
            .addOrderProduct(
                new BuyerProduct(
                    new Product("title4", 40.5, "description4"), 
                    4
                )
            );     

        Invoice invoice2 = new Invoice()
            .addOrderProduct(
                new BuyerProduct(
                    new Product("title1", 50.5, "description1"), 
                    5
                )
            )
            .addOrderProduct(
                new BuyerProduct(
                    new Product("title2", 20.5, "description2"), 
                    2
                )
            )
            .addOrderProduct(
                new BuyerProduct(
                    new Product("title3", 30.5, "description3"), 
                    3
                )
            )
            .addOrderProduct(
                new BuyerProduct(
                    new Product("title4", 40.5, "description4"), 
                    4
                )
            );     

        ListInterface<Invoice> invoices = new ArrayList<>();
        invoices.add(invoice);
        invoices.add(invoice2);

        App.clearScreen();
        System.out.println(displayAll(invoices));
    }

    // Chooi Li part
    private static ListInterface<Invoice> invoiceList;
    static Account account;
    static Product product;
    static CartDetails cartDetails;
    private double subtotal;
    private double total;

    

    public Invoice(ListInterface<Invoice> invoiceList, Account account, Product product, CartDetails cartDetails, double subtotal, double total) {
        this.invoiceList = invoiceList;
        this.account = account;
        this.product = product;
        this.cartDetails = cartDetails;
        this.subtotal = subtotal;
        this.total = total;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public CartDetails getCartDetails() {
        return cartDetails;
    }

    public void setCartDetails(CartDetails cartDetails) {
        this.cartDetails = cartDetails;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    public static void invoiceFormat() {
        System.out.println();
        System.out.println();
        System.out.println("          Online Sales System        ");
        System.out.println("-------------------------------------------- ");
        System.out.println();
        System.out.println("userID: "+ account.getAccountID());
        System.out.println("Username: "+ account.getUserName());
        System.out.println("Address: " +account.getAddress());
        System.out.println();
        OrderProductycl.displayFormat();

        int count = invoiceList.size();
        for (int i = 0; i < count; ++i) {
            // product.display();
            System.out.format("%-9s %8d %10.2f %10.2f\n",
                     product.getTitle(), cartDetails.getProductQty(), product.getPrice(), cartDetails.getCost());
        }

        System.out.println();
        System.out.println("Thank you. Come Again!");
    }

    public static void main() {
        invoiceFormat();
    }
    
}