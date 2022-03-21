package SubSystem.CatalogEditor;

import entity.Product;

public class CatalogFormatter
{
    private final static int TOTAL_LENGTH = 110;
    private final String noColF = "| %-3s |";
    private final String nameColF = " %-33s |";
    private final String priceColF = " %-7s |";
    private final String descColF = " %-54s |";


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

    public String strTableTitle(String title)
    {
        int titleChar = title.length();
        String padding = Integer.toString((TOTAL_LENGTH - titleChar) / 2);
        String format = "%"+padding+"s" + "%"+titleChar+"s" + "%"+padding+"s";
        return String.format(format, " ",title," ");
    }

    public String headStr()
    {
        return
        strTableTitle("Live Sale Product Catalog") + "\n" +
        lineStr() + "\n" +
        strTableHead() + "\n"+
        lineStr();
    }

    public String strActionPane(){
        return String.format("%-18s%-18s%-18s%18s%18s%18s","[1]Add","[2]Delete","[3]Insert Below","[4]Edit","[5]Create Room","[6]Quit");
    }
}
