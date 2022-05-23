package StockModule.PresentationLayer;

import StockModule.BusinessLogicLayer.Type;
import StockModule.ServiceLayer.Service;

import java.util.*;

public class StockCLI {

    static int SelectedBranchID = -1;
    static boolean branchSelected = false;

    public void run() {

        Service s = new Service();

        Scanner toRead = new Scanner(System.in);

        String message = "";
        System.out.println("Hello user welcome to our shop.");
        System.out.println("You can always type 'Help' in order to get list of available commands.");
        System.out.println("To quit, type 'Quit'");
        while(!message.equals("Quit"))
        {
            message = toRead.next();
            Protocol(message,s,toRead);
        }
        System.out.println("Ok, bye.");
    }

    public static void Protocol(String msg,Service s,Scanner toRead){

        if(msg.equals("Help")){
            System.out.println("List of commands : ");
            System.out.println(" 'LoadData' - Loads default data.");
            System.out.println(" 'NewBranch' - Add new branch.");
            System.out.println(" 'RemoveBranch' - Deletes branch.");
            System.out.println(" 'SelectBranch' - Use a branch to control data on.");

            if(branchSelected){
                System.out.println(" 'QuitBranch' - Deselects the selected branch.");
                System.out.println(" 'AddCategory' - Inserts new category.");
                System.out.println(" 'SetSubCategory' - Sets category as subcategory of other category.");
                System.out.println(" 'RemoveCategory' - Deletes category.");
                System.out.println(" 'AddProduct' - Inserts new product.");
                System.out.println(" 'RemoveProduct' - Deletes category.");
                System.out.println(" 'AddPurchase' - Inserts new purchase.");
                System.out.println(" 'RemovePurchase' - Deletes purchase.");
                System.out.println(" 'AddDiscount' - Inserts new discount.");
                System.out.println(" 'RemoveDiscount' - Deletes discount.");
                System.out.println(" 'AddItem' - Inserts new item.");
                System.out.println(" 'ReduceItemAmount' - Reduce amount of existing item.");
                System.out.println(" 'RemoveItem' - Deletes item.");
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.out.println("'ShowProducts' - Prints products report.");
                System.out.println("'ShowPurchases' - Prints purchases report.");
                System.out.println("'ShowDiscount' - Prints discount report.");
                System.out.println("'ShowCategories' - Prints categories report.");
                System.out.println("'ShowItems' - Prints stock report.");
                System.out.println("'ShowItemsByCategory' - Prints stock report only on a specific category.");
                System.out.println("'ShowDefectedItems' - Prints stock report of Defective products.");
                System.out.println("'ShowExpiredItems' - Prints stock report of Expired products.");
            }
        }

        else if(msg.equals("LoadData")){
            s.LoadDefaultData();
            System.out.println("Data loaded successfully.");
        }

        else if(msg.equals("NewBranch")){
            System.out.println("Enter the branch name : ");
            if(!s.addNewBranch(toRead.next()).ErrorOccured()) {
                System.out.println("New branch created successfully.");
            }
        }

        else if(msg.equals("RemoveBranch")){
            System.out.println("Enter Branch ID to delete : ");
            int toDeleteBranch = toRead.nextInt();
            if(!s.deleteBranch(toDeleteBranch).ErrorOccured())
                System.out.println("Branch removed successfully.");
            if(SelectedBranchID==toDeleteBranch)
            {
                branchSelected = false;
                SelectedBranchID = -1;
                System.out.println("Left branch successfully.");
            }

        }
        else if(msg.equals("SelectBranch")){
            System.out.println("Current available branches : ");
            System.out.println(s.getBranches().Value);
            System.out.println("Enter the branch number : ");
            int tempBranchID = toRead.nextInt();
            if (s.getBranches().Value.size() > tempBranchID)
            {
                SelectedBranchID = tempBranchID;
                branchSelected = true;
                System.out.println("Branch selected successfully.");
            }
            else
            {
                System.out.println("Branch does not exist, please try again.");
            }

        }
        else if(branchSelected){
            switch (msg) {
                case "QuitBranch":
                    branchSelected = false;
                    SelectedBranchID = -1;
                    System.out.println("Left branch successfully.");
                    break;

                case "AddCategory":
                    System.out.println("Enter new category name : ");
                    if (!s.insertNewCategory(SelectedBranchID, toRead.next()).ErrorOccured())
                        System.out.println("Added category successfully.");
                    break;

                case "SetSubCategory":
                    System.out.println("Enter categoryID you want to set as subcategory : ");
                    int subCategoryID = toRead.nextInt();
                    System.out.println("Enter categoryID you want to set as parent : ");
                    int parentID = toRead.nextInt();
                    if (!s.setSubCategory(SelectedBranchID, subCategoryID, parentID).ErrorOccured())
                        System.out.println("Subcategory set successfully.");
                    break;

                case "RemoveCategory":
                    System.out.println("Enter category ID to delete :");
                    if (!s.deleteCategory(SelectedBranchID, toRead.nextInt()).ErrorOccured())
                        System.out.println("Removed category successfully.");
                    break;

                case "AddProduct":
                    System.out.println("Enter new product name :");
                    String productName = toRead.next();
                    System.out.println("Enter new product manufacturer :");
                    String productManufacturer = toRead.next();
                    System.out.println("Enter the ID of the category which the product will belong :");
                    int categoryID = toRead.nextInt();
                    System.out.println("Enter on which date the product will be supplied :");
                    System.out.println("Year :");
                    int year = toRead.nextInt();
                    System.out.println("Month :");
                    int month = toRead.nextInt();
                    System.out.println("Day :");
                    int day = toRead.nextInt();
                    System.out.println("Enter the minimum demand for this product.");
                    int demand = toRead.nextInt();
                    System.out.println(s.insertNewProduct(SelectedBranchID, productName, productManufacturer, categoryID, new Date(year, month - 1, day), demand).ErrorMessage);
                    break;

                case "RemoveProduct":
                    System.out.print("Enter product ID to remove : ");
                    if (!s.deleteProduct(SelectedBranchID, toRead.nextInt()).ErrorOccured())
                        System.out.print("Product removed successfully.");
                    break;

                case "AddPurchase":
                    System.out.println("Enter date of purchase : ");
                    System.out.println("Year :");
                    int yearAddPurchase = toRead.nextInt();
                    System.out.println("Month :");
                    int monthAddPurchase = toRead.nextInt();
                    System.out.println("Day :");
                    int dayAddPurchase = toRead.nextInt();
                    System.out.println("Enter product ID : ");
                    int productID = toRead.nextInt();
                    System.out.println("Enter fixed price of product : ");
                    int fixedPrice = toRead.nextInt();
                    System.out.println("Enter actual price of product : ");
                    int actualPrice = toRead.nextInt();
                    Map<Integer, Map<Integer, Integer>> products = (Map<Integer, Map<Integer, Integer>>) new HashMap<>().put(productID, new HashMap<>().put(fixedPrice, actualPrice));
                    if (!s.insertNewPurchase(SelectedBranchID, new Date(yearAddPurchase, monthAddPurchase - 1, dayAddPurchase), products).ErrorOccured())
                        System.out.print("Added purchase successfully.");
                    break;

                case "RemovePurchase":
                    System.out.println("Enter purchase ID to remove : ");
                    if (!s.deletePurchase(SelectedBranchID, toRead.nextInt()).ErrorOccured())
                        System.out.print("Removed purchase successfully.");
                    break;

                case "AddDiscount":
                    System.out.println("Enter ID of product to add discount to : ");
                    int ProductID = toRead.nextInt();
                    System.out.println("Enter start date of discount : ");
                    System.out.println("Year :");
                    int yearAddDiscount = toRead.nextInt();
                    System.out.println("Month :");
                    int monthAddDiscount = toRead.nextInt();
                    System.out.println("Day :");
                    int dayAddDiscount = toRead.nextInt();
                    Date startDate = new Date(yearAddDiscount, monthAddDiscount - 1, dayAddDiscount);

                    System.out.println("Enter end date of discount : ");
                    System.out.println("Year :");
                    int year2 = toRead.nextInt();
                    System.out.println("Month :");
                    int month2 = toRead.nextInt();
                    System.out.println("Day :");
                    int day2 = toRead.nextInt();
                    Date endDate = new Date(year2, month2 - 1, day2);
                    System.out.println("Enter amount of discount : ");
                    int amount = toRead.nextInt();
                    System.out.print("Enter type of discount : 'PERCENT' or 'FIXED'");
                    Type t;
                    while (true) {
                        String type = toRead.next();
                        if (type.equals("PERCENT")) {
                            t = Type.PERCENT;
                            break;
                        } else if (type.equals("FIXED")) {
                            t = Type.FIXED;
                            break;
                        } else
                            System.out.println("Wrong value, enter 'PERCENT' or 'FIXED'");

                    }
                    System.out.println(s.insertNewDiscount(SelectedBranchID, ProductID, startDate, endDate, amount, t).ErrorMessage);
                    break;

                case "RemoveDiscount":
                    System.out.println("Enter ID of discount to remove : ");
                    if (!s.deleteDiscount(SelectedBranchID, toRead.nextInt()).ErrorOccured())
                        System.out.print("Removed discount successfully.");
                    break;

                case "AddItem":
                    System.out.println("Enter ID of product of item to add : ");
                    int ProductIDAddItem = toRead.nextInt();
                    System.out.println("Enter location of item : ");
                    String Location = toRead.next();
                    System.out.println("Enter expire date : ");
                    System.out.println("Year :");
                    int yearAddItem = toRead.nextInt();
                    System.out.println("Month :");
                    int monthAddItem = toRead.nextInt();
                    System.out.println("Day :");
                    int dayAddItem = toRead.nextInt();
                    Date expireDate = new Date(yearAddItem, monthAddItem - 1, dayAddItem);
                    System.out.println("Is the item usable ? enter 'Yes' or 'No' ");
                    String isUsable = toRead.next();
                    while (!isUsable.equals("Yes") && !isUsable.equals("No")) {
                        System.out.println("Please enter 'Yes' or 'No'.");
                        isUsable = toRead.next();
                    }
                    System.out.println("Enter amount of items : ");
                    int amountAddItem = toRead.nextInt();
                    if (!s.insertNewItem(SelectedBranchID, ProductIDAddItem, Location, expireDate, isUsable.equals("Yes"), amountAddItem).ErrorOccured())
                        System.out.print("Added item successfully.");
                    break;

                case "ReduceItemAmount":
                    System.out.println("Enter ProductID of the item : ");
                    int ProductIDReduceItemAmount = toRead.nextInt();
                    System.out.println("Enter ID of the item : ");
                    int itemID = toRead.nextInt();
                    System.out.println("Enter how much do you want to reduce : ");
                    int amountToReduce = toRead.nextInt();
                    System.out.println(s.reduceItemAmount(SelectedBranchID, ProductIDReduceItemAmount, itemID, amountToReduce).ErrorMessage);
                    break;

                case "RemoveItem":
                    System.out.println("Enter product ID : ");
                    int ProductIDRemoveItem = toRead.nextInt();
                    System.out.println("Enter item ID : ");
                    int ItemID = toRead.nextInt();
                    System.out.println(s.deleteItem(SelectedBranchID, ProductIDRemoveItem, ItemID).ErrorMessage);
                    break;
                case "ShowProducts":
                    System.out.println(s.getProductsInStock(SelectedBranchID).Value);
                    break;

                case "ShowPurchases":
                    System.out.println(s.getPurchasesHistoryReport(SelectedBranchID).Value);
                    break;

                case "ShowDiscount":
                    System.out.println(s.getCurrentDiscounts(SelectedBranchID).Value);
                    break;

                case "ShowCategories":
                    System.out.println(s.getCategories(SelectedBranchID).Value);
                    break;

                case "ShowItems":
                    System.out.println(s.getStockReport(SelectedBranchID).Value);
                    break;

                case "ShowItemsByCategory":
                    System.out.println("Enter product ID to filter : ");
                    System.out.println(s.getStockReportByCategory(SelectedBranchID, toRead.nextInt()).Value);
                    break;

                case "ShowDefectiveItems":
                    System.out.println(s.getDefectedProductsReport(SelectedBranchID).Value);
                    break;

                case "ShowExpiredItems":
                    System.out.println(s.getExpiredProductsReport(SelectedBranchID).Value);
                    break;

                default:
                    System.out.println("Enter a valid command.");
                    break;
            }
        }
        else
        {
            System.out.println("Enter a valid command.");
        }
    }
}
