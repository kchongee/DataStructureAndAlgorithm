package entity;

import java.time.LocalDateTime;
import java.util.Iterator;
import adtImplementation.ArrayList;
import adtImplementation.SortedArrayList;
import adtInterfaces.ListInterface;
import adtInterfaces.SortedListInterface;

public class Invoice implements Comparable<Invoice> {    
    private String invoiceId;
    private SortedListInterface<OrderProduct> invoiceProducts;
    private LocalDateTime invoiceDateTime;
    private Buyer buyer;    
    private String paymentMethod;
    private double totalAmount;
    private static int id = 0;

    private Invoice()
    {
        this.invoiceId = String.format("INV%4s", id).replace(' ', '0');   
        this.invoiceProducts = new SortedArrayList<>();
        this.invoiceDateTime = LocalDateTime.now();
        id++;
    }    

    public Invoice(SortedListInterface<OrderProduct> invoiceProducts, String paymentMethod, Buyer buyer){
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

    public SortedListInterface<OrderProduct> getOrderProducts() {
        return invoiceProducts;
    }

    public void setOrderProducts(SortedListInterface<OrderProduct> invoiceProducts) {
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

    public SortedListInterface<OrderProduct> getOrderProduct(){
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

    public SortedListInterface<OrderProduct> getInvoiceProducts() {
        return invoiceProducts;
    }

    public void setInvoiceProducts(SortedListInterface<OrderProduct> invoiceProducts) {
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
        str += String.format("|Date: %-59s|\n", invoiceDateTime.getDayOfMonth()+" "+invoiceDateTime.getMonth()+" "+invoiceDateTime.getYear());
        str += String.format("|Time: %-59s|\n", invoiceDateTime.getHour()+":"+invoiceDateTime.getMinute()+":"+invoiceDateTime.getSecond());
        str += String.format("|Payment method: %-49s|\n", paymentMethod);
        str += String.format("|Buyer: %-58s|\n", buyer.getUserName());
        str += OrderProduct.displayAll(invoiceProducts);
        str += String.format("|%50s|%-14.2f|\n","Total Amount(RM)",getTotalAmount());        
        str += String.format("+%s+\n", "-".repeat(65));
        return str;
    }

    public static String displayAll(ListInterface<Invoice> invoices) {                
        String str = invoices.size()>0?invoices.toString():"You don't have any invoice yet.\n";        
        return str;
    }

    @Override
    public int compareTo(Invoice o) {
        // TODO Auto-generated method stub
        return 0;
    }

    // public static void main(String[] args) {
    //     Invoice invoice = new Invoice()
    //         .addOrderProduct(
    //             new OrderProduct(
    //                 new Product("title1", 50.5, "description1"), 
    //                 5
    //             )
    //         )
    //         .addOrderProduct(
    //             new OrderProduct(
    //                 new Product("title2", 20.5, "description2"), 
    //                 2
    //             )
    //         )
    //         .addOrderProduct(
    //             new OrderProduct(
    //                 new Product("title3", 30.5, "description3"), 
    //                 3
    //             )
    //         )
    //         .addOrderProduct(
    //             new OrderProduct(
    //                 new Product("title4", 40.5, "description4"), 
    //                 4
    //             )
    //         );     

    //     Invoice invoice2 = new Invoice()
    //         .addOrderProduct(
    //             new OrderProduct(
    //                 new Product("title1", 50.5, "description1"), 
    //                 5
    //             )
    //         )
    //         .addOrderProduct(
    //             new OrderProduct(
    //                 new Product("title2", 20.5, "description2"), 
    //                 2
    //             )
    //         )
    //         .addOrderProduct(
    //             new OrderProduct(
    //                 new Product("title3", 30.5, "description3"), 
    //                 3
    //             )
    //         )
    //         .addOrderProduct(
    //             new OrderProduct(
    //                 new Product("title4", 40.5, "description4"), 
    //                 4
    //             )
    //         );     

    //     ListInterface<Invoice> invoices = new ArrayList<>();
    //     invoices.add(invoice);
    //     invoices.add(invoice2);

    //     App.clearScreen();
    //     System.out.println(displayAll(invoices));
    // }    
}