package entity;
import java.util.function.Consumer;
import java.util.function.Function;

public class ConsoleMenuOption{
    private String text;
    private Consumer<String> function;

    public ConsoleMenuOption(String text, Consumer<String> func){
        this.text = text;
        this.function = func;
    }    

    public String getText() {
        return text;
    }

    public ConsoleMenuOption setText(String text) {
        this.text = text;
        return this;
    }    

    public ConsoleMenuOption changeFunction(Consumer<String> function) {
        this.function = function;
        return this;
    }

    public void execFunction(){
        this.function.accept(text);
    }
}
