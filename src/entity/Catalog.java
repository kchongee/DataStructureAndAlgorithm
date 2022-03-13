package entity;
import adtImplementation.ArrayListEe;
import adtImplementation.HashMap;
// import adtInterfaces.ListInterface;
import adtInterfaces.ListInterfaceEe;
import adtInterfaces.MapInterface;

public class Catalog{
    // private ListInterfaceEe<Product> productList = new ArrayListEe<Product>();    
    private MapInterface<String,Product> numberingProduct;

    public Catalog(){
        this.numberingProduct = new HashMap<>();
    }

    void add(String keyword,Product product){
        this.numberingProduct.put(keyword, product);
    }

    void delete(){

    }
}
