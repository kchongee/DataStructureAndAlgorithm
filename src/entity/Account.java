package entity;

public class Account {
    private String accountID;
    private String userName;
    private String userPwd;
    private String name;
    private String address;
    private String email;
    private Integer isSeller;
    static int counter=1;
    

    public Account(){
        this.accountID="";
        this.userName="";
        this.userPwd="";
        this.name=""; 
        this.address="";
        this.email=""; 
        this.isSeller = null;  
    }

    /*public Account(String userName){
        this.accountID="";
        this.userName=userName;
        this.userPwd="";
        this.name=""; 
        this.address="";
        this.email=""; 
        this.isSeller=0;  
    }*/

    public Account(String accountID){
        this.accountID=accountID;
        this.userName="";
        this.userPwd="";
        this.name=""; 
        this.address="";
        this.email=""; 
        this.isSeller=null;  
    }

    public Account(String accountID, String userName, int isSeller){
        this.accountID=accountID;
        this.userName=userName;
        this.userPwd="";
        this.name="";
        this.address="";
        this.email="";
        this.isSeller=isSeller;
    }

    public Account(String userName, String userPwd){
        this.accountID="";
        this.userName=userName;
        this.userPwd=userPwd;
        this.name="";
        this.address="";
        this.email="";
        this.isSeller=null;
    }

    public Account(String userName, String userPwd,String name, String address, String email, int isSeller){
        this.accountID=String.format("A%d", Account.counter);
        this.name=name;
        this.address=address;
        this.email=email;
        this.userName=userName;
        this.userName=userPwd;
        this.isSeller=isSeller;
        Account.counter++;
    }

    public Account(Object userName, Object userPwd,Object name, Object address, Object email, Object isSeller){
        this.accountID=String.format("A%d", Account.counter);
        this.name=(String)name;
        this.address=(String)address;
        this.email=(String)email;
        this.userName=(String)userName;
        this.userName=(String)userPwd;
        this.isSeller=(int)isSeller;
        
    }

    public Account(String accountID, String userName, String userPwd,String name, String address, String email, int isSeller){
        this.accountID=accountID;
        this.name=name;
        this.address=address;
        this.email=email;
        this.userName=userName;
        this.userName=userPwd;
        this.isSeller=isSeller;
    }

    public Account(Object accountID, Object userName, Object userPwd,Object name, Object address, Object email, Object isSeller){
        this.accountID=(String)accountID;
        this.name=(String)name;
        this.address=(String)address;
        this.email=(String)email;
        this.userName=(String)userName;
        this.userPwd=(String)userPwd;
        this.isSeller=(Integer)isSeller;
        Account.counter++;
    }


    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
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

    public Integer getIsSeller() {
        return isSeller;
    }

    public void setIsSeller(Integer isSeller) {
        this.isSeller = isSeller;
    }

    public String getAccountType(){
        if (this.isSeller==1){
            return "Seller";
        }else{
            return "Buyer";
        }
    }


    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "\nName: "+name+
        "\nAddress: "+address+
        "\nEmail: "+email+
        "\nUsername: "+userName+
        "\nPassword: "+userPwd+
        "\nisSeller: "+isSeller+
        "\nYour account has been created. You can proceed to login now.";
    }
}
