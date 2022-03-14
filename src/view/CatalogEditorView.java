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
    Catalog catalog;
    ArrayList<Option> options = new ArrayList(new Option[]{
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

            /*
            * Problem
            * Statement input type error
            * */


            String productName  = App.promptStringInput("Enter product name        : ");
            String productPrice = App.promptStringInput("Enter product price       : ");
            String productDesc  = App.promptStringInput("Enter product description : ");
            catalog.add(new Product(productName, Double.parseDouble(productPrice), productDesc));
            ConsoleFormatter.cls();
            catalog.displayCatalog();
            userWantAddProduct = App.promptYesOrNo("Successfully added! add again? (y/n) >>> ");
        }
        App.clearScreen();
        this.main();
    }

    public void main()
    {
        this.catalog.displayCatalog();
        this.catalog.displayActionPane();
        System.out.println("\n");
        int number = App.promptIntInput("Select an action >>> ");
        ConsoleFormatter.cls();
        App.goToUserOption(number, this.options);
    }

    public static void main(String[] args)
    {
        ProjectCompileUtil.compileAndGenerate(new CatalogEditorView());
        ConsoleFormatter.cls();
        CatalogEditorView editor = new CatalogEditorView(new Catalog());
        editor.main();
    }
}
