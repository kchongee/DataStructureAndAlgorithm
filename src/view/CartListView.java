import entity.Account;
import entity.CartList;




public class CartListView
{
    CartList cartList;
    Account buyer;

    public CartListView(Account account)
    {
        this.buyer = account;
        this.cartList = new CartList(buyer);
        this.cartList.syncCart();
    }


    public static void main(String[] args) 
    {
        Account acc = new Account("A01");
        CartListView view = new CartListView(acc);
        System.out.println(view.cartList.toString());
    }
}