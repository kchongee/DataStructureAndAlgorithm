package TestingPage.OpenerAndCloser;

import UtilityClasses.CMD;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Controller
{
    public static void main(String[] args) throws IOException {
        Desktop desktop = Desktop.getDesktop();
        desktop.open(new File("C:\\Users\\Teo Shi Han\\Documents\\07 TARCITEMS\\Year2Sem3\\DataStructureAndAlgoritm\\Assignment\\src\\Executeable\\loopHelloWorld.exe"));


        Process a =Runtime.
                getRuntime().
                exec("cmd /c start \"\" build.bat");
    }

}
