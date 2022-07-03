package StockModule.PresentationLayer;

import StockModule.BusinessLogicLayer.Type;
import StockModule.ServiceLayer.Service;

import java.util.*;

public class StockCLI {
    public void main(String[] args) {
        run();
    }

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    static boolean terminate = false;

    public void run() {

        Service s = new Service();

        Scanner toRead = new Scanner(System.in);

        String message = "";

        while (!terminate) {
            System.out.println(ANSI_WHITE_BACKGROUND+"Main Menu :"+ANSI_RESET);
            System.out.println("Hello user welcome to our shop.");
            System.out.println("Select the section you want to go :");
            System.out.println("1 - Load data");
            System.out.println("2 - Products");
            System.out.println("3 - Items");
            System.out.println("4- Purchases");
            System.out.println("5 - Discounts");
            System.out.println("6 - Categories");
            System.out.println("9 - Exit stock module");
            message = toRead.next();
            Protocol(message, s, toRead);
        }
        System.out.println("Ok, bye.");
    }

    public static void Protocol(String msg, Service s, Scanner toRead) {

        switch (msg) {

            case "1": {
                s.LoadDefaultData();
                System.out.println("Data loaded successfully.");
            }

            case "2": {
                runProductsMenu(s, toRead);
                break;
            }

            case "3": {
                runItemsMenu(s, toRead);
            }

            case "4": {
                runPurchasesMenu(s, toRead);
            }

            case "5": {
                runDiscountsMenu(s, toRead);
            }

            case "6": {
                runCategoriesMenu(s,toRead);
            }

            case "9": {
                ExitStockModule();
                break;
            }

            default: {
                System.out.println("Invalid input, try again");
            }

        }

    }

    private static void ExitStockModule() {
        terminate = true;
    }

    public static void runProductsMenu(Service s, Scanner toRead) {
        System.out.println(ANSI_WHITE_BACKGROUND+"Products Menu :"+ANSI_RESET);
        while (true) {
            System.out.println("Choose your action : ");
            System.out.println("0 - Back to previous menu");
            System.out.println("1 - Add new product");
            System.out.println("2 - Remove product");
            System.out.println("3 - Show products in catalog");
            System.out.println("4 - Edit product's attributes");
            System.out.println("9 - Exit stock module");

            String msg = toRead.next();

            switch (msg) {
                case "0": {
                    break;
                }
                case "1": {
                    System.out.println("Enter new product name :");
                    String productName = toRead.next();
                    System.out.println("Enter new product manufacturer :");
                    String productManufacturer = toRead.next();
                    System.out.println("Enter minimum amount of product :");
                    int amountToNotify = toRead.nextInt();
                    System.out.println("Enter the ID of the category which the product will belong :");
                    int categoryID = toRead.nextInt();
                    System.out.println("Enter the demand for this product.");
                    int demand = toRead.nextInt();
                    System.out.println(s.insertNewProduct(productName, productManufacturer, amountToNotify,categoryID, demand).ErrorMessage);
                    break;
                }
                case "2": {
                    System.out.print("Enter product ID to remove : ");
                    if (!s.deleteProduct(toRead.next()).ErrorOccurred())
                        System.out.print("Product removed successfully.");
                    break;
                }
                case "3": {
                    System.out.println(s.getProductsInStock().Value);
                    break;
                }
                case "4": {
                    System.out.println("Enter product to edit : ");
                    String productID = toRead.next();
                    System.out.println("Choose Attribute to Edit : ");
                    int attribute = toRead.nextInt();
                    System.out.println("Enter new value : ");
                    Object value = toRead.next();
                    s.updateProductAttribute(productID, attribute, value);
                    break;
                }
                case "9": {
                    ExitStockModule();
                    break;
                }
            }
            System.out.println("Invalid input, try again");
        }

    }

    public static void runItemsMenu(Service s, Scanner toRead) {
        System.out.println(ANSI_WHITE_BACKGROUND+"Items Menu :"+ANSI_RESET);
        System.out.println("Choose your action : ");
        System.out.println("0 - Back to previous menu");
        System.out.println("1 - Add new item");
        System.out.println("2 - Remove item");
        System.out.println("3 - Show items in stock");
        System.out.println("4 - Show items by category");
        System.out.println("5 - Show defective items report");
        System.out.println("6 - Show expired items report");
        System.out.println("7 - Edit item's attribute");
        System.out.println("8 - Reduce item's amount");
        System.out.println("9 - Exit stock module");
        String msg = toRead.next();
        while (true) {
            switch (msg) {
                case "0": {
                    break;
                }
                case "1": {
                    System.out.println("Enter ID of product of item to add : ");
                    String ProductIDAddItem = toRead.next();
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
                    if (!s.insertNewItem(ProductIDAddItem, Location, expireDate, isUsable.equals("Yes"), amountAddItem).ErrorOccurred())
                        System.out.print("Added item successfully.");
                    break;

                }
                case "2": {
                    System.out.println("Enter product ID : ");
                    String ProductIDRemoveItem = toRead.next();
                    System.out.println("Enter item ID : ");
                    int ItemID = toRead.nextInt();
                    System.out.println(s.deleteItem(ProductIDRemoveItem, ItemID).ErrorMessage);
                    break;
                }
                case "3": {
                    System.out.println(s.getStockReport().Value);
                    break;
                }
                case "4": {
                    System.out.println("Enter product ID to filter : ");
                    System.out.println(s.getStockReportByCategory(toRead.nextInt()).Value);
                    break;
                }
                case "5": {
                    System.out.println(s.getDefectedProductsReport().Value);
                    break;
                }
                case "6": {
                    System.out.println(s.getExpiredProductsReport().Value);
                    break;
                }
                case "7": {
                    System.out.println("Enter product's ID :");
                    String productID = toRead.next();
                    System.out.println("Enter item's ID :");
                    int itemID = toRead.nextInt();
                    System.out.println("Choose attribute to change :");
                    System.out.print("0 - ProductID");
                    System.out.print("1 - Location");
                    System.out.print("2 - Expire date");
                    System.out.print("3 - is defected? (Enter true/false"); // @TODO - VALIDATE BOOLEAN INPUT
                    System.out.print("4 - is expired? (Enter true/false"); // @TODO - VALIDATE BOOLEAN INPUT
                    System.out.print("5 - Amount");
                    int attribute = toRead.nextInt();
                    System.out.println("Enter the new value :");
                    Object value = toRead.next();

                    System.out.println(s.updateItemAttribute(productID, itemID, attribute, value).ErrorMessage);
                    break;
                }
                case "8": {
                    System.out.println("Enter ProductID of the item : ");
                    String ProductIDReduceItemAmount = toRead.next();
                    System.out.println("Enter ID of the item : ");
                    int itemID = toRead.nextInt();
                    System.out.println("Enter how much do you want to reduce : ");
                    int amountToReduce = toRead.nextInt();
                    System.out.println(s.reduceItemAmount(ProductIDReduceItemAmount, itemID, amountToReduce).ErrorMessage);
                    break;
                }
                case "9": {
                    ExitStockModule();
                    break;
                }
            }
            System.out.println("Invalid input, try again");
        }

    }

    public static void runPurchasesMenu(Service s, Scanner toRead) {
        System.out.println("Not available :(");
        /*
        System.out.println(ANSI_WHITE_BACKGROUND+"Purchases Menu :"+ANSI_RESET);
        System.out.println("Choose your action : ");
        System.out.println("0 - Back to previous menu");
        System.out.println("1 - Show purchases history report");
        System.out.println("2 - Insert new purchase");
        System.out.println("3 - Delete purchase");
        System.out.println("9 - Exit stock module");
        String msg = toRead.next();
        while (true) {
            switch (msg) {
                case "0": {
                    break;
                }
                case "1": {
                    System.out.println("s.getPurchasesHistoryReport().Value");
                    break;
                }
                case "2": {
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
                    if (!s.insertNewPurchase(new Date(yearAddPurchase, monthAddPurchase - 1, dayAddPurchase), products).ErrorOccurred())
                        System.out.print("Added purchase successfully.");
                    break;
                }
                case "3": {
                    System.out.println("Enter purchase ID to remove : ");
                    if (!s.deletePurchase(toRead.nextInt()).ErrorOccurred())
                        System.out.print("Removed purchase successfully.");
                    break;
                }
                case "9": {
                    ExitStockModule();
                    break;
                }
            }
            System.out.println("Invalid input, try again");
        }

         */
    }



    public static void runDiscountsMenu(Service s,Scanner toRead) {
        System.out.println(ANSI_WHITE_BACKGROUND+"Discounts Menu :"+ANSI_RESET);
        System.out.println("Choose your action : ");
        System.out.println("0 - Back to previous menu");
        System.out.println("1 - Show current discounts report");
        System.out.println("2 - Insert new discount");
        System.out.println("3 - Delete discount");
        System.out.println("9 - Exit stock module");
        String msg = toRead.next();
        while (true) {
            switch (msg) {
                case "0": {
                    break;
                }
                case "1": {
                    System.out.println(s.getCurrentDiscounts().Value);
                    break;
                }
                case "2": {
                    System.out.println("Enter ID of product to add discount to : ");
                    String ProductID = toRead.next();
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
                    System.out.println(s.insertNewDiscount(ProductID, startDate, endDate, amount, t).ErrorMessage);
                    break;
                }
                case "3": {
                    System.out.println("Enter ID of discount to remove : ");
                    if (!s.deleteDiscount(toRead.nextInt()).ErrorOccurred())
                        System.out.print("Removed discount successfully.");
                    break;
                }
                case "9": {
                    ExitStockModule();
                    break;
                }
            }
            System.out.println("Invalid input, try again");
        }
    }

    public static void runCategoriesMenu(Service s,Scanner toRead){
        System.out.println(ANSI_WHITE_BACKGROUND+"Categories Menu :"+ANSI_RESET);
        System.out.println("Choose your action : ");
        System.out.println("0 - Back to previous menu");
        System.out.println("1 - Show categories");
        System.out.println("2 - Insert new category");
        System.out.println("3 - Remove category");
        System.out.println("4 - Set subcategory");
        System.out.println("9 - Exit stock module");
        String msg = toRead.next();
        while (true) {
            switch (msg) {
                case "0": {
                    break;
                }
                case "1": {
                    System.out.println(s.getCategories().Value);
                    break;
                }
                case "2": {
                    System.out.println("Enter new category name : ");
                    if (!s.insertNewCategory(toRead.next()).ErrorOccurred())
                        System.out.println("Added category successfully.");
                    break;
                }
                case "3": {
                    System.out.println("Enter category ID to delete :");
                    if (!s.deleteCategory(toRead.nextInt()).ErrorOccurred())
                        System.out.println("Removed category successfully.");
                    break;
                }
                case "4": {
                    System.out.println("Enter categoryID you want to set as subcategory : ");
                    int subCategoryID = toRead.nextInt();
                    System.out.println("Enter categoryID you want to set as parent : ");
                    int parentID = toRead.nextInt();
                    if (!s.setSubCategory(subCategoryID, parentID).ErrorOccurred())
                        System.out.println("Subcategory set successfully.");
                    break;
                }
                case "9": {
                    ExitStockModule();
                    break;
                }
            }
            System.out.println("Invalid input, try again");
        }
    }


}