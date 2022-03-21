package UI;

import SubSystem.CatalogEditor.CatalogFormatter;
import UtilityClasses.CMD;
import entity.Catalog;

import javax.swing.*;
import java.awt.*;

public class CatalogUI
{
    Catalog catalog;

    public CatalogUI(Catalog catalog) {
        this.catalog = catalog;
    }

    public void displayCatalogViaJPane()
    {
        JLabel label = new JLabel(catalog.catalogHtml());
        label.setFont(new Font("Consolas", Font.BOLD, 16));
        JOptionPane.showMessageDialog(null, label);
    }

    public void displayCatalogString()
    {
        CMD.cls();
        System.out.println(catalog.catalogStr());
    }

    public void displayActionPane(){
        System.out.println(new CatalogFormatter().strActionPane());
    }
}
