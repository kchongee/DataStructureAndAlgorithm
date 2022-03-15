package view;

import UtilityClasses.ConsoleFormatter;
import UtilityClasses.ProjectCompileUtil;
import adtImplementation.ArrayList;
import application.App;
import entity.Catalog;
import entity.Option;
import entity.Product;

public class CatalogEditorView
{
    private Catalog catalog;
    private final ArrayList<Option> EDIT_OPTIONS = new ArrayList(new Option[]{
            new Option(i->displayAddForm())
    });

    public CatalogEditorView(){}


    public CatalogEditorView(Catalog catalog) {
        this.catalog = catalog;
    }


    public void displayAddForm()
    {
        boolean userWantAddProduct = true;
        while (userWantAddProduct)
        {
            ConsoleFormatter.cls();
            catalog.displayCatalog();
            catalog.add(productData());

            catalog.displayCatalog();

            userWantAddProduct = App.promptYesOrNo("Successfully added! add again? (y/n) >>> ");
        }
        App.clearScreen();
        this.main();
    }


    public Product productData()
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

    public void main()
    {
        this.catalog.displayCatalog();
        this.catalog.displayActionPane();
        System.out.println("\n");
        int number = App.promptIntInput("Select an action >>> ");
        ConsoleFormatter.cls();
        App.goToUserOption(number, this.EDIT_OPTIONS);
    }

    public static void main(String[] args)
    {
        ProjectCompileUtil.compileAndGenerate(new CatalogEditorView());
        ConsoleFormatter.cls();
        CatalogEditorView editor = new CatalogEditorView(new Catalog());
        editor.main();
    }
}
