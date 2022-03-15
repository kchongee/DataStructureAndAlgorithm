package view.RoomViews;

import UtilityClasses.CMD;
import UtilityClasses.FileUtil;
import UtilityClasses.ProjectCompileUtil;
import adtImplementation.ArrayList;
import entity.Catalog;
import entity.Option;
import entity.Product;

public class BuyerRoomControlView
{
    Catalog catalog;
    ArrayList<Option> BUYER_CONTROLS = new ArrayList<Option>(
        new Option[]
            {

            }
    );



    // region : Constructor
    public BuyerRoomControlView(){}

    public BuyerRoomControlView(Catalog catalog){
        this.catalog = catalog;
    }
    // endregion


    // region : options
    public void showCommentInstructions()
    {
        CMD.cls();
        String instructionDoc = FileUtil.readFileFromStringRes("commentInstruction.txt");
        System.out.println(instructionDoc);
        CMD.pauseWithCustomScript("  Press any key to return...");
    }

    public void showCatalog() {
        catalog.displayCatalogOptionPane();
    }
    // endregion



    public static void main(String[] args)
    {
        ArrayList<Product> productList = new ArrayList<Product>(
                new Product[]
                {
                    new Product("Fruit", 12.50, "sweet and nice"),
                    new Product("Apple", 45, "vitamin c"),
                    new Product("Orange", 56, "vitaminC rich fruit")
                }
        );
        Catalog cat = new Catalog(productList);
        ProjectCompileUtil.compileAndGenerate(new BuyerRoomControlView());
        BuyerRoomControlView brcv = new BuyerRoomControlView(cat);
        brcv.catalog.displayCatalogOptionPane();
    }
}