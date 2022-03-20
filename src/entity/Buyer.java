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
        // this.inbox.addNotification(notification);
        this.inbox.pushNotification(notification);
    }    

    public void addInvoice(Invoice invoice){
        this.invoices.add(invoice);
    }

    public void removeProductFromCart(BuyerProduct cartProduct){
        this.cart.removeProduct(cartProduct);
    }

    public void addProductToCart(BuyerProduct cartProduct){
        this.cart.addProduct(cartProduct);
    }

    public void checkoutCart(Seller seller){
        Invoice invoice = new Invoice(this.cart.checkoutProducts(), this);
        seller.addInvoice(invoice);
        addInvoice(invoice);        
        if(seller.getVoucher().isReleased() && seller.getVoucher().getMinSpend()>0 && invoice.getTotalAmount()>=seller.getVoucher().getMinSpend()){            
            this.receiveNotification(seller.sendVoucherNotification());            
        }
        // try {
        //     Thread.sleep(1500);
        // } catch (InterruptedException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // }
    }
}
