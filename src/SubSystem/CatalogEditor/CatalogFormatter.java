package SubSystem.CatalogEditor;

import entity.Product;

public class CatalogFormatter
{
    String noColF = "| %-3s |";
    String nameColF = " %-33s |";
    String priceColF = " %-7s |";
    String descColF = " %-54s |";


    public String lineStr()
    {
        return
        "+" + repChar(5,"-") +
        "+" + repChar(35, "-") +
        "+" + repChar(9,"-") +
        "+" + repChar(56, "-")+ "+";
    }

    private static String repChar(int time, String character) {
        return new String(new char[time]).replace("\0",character);
    }

    public String toRow(int rowNo, Product product)
    {
        return String.format(
                noColF+nameColF+priceColF+descColF,
                Integer.toString(rowNo), product.getTitle(), product.getPrice(),
                product.getDescription());
    }

    public String strTableHead()
    {
        return String.format(noColF+nameColF+priceColF+descColF,"No.","Product Name","Price","Description");
    }

    public String emptyTable()
    {
        return
        lineStr() + "\n" +
        strTableHead() + "\n"+
        lineStr();
    }

    public String strActionPane(){
        return String.format("%-22s%-22s%22s%22s%22s","[1]Add","[2]Delete","[3]Insert Below","[4]Edit","[5]Quit");
    }
}
