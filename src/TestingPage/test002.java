package TestingPage;

import UtilityClasses.ConsoleFormatter;
import UtilityClasses.ProjectCompileUtil;

public class test002 {

    public static void main(String[] args)
    {
        ConsoleFormatter.cls();
        ProjectCompileUtil.compileAndGenerate(new test002());
        for (int i = 0 ; i <= 15 ; i++)
        {
            System.out.println(i);
        }
    }
}