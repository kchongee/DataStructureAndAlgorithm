package adtImplementation;

import adtInterfaces.ListInterface;

public class AccountList {
    private ListInterface<Account> accountList;

    public AccountList(int size){
        accountList = new ArrayList<Account>(size);
    }

    public boolean addAccount(Account inputAccount){
        for(int i=0;i<accountList.size();i++){
            if(inputAccount.getUserName().equals(accountList.getElementValue(i).getUserName())){
                return false;
            }
        }
        boolean successful = accountList.add(inputAccount);
        System.out.println(successful);
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
            if(inputAccount.getUserName().equals(accountList.getElementValue(i).getUserName())){
                givenPosition=i;
                found=true;
                accountList.delete(givenPosition);
                break;
            }else{
                found=false;
            }
        }
        return found;
    }

    public boolean checkAccount(Account inputAccount){
        for (int i=0;i<accountList.size();i++){
            if (inputAccount.getUserName().equals(accountList.getElementValue(i).getUserName())){
                System.out.println(inputAccount.getUserName());
                if (inputAccount.getUserPwd().equals(accountList.getElementValue(i).getUserPwd())){
                    System.out.println(inputAccount.getUserPwd());
                    return true;
                }
            }
        }  
        return false;
    }

    public boolean changePassword(Account inputAccount, String newPwd){
        boolean pwdChanged=true;
        for(int i=0;i<accountList.size();i++){
            if(inputAccount.getUserName().equals(accountList.getElementValue(i).getUserName())){
                accountList.getElementValue(i).setUserPwd(newPwd);
                pwdChanged=true;
                break;
            } else{
                pwdChanged=false;
            }
        }
        return pwdChanged;
    }
    
}
