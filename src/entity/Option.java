package entity;
import java.util.function.Consumer;
import java.util.function.Function;

public class Option{
    private String text;
    private Consumer<String> function;

    public Option(String text, Consumer<String> func){
        this.text = text;
        this.function = func;
    }

    public Option(Consumer<String> func){
        this.text = "";
        this.function = func;
    }

    public String getText() {
        return text;
    }

    public Option setText(String text) {
        this.text = text;
        return this;
    }    

    public Option setFunction(Consumer<String> function) {
        this.function = function;
        return this;
    }

    public void execFunction(){
        this.function.accept(text);
    }
}
