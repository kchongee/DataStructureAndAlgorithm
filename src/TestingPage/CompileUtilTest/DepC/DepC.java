package TestingPage.CompileUtilTest.DepC;

import TestingPage.CompileUtilTest.DepA.DepA;
import TestingPage.CompileUtilTest.DepB.DepB;
import UtilityClasses.ProjectCompileUtil;
import entity.Product;

public class DepC {
    public static void main(String[] args)
    {
        ProjectCompileUtil.compileAndGenerate(new DepC());
        DepA.main(new String[]{"a"});
        DepB.main(new String[]{"b"});
        System.out.println("DepC");
    }
}
