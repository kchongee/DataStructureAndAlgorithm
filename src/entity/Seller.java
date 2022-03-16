package entity;

<<<<<<< HEAD
import entity.Account;
import UtilityClasses.DateTimeUtil;
=======
>>>>>>> 1862afae7af0cc415b7e7f9aab87a185184b05e4
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

<<<<<<< HEAD
    public static void main(String[] args) {
        System.out.print(DateTimeUtil.localDateNow());
    }

=======
    public boolean removeProduct(Product product){
        return products.remove(product);
    }

    public boolean removeProductByIndex(int index){
        return products.remove(index);
    }

    public void clearProducts(){
        products = new ArrayList<>();
    }
>>>>>>> 1862afae7af0cc415b7e7f9aab87a185184b05e4
}
