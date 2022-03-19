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
    String mode;


    EXEHandler(String shortcutName, String[] arguments, String mode) throws FileNotFoundException {
        this.mode = mode.toLowerCase();

        if (!this.mode.equals("exe") && !this.mode.equals("lnk")){
            throw new UnsupportedOperationException("only can handle exe and lnk type");
        }

        if (!shortcutName.contains("." + this.mode)) {
            throw new FileNotFoundException("Please provide a shortcut file name or exe");
        }

        this.shortcutName = shortcutName;
        this.arguments = arguments;
    }


    EXEHandler(String shortcutName, String mode) throws FileNotFoundException {
        this.mode = mode.toLowerCase();


        if (!this.mode.equals("exe") && !this.mode.equals("lnk")){
            throw new UnsupportedOperationException("only can handle exe and lnk type");
        }

        if (!shortcutName.contains("." + this.mode)) {
            throw new FileNotFoundException("Please provide a shortcut file name or exe");
        }

        this.shortcutName = shortcutName;
    }


    public void run(){
        generateBatchFile();
        String command = "cmd /c start \"\" " + wrapStringWithQuotes(getBatchFilePath());

        // debug
        // System.out.println(command);

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
        if (mode.equals("lnc")){
            return wrapStringWithQuotes(getFilePath()) + " " + argsToString();
        } else {
            String str= String.format(
                    """
                    cd %s
                    %s %s
                    """, wrapStringWithQuotes(getExecuteablePath()),shortcutName, argsToString()
            );
            System.out.println(str);
            return str;
        }
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
        String fileName = temp+"\\"+shortcutName.replaceAll(String.format(".%s",mode),"")+".bat";

        // debug
        // System.out.println(fileName);

        return fileName;
    }


    public static void main(String[] args) throws IOException {
        EXEHandler handler = new EXEHandler("ExeArgTest.exe",new String[]{"a","b","c"},"exe");
        handler.run();
    }
}
