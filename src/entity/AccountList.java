package entity;

import adtImplementation.ArrayList;
import adtInterfaces.ListInterface;

public class AccountList {
    private ListInterface<Account> accountList;

    public AccountList(int size){
        accountList = new ArrayList<Account>(size);
    }

    public boolean addAccount(Account inputAccount){
        for(int i=0;i<accountList.size();i++){
            if(inputAccount.getUserName().equals(accountList.get(i).getUserName())){
                return false;
            }
        }
        boolean successful = accountList.add(inputAccount);
        if(successful){
            inputAccount.toString();
        } else{
            return false;
        }
        return true;
    }

    public boolean deleteAccount(Account inputAccount){
        boolean found=true;
        int givenPosition=0;
        for(int i=0;i<accountList.size();i++){
            if(inputAccount.getUserName().equals(accountList.get(i).getUserName())){
                givenPosition=i;
                found=true;
                accountList.remove(givenPosition);
                break;
            }else{
                found=false;
            }
        }
        return found;
    }

    public boolean loginAccount(Account inputAccount){
        for (int i=0;i<accountList.size();i++){
            if (inputAccount.getUserName().equals(accountList.get(i).getUserName())){
                System.out.println(inputAccount.getUserName());
                if (inputAccount.getUserPwd().equals(accountList.get(i).getUserPwd())){
                    System.out.println(inputAccount.getUserPwd());
                    return true;
                }
            }
        }  
        return false;
    }

    /*public boolean changePassword(Account inputAccount, String newPwd){
        boolean pwdChanged=true;
        for(int i=0;i<accountList.size();i++){
            if(inputAccount.getUserName().equals(accountList.get(i).getUserName())){
                accountList.get(i).setUserPwd(newPwd);
                pwdChanged=true;
                break;
            } else{
                pwdChanged=false;
            }
        }
        return pwdChanged;
    }*/

    public boolean checkAccount(String userName){
        for (int i=0;i<accountList.size();i++){
            if (userName.equals(accountList.get(i).getUserName())){
                return true;
            }
        }  
        return false;
    }

    public boolean changePassword(String userName, String newPwd){
        boolean pwdChanged=true;
        for(int i=0;i<accountList.size();i++){
            if(userName.equals(accountList.get(i).getUserName())){
                accountList.get(i).setUserPwd(newPwd);
                pwdChanged=true;
                break;
            } else{
                pwdChanged=false;
            }
        }
        return pwdChanged;
    }
    
    public Account grabAccount(String userName){
        Account grabbed = null;
        for(int i=0;i<accountList.size();i++){
            if(userName.equals(accountList.get(i).getUserName())){
                grabbed = accountList.get(i);
            }
        }
        return grabbed;
    }
}
