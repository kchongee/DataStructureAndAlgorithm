package entity;
import SubSystem.CatalogEditor.CatalogFormatter;
import UI.CatalogUI;
import UtilityClasses.CMD;
import adtImplementation.ArrayList;
// import adtInterfaces.ListInterface;

import javax.swing.*;
import java.awt.*;

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

    public Catalog(ArrayList<Product> productList)
    {
        this.productList = productList;
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
        if (index == productList.size()-1)
        {   // is last element
            productList.add(product);
        }
        else
        {   // not last element
            productList.add(index+1, product);
        }
    }

    public void replace(int number, Product product){
        productList.replace(number, product);
    }

    // endregion


    /*
    * Problem
    * Statement : Catalog can't be zero in product list
    **/


    // region : public methods
    public String catalogStr()
    {
        String head = formatter.headStr();
        String content = "";
        if(!productList.isEmpty())
        {
            for (int i = 0 ; i < productList.size() ; i++)
            {
                Product tempProduct = productList.get(i);
                content = content + (formatter.toRow(i+1, tempProduct)) + "\n";
            }
            content = content + formatter.lineStr();
        }
        return head + "\n" + content;
    }

    public String catalogHtml()
    {
        String catalogHTML = catalogStr().replace("\n", "<br>");
        catalogHTML = catalogHTML.replace(" ", "&nbsp;");
        catalogHTML= "<html>" + catalogHTML +"</html>";
        return catalogHTML;
    }


    public void displayActionPane() {
        System.out.println(formatter.strActionPane());
    }
    // endregion



    public static void main(String[] args)
    {
        Catalog catalog = new Catalog();
        CatalogUI ui = new CatalogUI(catalog);
        ui.displayCatalogString();
        catalog.displayActionPane();
    }


    public static void uploadCatalogToDatabase()
    {

    }


    // region : getters setters
    public ArrayList<Product> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<Product> productList) {
        this.productList = productList;
    }

    public CatalogFormatter getFormatter() {
        return formatter;
    }

    public void setFormatter(CatalogFormatter formatter) {
        this.formatter = formatter;
    }
    // endregion
}