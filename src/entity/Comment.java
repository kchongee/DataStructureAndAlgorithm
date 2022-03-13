package entity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

// import Customer;
// import adtInterfaces.ArrayList;
import adtInterfaces.ListInterface;

public class Comment {
    private String text;
    private Account account;
    private LocalDateTime dateTimePosted;

    public Comment(Account acc, String text){
        this.account = acc;
        this.text = text;
        this.dateTimePosted = LocalDateTime.now();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Account getAccount() {
        return this.account;
    }

    public void setAccount(Account acc) {
        this.account = acc;
    }

    public LocalDateTime getdateTimePosted() {
        return dateTimePosted;
    }

    public void setdateTimePosted(LocalDateTime dateTimePosted) {
        this.dateTimePosted = dateTimePosted;
    }
    
    
}
