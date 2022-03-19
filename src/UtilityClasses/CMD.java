package UtilityClasses;

import java.io.IOException;

public final class CMD
{
    private CMD(){}

    public static void cls()
    {
        executeWindowsCommand("cls");
    }

    public static void pauseWithCustomScript(String msg)
    {
        try {
            System.out.print(msg);
            executeWindowsCommand("pause>nul");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void executeWindowsCommand(String command)
    {
        try {
            new ProcessBuilder("cmd.exe", "/c", command).inheritIO().start().waitFor();
        }
        catch (IOException  | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
