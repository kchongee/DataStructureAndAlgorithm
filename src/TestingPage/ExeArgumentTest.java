package TestingPage;

public class ExeArgumentTest {
    public static void main(String[] args) {
        int count = 0;
        for(String item : args){
            System.out.println(count++ + item);
        }
    }
}
