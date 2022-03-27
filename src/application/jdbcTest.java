package application;

import UtilityClasses.jdbcUtil;
import adtInterfaces.ListInterface;
import adtInterfaces.MapInterface;
import entity.AccountList;
import adtImplementation.ArrayList;
import adtImplementation.HashMap;
import entity.Account;

public class jdbcTest {

    public static AccountList accountList = new AccountList(100);
    public static ListInterface<MapInterface<String, Object>> hashList = new ArrayList<>(100);
    public static void main(String[] args) {

    
        hashList = jdbcUtil.readAll("SELECT * FROM Account;");

        for(int i=0;i<hashList.size();i++){      
            Account a = new Account(hashList.get(i).get("accountID"),
            hashList.get(i).get("userName"),
            hashList.get(i).get("userPwd"),
            hashList.get(i).get("name"),
            hashList.get(i).get("address"),
            hashList.get(i).get("email"),
            hashList.get(i).get("isSeller"));         
            accountList.addAccount(a);

            System.out.println(a);
        } 
    }
    
}
