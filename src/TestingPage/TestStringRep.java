package TestingPage;

public class TestStringRep {
    public static void main(String[] args)
    {
        System.out.println(new String(new char[6]).replace("\0", "s"));
    }
}
