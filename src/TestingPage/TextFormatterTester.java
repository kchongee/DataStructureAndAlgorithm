package TestingPage;

public class TextFormatterTester {
    public static void main(String[] args)
    {
        System.out.println(toStrings("In this quick tutorial, you will learn how to capitalize the first letter of a string in Java. Unfortunately, the String class in Java does not provide any method to capitalize string. However, there are methods available to change the case of the string (uppercase, lowercase, etc.)."));
    }

    public static String toStrings(String str)
    {
        String[] words = str.split(" ");
        String line = "";
        String text = "";
        for (String word : words)
        {
            String tempWord = word + " ";
            if ((line + tempWord).length()-1 == 100) // exceed
            {
                text = text + line + word + '\n';
                line = "";
            }
            else if ((line + tempWord).length()-1 > 100)
            {
                text = text + line + '\n';
                line = tempWord;
            }
            else
            {
                line = line+tempWord;
            }
        }
        if (line.length() > 0){
            text = text + line;
        }
        return text;
    }
}
