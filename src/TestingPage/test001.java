package TestingPage;

import UtilityClasses.CMD;
import UtilityClasses.ProjectCompileUtil;

public class test001
{
    public static void main(String[] args)
    {
        ProjectCompileUtil.compileAndGenerate(new test001());
        CMD.cls();
        System.out.println("Hello, my name is teo shi han");
        System.out.println("This is to test is the compile util working");
    }
}
