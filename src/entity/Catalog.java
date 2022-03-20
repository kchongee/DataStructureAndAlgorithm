package entity;
import SubSystem.CatalogEditor.CatalogFormatter;
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
        if (index == productList.size()-1) {
            productList.add(product);
        }else {
            productList.add(index+1, product);
        }
    }

    public void replace(int number, Product product){
        productList.replace(number, product);
    }

    // endregion


    /*
    * Problem
    * Statement : Catalog can't be zero product list
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


    public void displayCatalog()
    {
        CMD.cls();
        System.out.println(catalogStr());
    }


    public void displayCatalogOptionPane()
    {
        String catalogHTML = catalogStr().replace("\n", "<br>");
        catalogHTML = catalogHTML.replace(" ", "&nbsp;");
        catalogHTML= "<html>" + catalogHTML +"</html>";
        JLabel label = new JLabel(catalogHTML);
        label.setFont(new Font("Consolas", Font.BOLD, 16));
        JOptionPane.showMessageDialog(null, label);
    }


    public void displayActionPane() {
        System.out.println(formatter.strActionPane());
    }
    // endregion



    public static void main(String[] args)
    {
        Catalog catalog = new Catalog();
        catalog.displayCatalog();
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
