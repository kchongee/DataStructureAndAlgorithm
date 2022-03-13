package entity;

import adtImplementation.Account;
import adtImplementation.ArrayList;
import adtInterfaces.ListInterface;

public class Seller extends Account{

    ListInterface<Product> products;
    ListInterface<Room> rooms;
    ListInterface<Buyer> followers;

    public Seller(){
        rooms = new ArrayList<Room>();
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

}
