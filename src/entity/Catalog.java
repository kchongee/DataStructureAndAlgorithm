package entity;
import SubSystem.CatalogEditor.CatalogFormatter;
import adtImplementation.ArrayList;
import adtImplementation.HashMap;
// import adtInterfaces.ListInterface;
import adtInterfaces.ListInterfaceEe;
import adtInterfaces.MapInterface;

public class Catalog
{
    // private ListInterfaceEe<Product> productList = new ArrayListEe<Product>();    
    private ArrayList<Product> productList;
    private CatalogFormatter formatter;

    public Catalog()
    {
        this.productList = new ArrayList<Product>();
        this.formatter = new CatalogFormatter();
    }

    public void add(String keyword,Product product) {
        this.productList.add(product);
    }

    public void delete(int number) {
        productList.remove(number-1);
    }

    public void insertBelow(int number, Product product)
    {
        int index = number -1;
        productList.add(index, product);
    }

    public void replace(int number, Product product){
        productList.replace(number-1, product);
    }

    public void displayCatalog()
    {
        if (productList.isEmpty()){
            System.out.println(formatter.emptyTable());
        }
        else
        {
            System.out.println(formatter.lineStr());
            for (int i = 0 ; i < productList.size() ; i++)
            {
                Product tempProduct = productList.get(i);
                System.out.println(formatter.toRow(i+1, tempProduct));
            }
            System.out.println(formatter.lineStr());
        }
    }

    public void displayActionPane() {
        System.out.println(formatter.strActionPane());
    }

    public static void main(String[] args) {
        Catalog catalog = new Catalog();
        catalog.displayCatalog();
    }
}
