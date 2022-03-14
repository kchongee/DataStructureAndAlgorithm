package UtilityClasses;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ProjectCompileUtil
{
    private static final ProjectCompileUtil INSTANCE = new ProjectCompileUtil();


    private ProjectCompileUtil() {}


    public static ProjectCompileUtil getInstance() {
        return INSTANCE;
    }


    public static void compileAndGenerate(Object obj)
    {
        compileJava(obj);
        generateLauncher(obj);
    }


    // region: ScriptHandling
    private static String generateCompileCommand(Object obj)
    {
        String sourcePath = wrapQuotes(getSourceFilePath());
        String classMain = wrapQuotes(getFilePath(obj));


        // debug
        // System.out.println("javac -cp " +  sourcePath + " " + classMain);


        return "javac -cp " +  sourcePath + " " + classMain;
    }

    private static String generateLauncherScript(Object obj)
    {
        compileJava(obj);
        String line1 = "cd " + getSourceFilePath() + "\n";
        String line2 = "java -cp " + wrapQuotes(getSQLjarPath()) + "; " + obj.getClass().getName().toString() + "\npause";
        return  line1 + line2;
    }

    // endregion


    // region : PathHandling
    private static String getSourceFilePath() { return getProjectRoot() + "\\src";}


    private static String getSQLjarPath()
    {
        String source = getSourceFilePath();
        String sqlConnectorPath = source + "\\ThirdPartyLibrary\\" + "mysql-connector-java-8.0.28.jar";
        return sqlConnectorPath;
    }


    private static String generateLauncherDir()
    {
//        System.out.println("GEN = " + getSourceFilePath() + "\\Launcher");
        return getSourceFilePath() + "\\ClassLauncher";
    }


    private static void createLauncherDir()
    {
        Path path = Path.of(generateLauncherDir());
//        System.out.println("Launcher dir =" + path);
        try
        {
            Files.createDirectories(path);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    private static String getFilePath(Object obj)
    {
        String sourcePath = getSourceFilePath();
        String packageClass = obj.getClass().toString().replace('.','\\');
        packageClass = packageClass.replace("class ","");
        String path = sourcePath + '\\' +  packageClass + '\\' + ".java";
        path = path.replace("\\.java",".java");
        return path;
    }
    // endregion


    // region : FileHandling
    private static String getProjectRoot() { return System.getProperty("user.dir");}


    private static void createOrOverwriteFile(String filePath) {
        try
        {
            File myObj = new File(filePath);
            if (!myObj.createNewFile())
            {
                deleteFile(filePath);
                createOrOverwriteFile(filePath);
            }
        }
        catch (IOException e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    private static void deleteFile(String filePath)
    {
        File myObj = new File(filePath);
        if (!myObj.delete())
        {
            System.out.println("Failed to delete the file.");
        }
    }

    private static void writeToFile(String filePath, String command) {
        try
        {
            FileWriter myWriter = new FileWriter(filePath);
            myWriter.write(command);
            myWriter.close();
//            System.out.println("Successfully wrote to the file.");
        }
        catch (IOException e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    // endregion


    // region : complex
    private static void compileJava(Object obj)
    {
        String code = generateCompileCommand(obj);
        try
        {
            Runtime.getRuntime().exec(code);
        }
        catch (Exception e)
        {
            System.out.println("Error compile java code : " + e.getMessage());
        }
    }


    private static void generateLauncher(Object obj)
    {
        createLauncherDir();
        String launcherDir = generateLauncherDir();
        String fileName = obj.getClass().getSimpleName();
        String filePath = launcherDir + "\\" + fileName + "Launcher.bat";
        createOrOverwriteFile(filePath);
        writeToFile(filePath, generateLauncherScript(obj));
    }
    // endregion


    // region : Others
    private static String wrapQuotes(String str){ return "\"" + str + "\"";}
    // endregion
}