package entity;
import SubSystem.CatalogEditor.CatalogFormatter;
import UI.CatalogUI;
import UtilityClasses.CMD;
import UtilityClasses.jdbcUtil;
import adtImplementation.ArrayList;
import application.App;
// import adtInterfaces.ListInterface;

import javax.swing.*;
import java.awt.*;

public class Catalog
{
    // private ListInterfaceEe<Product> productList = new ArrayListEe<Product>();    
    private ArrayList<Product> productList;


    // region : constructor
    public Catalog() {
        this.productList = new ArrayList<Product>();
    }

    public Catalog(ArrayList<Product> productList) {
        this.productList = productList;
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
        CatalogFormatter formatter = new CatalogFormatter();
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

    // endregion




    // region : getters setters
    public ArrayList<Product> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<Product> productList) {
        this.productList = productList;
    }

    public Integer createNewCatalogIntoDatabase()
    {
        Integer maxRoomID = (Integer) jdbcUtil.readOne("select max(roomID) from roomcatalog").get("max(roomID)");
        if (maxRoomID == null){
            maxRoomID = 1;
        }

        int productMax = (Integer) jdbcUtil.readOne("select max(productID) from product").get("max(productID)");

        String sellerID = "";
        if (App.currentUser == null){
            sellerID = "A02";
        }else {
            sellerID = App.currentUser.getAccountID();
        }

        for (int i = 0 ; i < productList.size() ; i++)
        {
            String query = String.format("insert ignore into roomcatalog values (%s,%s);",maxRoomID+1,++productMax);
            String query2 = String.format("insert ignore into product values (%s,'%s','%s',%s,'%s');",productMax-1,productList.get(i).getTitle(),productList.get(i).getDescription(),productList.get(i).getPrice(),sellerID);
            jdbcUtil.executeCUD(query);
            jdbcUtil.executeCUD(query2);
        }
        return maxRoomID+1;
    }
    // endregion
}