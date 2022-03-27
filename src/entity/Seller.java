package entity;

import java.time.LocalDate;

import adtImplementation.ArrayList;
import adtInterfaces.ListInterface;

public class Seller extends Account{

    private ListInterface<Product> products;
    private ListInterface<Room> rooms;    
    private ListInterface<Invoice> invoices;
    private Voucher voucher;
    private Notification voucherNotification;

    public Seller()
    {
        this.products = new ArrayList<Product>();
        this.rooms = new ArrayList<Room>();        
        this.invoices = new ArrayList<Invoice>();
        this.voucher = new Voucher(0,0);
    }

    public Seller(String accountID) {
        super.setAccountID(accountID);
    }

    public Seller(Account acc){
        super.setAccountID(acc.getAccountID());        
        super.setAddress(acc.getAddress());
        super.setEmail(acc.getEmail());        
        super.setIsSeller(acc.getIsSeller());
        super.setName(acc.getName());
        super.setUserName(acc.getUserName());
        super.setUserPwd(acc.getUserPwd());
    }

    public ListInterface<Room> getRooms(){
        return rooms;
    }

    public boolean createRoom(Room newRoom){
        return rooms.add(newRoom);
    }

    public void setProducts(ListInterface<Product> products) {
        this.products = products;
    }

    public void setRooms(ListInterface<Room> rooms) {
        this.rooms = rooms;
    }

    public ListInterface<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(ListInterface<Invoice> invoices) {
        this.invoices = invoices;
    }    

    public boolean addInvoice(Invoice invoice){
        return invoices.add(invoice);
    }    

    public ListInterface<Product> getProducts(){
        return products;
    }

    public boolean addProduct(Product product){
        return products.add(product);
    }    

    public boolean removeProduct(Product product){
        return products.remove(product);
    }

    public boolean removeProductByIndex(int index){
        return products.remove(index);
    }

    public void clearProducts(){
        products = new ArrayList<>();
    }        

    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    public Notification getVoucherNotification() {
        return voucherNotification;
    }

    public void setVoucherNotification(Notification voucherNotification) {
        this.voucherNotification = voucherNotification;
    }    
    
    public Notification sendVoucherNotification(String accountID) {        
        return voucherNotification = new Notification(
            accountID,
            super.getUserName(),
            "You have one voucher from "+super.getUserName(), 
            String.format(
                    "You spent over RM%.2f, "+ 
                    "and here is your %.2f%% discount voucher." +
                    "Thanks and come again!",
                    voucher.getMinSpend(),
                    voucher.getDiscountPercentage()
                ),
            LocalDate.now().toString(),
            false
        );
    }    
}
