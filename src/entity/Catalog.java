package entity;
import SubSystem.CatalogEditor.CatalogFormatter;
import UtilityClasses.ConsoleFormatter;
import adtImplementation.ArrayList;
import adtImplementation.HashMap;
// import adtInterfaces.ListInterface;
import adtInterfaces.MapInterface;

public class Catalog
{
    // private ListInterfaceEe<Product> productList = new ArrayListEe<Product>();    
    private ArrayList<Product> productList;
    private CatalogFormatter formatter;


    // region : constructor
    public Catalog()
    {
        this.productList = new ArrayList<Product>();
        this.formatter = new CatalogFormatter();
    }
    // endregion

    // region : list manipulation
    public void add(Product product) {
        this.productList.add(product);
    }

    public void delete(int number) {
        productList.remove(number);
    }

    public void insertBelow(int number, Product product)
    {
        int index = number;
        if (index == productList.size()-1) {
            productList.add(product);
        }else {
            productList.add(index+1, product);
        }
    }

    public void replace(int number, Product product){
        productList.replace(number, product);
    }

    public void displayCatalog()
    {
        ConsoleFormatter.cls();
        System.out.println(formatter.headStr());

        if(!productList.isEmpty())
        {
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

    public static void main(String[] args)
    {
        Catalog catalog = new Catalog();
        catalog.displayCatalog();
        catalog.displayActionPane();
    }
}
