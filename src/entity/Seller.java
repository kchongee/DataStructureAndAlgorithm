package entity;

import entity.Account;
import UtilityClasses.DateTimeUtil;
import adtImplementation.ArrayList;
import adtInterfaces.ListInterface;

public class Seller extends Account{

    ListInterface<Product> products;
    ListInterface<Room> rooms;
    ListInterface<Buyer> followers;

    public Seller(){
        products = new ArrayList<Product>();
        rooms = new ArrayList<Room>();
        followers = new ArrayList<Buyer>();
    }

    public ListInterface<Room> getRooms(){
        return rooms;
    }

    public boolean createRoom(Room newRoom){
        return rooms.add(newRoom);
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
}
