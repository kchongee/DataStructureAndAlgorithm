package entity;

import java.time.LocalDateTime;
import java.util.Iterator;
import adtImplementation.ArrayList;
import adtInterfaces.ListInterface;
import application.App;

public class Invoice {
    // CE part
    private String invoiceId;
    private ListInterface<OrderProduct> invoiceProducts;
    private LocalDateTime invoiceDateTime;
    private Buyer buyer;    
    private String paymentMethod;
    private double totalAmount;
    private static int id = 0;

    private Invoice()
    {
        this.invoiceId = String.format("INV%4s", id).replace(' ', '0');   
        this.invoiceProducts = new ArrayList<>();
        this.invoiceDateTime = LocalDateTime.now();
        id++;
    }    

    public Invoice(ListInterface<OrderProduct> invoiceProducts, String paymentMethod, Buyer buyer){
        this();
        this.buyer = buyer;
        this.invoiceProducts = invoiceProducts;
        this.paymentMethod = paymentMethod;
    }    

    public String getOrderId() {
        return invoiceId;
    }

    public void setOrderId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public ListInterface<OrderProduct> getOrderProducts() {
        return invoiceProducts;
    }

    public void setOrderProducts(ListInterface<OrderProduct> invoiceProducts) {
        this.invoiceProducts = invoiceProducts;
    }

    public LocalDateTime getOrderDateTime() {
        return invoiceDateTime;
    }

    public void setOrderDateTime(LocalDateTime invoiceDateTime) {
        this.invoiceDateTime = invoiceDateTime;
    }

    public double getTotalAmount(){
        Iterator<OrderProduct> invoiceProductsIterator = invoiceProducts.iterator();        
        if(this.totalAmount==0){            
            while(invoiceProductsIterator.hasNext()){
                this.totalAmount += invoiceProductsIterator.next().getSubtotal();            
            }        
        }
        return this.totalAmount;
    }    

    public ListInterface<OrderProduct> getOrderProduct(){
        return this.invoiceProducts;
    }

    public Invoice addOrderProduct(OrderProduct invoiceProduct){
        invoiceProducts.add(invoiceProduct);
        return this;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public ListInterface<OrderProduct> getInvoiceProducts() {
        return invoiceProducts;
    }

    public void setInvoiceProducts(ListInterface<OrderProduct> invoiceProducts) {
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
        str += String.format("+%s+\n", "-".repeat(65));
        str += String.format("|Invoice %-57s|\n", invoiceId);
        str += String.format("|Date: %-59s|\n", invoiceDateTime.getDayOfMonth()+"/"+invoiceDateTime.getDayOfMonth()+"/"+invoiceDateTime.getYear());
        str += String.format("|Time: %-59s|\n", invoiceDateTime.getHour()+":"+invoiceDateTime.getMinute()+":"+invoiceDateTime.getSecond());
        str += String.format("|Payment method: %-49s|\n", paymentMethod);
        str += OrderProduct.displayAll(invoiceProducts);
        str += String.format("|%50s|%-14.2f|\n","Total Amount(RM)",getTotalAmount());        
        str += String.format("+%s+\n", "-".repeat(65));
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
                new OrderProduct(
                    new Product("title1", 50.5, "description1"), 
                    5
                )
            )
            .addOrderProduct(
                new OrderProduct(
                    new Product("title2", 20.5, "description2"), 
                    2
                )
            )
            .addOrderProduct(
                new OrderProduct(
                    new Product("title3", 30.5, "description3"), 
                    3
                )
            )
            .addOrderProduct(
                new OrderProduct(
                    new Product("title4", 40.5, "description4"), 
                    4
                )
            );     

        Invoice invoice2 = new Invoice()
            .addOrderProduct(
                new OrderProduct(
                    new Product("title1", 50.5, "description1"), 
                    5
                )
            )
            .addOrderProduct(
                new OrderProduct(
                    new Product("title2", 20.5, "description2"), 
                    2
                )
            )
            .addOrderProduct(
                new OrderProduct(
                    new Product("title3", 30.5, "description3"), 
                    3
                )
            )
            .addOrderProduct(
                new OrderProduct(
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
}