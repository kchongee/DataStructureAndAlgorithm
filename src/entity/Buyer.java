package entity;

import adtImplementation.ArrayList;
import adtInterfaces.ListInterface;

public class Buyer extends Account{
    private ListInterface<Invoice> invoices;
    private Cart cart;
    private Inbox inbox;

    public Buyer(){
        this.invoices = new ArrayList<>();
        this.cart = new Cart();
        this.inbox = new Inbox();
    }

    public Buyer(Account acc){
        this();
        super.setAccountID(acc.getAccountID());        
        super.setAddress(acc.getAddress());
        super.setEmail(acc.getEmail());        
        super.setIsSeller(acc.getIsSeller());
        super.setName(acc.getName());
        super.setUserName(acc.getUserName());
        super.setUserPwd(acc.getUserPwd());
    }

    public ListInterface<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(ListInterface<Invoice> invoices) {
        this.invoices = invoices;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Inbox getInbox() {        
        return inbox;
    }

    public void setInbox(Inbox inbox) {
        this.inbox = inbox;
    }

    public void receiveNotification(Notification notification){
        this.inbox.pushNotification(notification);
    }    

    public void addInvoice(Invoice invoice){
        this.invoices.add(invoice);
    }

    public void addProductToCart(OrderProduct cartProduct){
        this.cart.addProduct(cartProduct);
    }
    
    public void removeProductFromCart(OrderProduct cartProduct){
        this.cart.removeProduct(cartProduct);
    }

    public void checkoutCart(String paymentMethod, Seller seller){        
        Invoice invoice = new Invoice(this.cart.checkoutProducts(), paymentMethod, this);
        System.out.println(this.getUserName());
        seller.addInvoice(invoice);
        addInvoice(invoice);        
        if(seller.getVoucher().isReleased() && seller.getVoucher().getMinSpend()>0 && invoice.getTotalAmount()>=seller.getVoucher().getMinSpend()){            
            this.receiveNotification(seller.sendVoucherNotification(this.getAccountID()));            
        }
    }
}
