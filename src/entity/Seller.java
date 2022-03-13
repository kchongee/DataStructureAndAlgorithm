package entity;

<<<<<<< HEAD

import adtImplementation.ArrayListEe;
import adtInterfaces.ListInterfaceEe;
=======
import adtImplementation.Account;
import adtImplementation.ArrayList;
import adtInterfaces.ListInterface;
>>>>>>> 41f3895930fd6306188614898f4984633d1627a9

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
