package entity;

public class Follower {
    String a;

    public Follower(){
        this("");
    }

    public Follower(String a){
        this.a = a;
    }

    @Override
    public String toString() {        
        return this.a;
    }
}
