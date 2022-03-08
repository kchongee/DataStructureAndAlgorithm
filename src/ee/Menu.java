// package ee;

// public class Menu {
//     public static void main(String[] args) {
//         // #region : *Products
//         System.out.println("List of product:");
//         // while loop through the products of the seller have been added
//         System.out.println();

//         System.out.println("Menu");
//         System.out.println();        
//         System.out.println("1. Add a new Product");
//         System.out.println("2. Edit a Product");
//         System.out.println("3. Delete a Product");
//         System.out.println("0. Back");
//         userSelection = askForMenuSelection(scanner);
//         clearScreen();        

//         // #region : **Add a new Product
//         // call addNewProduct()        
//         clearScreen();        
//         // #endregion

//         // #region : **Edit a Product
//         System.out.println("List of product:");
//         // while loop throug the products of the seller have been added
//         System.out.println();
                     
//         System.out.println("0. Back");
//         userSelection = askForMenuSelection(scanner,"Choose the product you want to edit: ");        
//         clearScreen();        
//         // #endregion
        
//         // #region : ***Edit a Product (NEXT step)
//         System.out.println("Product details:");
//         // print the details of the product, printDetails()
//         System.out.println();
                     
//         System.out.println("0. Back");
//         userSelection = askForMenuSelection(scanner,"Choose the detail you want to edit: ");
//         // getTheDetailDataType();
//         // askFor[DataType]Input();
//         // replace the detail
//         clearScreen();        
//         // #endregion

//         // #region : **Delete a Product
//         System.out.println("Product details:");
//         // printDetails()
//         System.out.println();
         
//         System.out.println("0. Back");
//         userSelection = askForMenuSelection(scanner,"Choose the product you want to delete: ");
//         // delete the product that selected
//         clearScreen();

//         // #endregion

//         // #endregion                


//         // #region : *Orders
//         System.out.println("List of order:");
//         // while loop through the order of the seller received
//         System.out.println();
        
//         System.out.println("Menu");
//         System.out.println();        
//         System.out.println("1. Shipout Order");        
//         System.out.println("0. Back");
//         userSelection = askForMenuSelection(scanner);
//         clearScreen();

//         // #region : **Shipout Order
//         System.out.println("List of order:");
//         // while loop through the order of the seller received
//         System.out.println();

//         System.out.println("0. Back");
//         userSelection = askForMenuSelection(scanner,"Choose the order wanna ship out: ");
//         // order.shipOut(); change the status of order        
//         clearScreen();

//         // #endregion

//         // #endregion


//         // #region : *Followers
//         System.out.println("List of follower:");
//         // while loop through the follower of the seller
//         System.out.println();
//         // #endregion
//     }
// }


//  // #region : **Open Live Room
//  System.out.println("Menu");
//  System.out.println();        
//  System.out.println("1. Create Catalog");
//  System.out.println("0. Back");
//  userSelection = askForMenuSelection(scanner);
//  clearScreen();

//  // #region : ***Create Catalog
//  System.out.println("List of product:");
//  // while loop throug the products of the seller have been added
//  System.out.println();

//  System.out.println("Menu");
//  System.out.println();        
//  System.out.println("1. Select Product");
//  System.out.println("2. Add New Product");
//  System.out.println("0. Back");
//  userSelection = askForMenuSelection(scanner);
//  clearScreen();
//  // #endregion

//  // #region : ****Select Product
//  System.out.println("List of product:");
//  // while loop throug the products of the seller have been added
//  System.out.println();
         
//  System.out.println("0. Back");
//  userSelection = askForMenuSelection(scanner);
//  clearScreen();
//  // #endregion

//  // #region : ****Add New Product        
//  String productName = askForStringInput(scanner,"Enter the product name:");        
//  String productPrice = askForStringInput(scanner,String.format("Enter the price for %s",productName));
//  // add Product into ProductList of the seller page
//  clearScreen();
//  // #endregion

//  // #endregion

//  // #region : **Schedule Live Room
//  System.out.println("Room that have been scheduled:");
//  // while loop through the room of the seller scheduled
//  System.out.println();
 
//  System.out.println("Menu");
//  System.out.println();                
//  System.out.println("1. Schedule a Room");
//  System.out.println("2. Edit a Scheduled Room");
//  System.out.println("3. Delete a Scheduled Room");
//  System.out.println("0. Back");
//  userSelection = askForMenuSelection(scanner);
//  clearScreen();

//  // #region : ***Schedule a Room
//  LocalDate title = askForDateInput(scanner,"Enter the room title:");
//  LocalDate date = askForDateInput(scanner,"Enter the date:");
//  LocalTime time = askForTimeInput(scanner,"Enter the time:");        
//  // call createCatalog()
//  clearScreen();
//  // #endregion

//  // #region : ***Edit a Scheduled Room
//  System.out.println("Room that have been scheduled:");
//  // while loop throug the room of the seller scheduled
//  System.out.println();
              
//  System.out.println("0. Back");
//  userSelection = askForMenuSelection(scanner,"Choose the room you want to reschedule: ");
//  clearScreen();
//  // #endregion

//  // #region : ****Edit a Scheduled Room (NEXT step)
//  // call scheduleARoom()
//  clearScreen();
//  // #endregion

//  // #region : ***Delete a Scheduled Room
//  System.out.println("Room that have been scheduled:");
//  // while loop throug the room of the seller scheduled
//  System.out.println();
 
//  System.out.println("0. Back");
//  userSelection = askForMenuSelection(scanner,"Choose the scheduled room you want to delete: ");
//  // delete the room that selected
//  clearScreen();
//  // #endregion

//  // #endregion        

//  // #endregion