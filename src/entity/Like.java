package entity;
import adtInterfaces.ListInterface;

public class Like {
    // private Customer customerName;
    private static int likesCount = 0;

    void likesIncrease(){        
        likesCount++;
    }

    void likesDecrease(){
        likesCount--;
    }

    int getLikesCount(){
        return likesCount;
    }

    // void addALike(Customer customer){
    //     likesIncrease();

    // }

    // void deleteALike(Customer customer){
    //     likesDecrease();

    // }

    // public boolean removeCustomerLike(Customer customer){
    //     if(customers.contains(customer)){
    //         customers.remove(customer);
    //         return true;
    //     }
    //     return false;
    // }

    // public ListInterface<Customer> getCustomers(){
    //     return customers;
    // }
}
