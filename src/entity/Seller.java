package entity;


import adtImplementation.ArrayListEe;
import adtInterfaces.ListInterfaceEe;

public class Seller extends Account{

    ListInterfaceEe<Product> products;
    ListInterfaceEe<Room> rooms;

    public Seller(){
        rooms = new ArrayListEe<Room>();
    }

    public ListInterfaceEe<Room> getRooms(){
        return rooms;
    }

    public boolean createRoom(Room newRoom){
        return rooms.add(newRoom);
    }


    public ListInterfaceEe<Product> getProducts(){
        return products;
    }

    public boolean addProduct(Product product){
        return products.add(product);
    }

}
