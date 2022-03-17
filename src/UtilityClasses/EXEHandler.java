package UtilityClasses;

import java.io.FileNotFoundException;
import java.io.IOException;


/**
* @author : Teo Shi Han
* The purpose of this class is to launch exe via shorcut
 * It does not run exe directly
 * Hence must create shortcut for exe before using this
 * Also the shortcut must put in "Executable" folder of this project
* */




public class EXEHandler
{
    String shortcutName;
    String[] arguments;
    Process process = null;


    EXEHandler(String shortcutName, String[] arguments) throws FileNotFoundException {

        if (!shortcutName.contains(".lnk")) {
            throw new FileNotFoundException("Please provide a shotcut file name");
        }

        this.shortcutName = shortcutName;
        this.arguments = arguments;
    }


    EXEHandler(String shortcutName) throws FileNotFoundException {

        if (!shortcutName.contains(".lnk")) {
            throw new FileNotFoundException("Please provide a shotcut file name");
        }

        this.shortcutName = shortcutName;
    }


    public void run(){
        generateBatchFile();
        String command = "cmd /c start \"\" " + wrapStringWithQuotes(getBatchFilePath());

        // debug
        System.out.println(command);

        try {
            this.process = Runtime.getRuntime().exec(command);
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public void terminate(){
        deleteBatchFile();
        this.process.destroy();
    }

    private String getFullCommand(){
        return wrapStringWithQuotes(getFilePath()) + " " + argsToString();
    }

    private String getFilePath(){
        return getExecuteablePath() + "\\" + shortcutName;
    }

    private String getExecuteablePath()
    {
        String executablePath = System.getProperty("user.dir");
        if (!executablePath.contains("scr")) {
            executablePath = executablePath+ "\\src\\Executable";
        }
        return executablePath;
    }

    private String wrapStringWithQuotes(String str) {
        return "\""+str+"\"";
    }

    private String argsToString(){
        String empty = "";


        if (arguments != null)
        {
            for (String arg : arguments) {
                empty = empty + arg + " ";
            }
        }

        // debug
        //System.out.println(empty);

        return empty;
    }



    private void generateBatchFile()
    {
        ProjectCompileUtil.createOrOverwriteFile(getBatchFilePath());
        ProjectCompileUtil.writeToFile(getBatchFilePath(),getFullCommand());
    }


    private void deleteBatchFile()
    {
        ProjectCompileUtil.deleteFile(getBatchFilePath());
    }

    private String getBatchFilePath()
    {
        String temp = System.getProperty("user.dir").replaceAll("src","") + "\\temp";
        String fileName = temp+"\\"+shortcutName.replaceAll(".inc","")+".bat";

        // debug
        System.out.println(fileName);

        return fileName;
    }



    public static void main(String[] args) throws IOException {
        EXEHandler handler = new EXEHandler("ExeArgTestShortcut.lnk");
        handler.run();
    }
}
