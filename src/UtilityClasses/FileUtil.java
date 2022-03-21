package UtilityClasses;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileUtil
{
    private final static FileUtil INSTANCE = new FileUtil();
    private FileUtil(){}

    public static String fileToString(String filePath)
    {
        String text = "";
        try
        {
            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine())
            {
                String data = myReader.nextLine() + "\n";
                text = text + data;
            }
            myReader.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return text;
    }

    public static String readFileFromStringRes(String filename)
    {
        String sourcepath = System.getProperty("user.dir");

        if (!sourcepath.contains("src")){
            sourcepath = sourcepath + "\\src";
        }

        String folderName = "StringResources";
        String completePath = sourcepath + "\\" + folderName + "\\" + filename;
        return fileToString(completePath);
    }
}