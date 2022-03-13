package adtImplementation;

public class Account {
    private String userName;
    private String userPwd;
    private String name;
    private String address;
    private String email;
    

    public Account(){
        this.userName="";
        this.userPwd="";
        this.name=""; 
        this.address="";
        this.email="";   
    }

    public Account(String userName, String userPwd){
        this.userName=userName;
        this.userPwd=userPwd;
        this.name="";
        this.address="";
        this.email="";
    }

    public Account(String userName, String userPwd,String name, String address, String email){
        this.name=name;
        this.address=address;
        this.email=email;
        this.userName=userName;
        this.userName=userPwd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "\nName: "+name+
        "\nAddress: "+address+
        "\nEmail: "+email+
        "\nUsername: "+userName+
        "\nYour account has been created. You can proceed to login now.";
    }
}
