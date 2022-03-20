package view;

import UI.CatalogUI;
import UtilityClasses.CMD;
import UtilityClasses.ProjectCompileUtil;
import adtImplementation.ArrayList;
import application.App;
import entity.Catalog;
import entity.Option;
import entity.Product;

public class CatalogEditorView
{
    private Catalog catalog;
    private CatalogUI catalogUI;

    private final ArrayList<Option> EDIT_OPTIONS = new ArrayList(new Option[]
    {
            new Option(i->displayAddForm()),
            new Option(i->deleteProduct()),
            new Option(i->insertBelow()),
            new Option(i->editProduct())
    });

    public CatalogEditorView(){}


    public CatalogEditorView(Catalog catalog) {
        this.catalog = catalog;
        this.catalogUI = new CatalogUI(this.catalog);
    }


    // region : user options
    public void displayAddForm()
    {
        boolean userWantAddProduct = true;
        while (userWantAddProduct)
        {
            catalogUI.displayCatalogString();
            catalog.add(sellerProductData());
            catalogUI.displayCatalogString();
            userWantAddProduct = App.promptYesOrNo("Successfully added! add again? (y/n) >>> ");
        }
        App.clearScreen();
        this.main();
    }

    public void deleteProduct()
    {
        boolean userWantDeleteProduct = true;
        while (userWantDeleteProduct)
        {
            /*
            ProblemCo
            Statement : Input exeption handling
             */
            catalogUI.displayCatalogString();
            catalog.delete(productNoToDelete()-1);
            catalogUI.displayCatalogString();
            userWantDeleteProduct = App.promptYesOrNo("Successfully deleted! delete again? (y/n) >>> ");
        }
        App.clearScreen();
        this.main();
    }

    public void insertBelow()
    {
        boolean userWantInsertData = true;
        while (userWantInsertData)
        {
            catalogUI.displayCatalogString();
            catalog.insertBelow(productNoToInsertBelow()-1, sellerProductData());
            catalogUI.displayCatalogString();
            userWantInsertData = App.promptYesOrNo("Successfully inserted! insert again? (y/n) >>> ");
        }
        App.clearScreen();
        this.main();
    }

    public void editProduct()
    {
        boolean userWantEditData = true;
        while (userWantEditData)
        {
            catalogUI.displayCatalogString();
            catalog.replace(productNoToDelete()-1, sellerProductData());
            catalogUI.displayCatalogString();
            userWantEditData = App.promptYesOrNo("Successfully edited! edit again? (y/n) >>> ");
        }
        App.clearScreen();
        this.main();
    }
    // endregion


    // region : utility
    private Product sellerProductData()
    {
        /*
         * Problem
         * Statement input type error
         *
         */

        String productName  = App.promptStringInput("Enter product name        : ");
        String productPrice = App.promptStringInput("Enter product price       : ");
        String productDesc  = App.promptStringInput("Enter product description : ");
        return new Product(productName, Double.parseDouble(productPrice), productDesc);
    }


    private int productNoToDelete() {
        /*
         * Problem
         * Statement input type error
         *
         */

        return App.promptIntInput("Enter product number : ");
    }


    private int productNoToInsertBelow() {
        /*
         * Problem
         * Statement input type error
         *
         */

        return App.promptIntInput("Put below which product? : ");
    }


    private int productNoToEdit(){
        return App.promptIntInput("Edit which product? : ");
    }

    // endregion


    // region : main
    public void main()
    {
        this.catalogUI.displayCatalogString();
        this.catalog.displayActionPane();
        System.out.println("\n");
        int number = App.promptIntInput("Select an action >>> ");
        CMD.cls();
        App.goToUserOption(number, this.EDIT_OPTIONS);
    }

    public static void main(String[] args)
    {
        ProjectCompileUtil.compileAndGenerate(new CatalogEditorView());
        CMD.cls();
        CatalogEditorView editor = new CatalogEditorView(new Catalog());
        editor.main();
    }
    // endregion
}
