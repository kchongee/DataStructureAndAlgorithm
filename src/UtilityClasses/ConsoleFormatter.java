package UtilityClasses;

public final class ConsoleFormatter
{
    private ConsoleFormatter(){}

    public static void cls()
    {
        try
        {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
