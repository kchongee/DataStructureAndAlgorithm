package view;

import UI.CatalogUI;
import UtilityClasses.CMD;
import UtilityClasses.EXEHandler;
import UtilityClasses.jdbcUtil;
import adtImplementation.ArrayList;
import adtImplementation.LinkedHashSet;
import application.App;
import application.Option;
import entity.Catalog;
import entity.Product;
import view.RoomViews.RoomViewExe;
import view.RoomViews.SellerRoomControlView;

import javax.swing.*;
import java.io.FileNotFoundException;

import static application.App.roomViewExe;

public class CatalogEditorView
{
    private Catalog catalog;
    private CatalogUI catalogUI;
    private LinkedHashSet<String> productNames;

    private final ArrayList<Option> EDIT_OPTIONS = new ArrayList(new Option[]
    {
            new Option(i->displayAddForm()),
            new Option(i->deleteProduct()),
            new Option(i->insertBelow()),
            new Option(i->editProduct()),
            new Option(i->createRoom()),
            new Option(i->quit())
    });

    public CatalogEditorView(){}


    public CatalogEditorView(Catalog catalog)
    {
        this.catalog = catalog;
        this.catalogUI = new CatalogUI(this.catalog);
        this.productNames = new LinkedHashSet<String>();
    }


    // region : user options
    public void displayAddForm()
    {
        boolean userWantAddProduct = true;
        while (userWantAddProduct)
        {
            catalogUI.displayCatalogString();
            Product product= sellerProductData();

            boolean exist = this.productNames.contains(product.getTitle());
            if (!exist)
            {
                catalog.add(product);
                catalogUI.displayCatalogString();
                userWantAddProduct = App.promptYesOrNo("Successfully added! add again? (y/n) >>> ");
                this.productNames.add(product.getTitle());
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Product already exit!");
            }
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
            Product product = sellerProductData();
            boolean successfullyAdded = this.productNames.add(product.getTitle());
            if (!successfullyAdded)
            {
                JOptionPane.showMessageDialog(null,"Product already exit!");
            }
            else
            {
                catalog.insertBelow(productNoToInsertBelow()-1, product);
                catalogUI.displayCatalogString();
                userWantInsertData = App.promptYesOrNo("Successfully inserted! insert again? (y/n) >>> ");
            }
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
            Product product = sellerProductData();

            boolean successfullAdded = this.productNames.add(product.getTitle());

            if (successfullAdded)
            {
                catalog.replace(productNoToDelete()-1, product);
                catalogUI.displayCatalogString();
                userWantEditData = App.promptYesOrNo("Successfully edited! edit again? (y/n) >>> ");
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Product already exit!");
            }
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
        double productPrice = App.promptDoubleInput("Enter product price       : ");
        String productDesc  = App.promptStringInput("Enter product description : ");
        return new Product(productName, productPrice, productDesc);
    }


    private int productNoToDelete()
    {
        while (true)
        {
            int number =App.promptIntInput("Enter product number : ");
            if   (number > 0 && number <= catalog.getProductList().size()) { return number; }
            else { CMD.pauseWithCustomScript("please input a valid product number\nPress any key to continue...");}
        }
    }


    private int productNoToInsertBelow()
    {
        while (true)
        {
            int number = App.promptIntInput("Put below which product? : ");
            if   (number > 0 && number <= catalog.getProductList().size()) { return number; }
            else { CMD.pauseWithCustomScript("please input a valid product number\nPress any key to continue...");}
        }
    }


    private int productNoToEdit()
    {
        while (true)
        {
            int number = App.promptIntInput("Edit which product? : ");
            if   (number > 0 && number <= 5) { return number; }
            else { CMD.pauseWithCustomScript("please input a valid product number\nPress any key to continue...");}
        }
    }


    public void createRoom()
    {
        if (catalog.getProductList().size() <= 0)
        {
            JOptionPane.showMessageDialog(null,"Please add at least 1 product into catalog","Error",JOptionPane.WARNING_MESSAGE);
            this.main();
        }
        else
        {
            String roomTitle = JOptionPane.showInputDialog("Please enter a title for your room");
            if (roomTitle == "" || roomTitle == null){ roomTitle = "seller too lazy to create title";}
            int roomID = catalog.createNewCatalogIntoDatabase();
            App.sellerCreatedRoomID = roomID;

            String accountId = "A01";
            if (!(App.currentUser == null)){
                accountId = App.currentUser.getAccountID();
            }
            jdbcUtil.executeCUD(String.format("insert ignore into room values (%s,'%s',%s,'%s')",roomID, roomTitle, 1,accountId));

            try
            {
                RoomViewExe roomViewExe = new RoomViewExe();
                roomViewExe.getCommentShowerShortcut().run();
                roomViewExe.getInputPanelShortcut().run();
                App.roomViewExe = roomViewExe;
                CMD.cls();
                SellerRoomControlView.main(new String[]{});
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
        }
        CMD.cls();
    }

    private void quit()
    {
        JOptionPane.showMessageDialog(null,"Your catolog will not be saved!");
        BuyerHomeView.main();
    }

    // endregion


    // region : main
    public void main()
    {
        CMD.cls();
        this.catalogUI.displayCatalogString();
        this.catalogUI.displayActionPane();
        System.out.println("\n");
        int number = App.promptIntInput("Select an action >>> ");
        CMD.cls();
        App.goToUserOption(number, this.EDIT_OPTIONS);
    }

    public static void main(String[] args)
    {
        CMD.cls();
        CatalogEditorView editor = new CatalogEditorView(new Catalog());
        editor.main();
    }
    // endregion
}
