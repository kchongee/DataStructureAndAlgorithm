package entity;

import adtInterfaces.ListInterface;

public class Invoice {

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
