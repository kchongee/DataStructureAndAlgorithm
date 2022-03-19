package entity;

import entity.Account;
import UtilityClasses.DateTimeUtil;
import adtImplementation.ArrayList;
import adtInterfaces.ListInterface;

public class Seller extends Account{

    private ListInterface<Product> products;
    private ListInterface<Room> rooms;
    private ListInterface<Buyer> followers;
    private ListInterface<Invoice> invoices;
    private Voucher voucher;
    private Notification voucherNotification;

    public Seller(){
        this.products = new ArrayList<Product>();
        this.rooms = new ArrayList<Room>();
        this.followers = new ArrayList<Buyer>();
        this.invoices = new ArrayList<Invoice>();
        this.voucher = new Voucher(0,0);
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

    public ListInterface<Buyer> getFollowers() {
        return followers;
    }

    public void setFollowers(ListInterface<Buyer> followers) {
        this.followers = followers;
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

    public boolean addFollower(Buyer buyer){
        return followers.add(buyer);
    }

    public ListInterface<Product> getProducts(){
        return products;
    }

    public boolean addProduct(Product product){
        return products.add(product);
    }

    public static void main(String[] args) {
        System.out.print(DateTimeUtil.localDateNow());
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

    public void receiveInvoice(Invoice invoice){
        this.invoices.add(invoice);
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
    
    public Notification sendVoucherNotification() {        
        return voucherNotification = new Notification(
            "You have one voucher from "+super.getName(), 
            String.format(
                    "You spent over RM%.2f, "+ 
                    "and here is your %.2f%% discount voucher." +
                    "Thanks and come again!",
                    voucher.getMinSpend(),
                    voucher.getDiscountPercentage()
                ),
            this
        );
    }    
}
