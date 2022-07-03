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
        terminate = false;
        while (!terminate) {
            System.out.println(ANSI_WHITE_BACKGROUND + "Stock module - Main Menu :" + ANSI_RESET);
            System.out.println("Hello user welcome to our shop.");
            System.out.println("Select the section you want to go :");
            System.out.println("1 - Load data");
            System.out.println("2 - Products");
            System.out.println("3 - Items");
            System.out.println("4 - Discounts");
            System.out.println("5 - Categories");
            System.out.println("9 - Exit stock module");
            message = toRead.nextLine();
            Protocol(message, s, toRead);
        }
        System.out.println("Ok, bye.");
    }

    public static void Protocol(String msg, Service s, Scanner toRead) {

        switch (msg) {

            case "1": {
                s.LoadDefaultData();
                System.out.println("Data loaded successfully.");
                break;
            }

            case "2": {
                runProductsMenu(s, toRead);
                break;
            }

            case "3": {
                runItemsMenu(s, toRead);
                break;
            }

            case "4": {
                runDiscountsMenu(s, toRead);
                break;
            }

            case "5": {
                runCategoriesMenu(s, toRead);
                break;
            }

            case "9": {
                ExitStockModule();
                break;
            }

            default: {
                System.out.println("Invalid input, try again");
                break;
            }

        }

    }

    private static void ExitStockModule() {
        terminate = true;
    }

    public static void runProductsMenu(Service s, Scanner toRead) {
        System.out.println(ANSI_WHITE_BACKGROUND + "Stock Module - Products Menu :" + ANSI_RESET);
        boolean terminateCurrentMenu = false;
        while (!terminateCurrentMenu) {
            System.out.println("Choose your action : ");
            System.out.println("0 - Back to previous menu");
            System.out.println("1 - Add new product");
            System.out.println("2 - Remove product");
            System.out.println("3 - Show products in catalog");
            System.out.println("4 - Edit product's attributes");
            System.out.println("9 - Exit stock module");

            String msg = toRead.nextLine();

            switch (msg) {
                case "0": {
                    terminateCurrentMenu = true;
                    break;
                }
                case "1": {
                    System.out.println("Enter new product's name :");
                    String productName = toRead.nextLine();
                    System.out.println("Enter new product's manufacturer :");
                    String productManufacturer = toRead.nextLine();
                    System.out.println("Enter new product's weight :");
                    double productWeight = Double.parseDouble(toRead.nextLine());
                    System.out.println("Enter minimum amount of product :");
                    int amountToNotify = Integer.parseInt(toRead.nextLine());
                    System.out.println("Enter the ID of the category which the product will belong :");
                    int categoryID = Integer.parseInt(toRead.nextLine());
                    System.out.println("Enter the demand for this product.");
                    int demand = Integer.parseInt(toRead.nextLine());
                    System.out.println(s.insertNewProduct(productName, productManufacturer,productWeight, amountToNotify, categoryID, demand).ErrorMessage);
                    break;
                }
                case "2": {
                    System.out.print("Enter product ID to remove : ");
                    if (!s.deleteProduct(toRead.nextLine()).ErrorOccurred())
                        System.out.print("Product removed successfully.");
                    break;
                }
                case "3": {
                    System.out.println(s.getProductsInStock().Value);
                    break;
                }
                case "4": {
                    System.out.println("Enter product to edit : ");
                    String productID = toRead.nextLine();
                    System.out.println("Choose Attribute to Edit : ");
                    System.out.println(s.getProductAttributes().Value);
                    int attribute = Integer.parseInt(toRead.nextLine());
                    System.out.println("Enter new value : ");
                    Object value = toRead.nextLine();
                    s.updateProductAttribute(productID, attribute, value);
                    break;
                }
                case "9": {
                    ExitStockModule();
                    terminateCurrentMenu = true;
                    break;
                }
                default: {
                    System.out.println("Invalid input, try again");
                    break;
                }
            }

        }

    }

    public static void runItemsMenu(Service s, Scanner toRead) {
        System.out.println(ANSI_WHITE_BACKGROUND + "Stock Module - Items Menu :" + ANSI_RESET);
        boolean terminateCurrentMenu = false;
        while (!terminateCurrentMenu) {
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
            String msg = toRead.nextLine();

            switch (msg) {
                case "0": {
                    terminateCurrentMenu = true;
                    break;
                }
                case "1": {
                    System.out.println("Enter ID of product of item to add : ");
                    String ProductIDAddItem = toRead.nextLine();
                    System.out.println("Enter location of item : ");
                    String Location = toRead.nextLine();
                    System.out.println("Enter expire date : ");
                    System.out.println("Year :");
                    int yearAddItem = Integer.parseInt(toRead.nextLine());
                    System.out.println("Month :");
                    int monthAddItem = Integer.parseInt(toRead.nextLine());
                    System.out.println("Day :");
                    int dayAddItem = Integer.parseInt(toRead.nextLine());
                    Date expireDate = new Date(yearAddItem, monthAddItem - 1, dayAddItem);
                    System.out.println("Is the item usable ? enter 'Yes' or 'No' ");
                    String isUsable = toRead.nextLine();
                    while (!isUsable.equals("Yes") && !isUsable.equals("No")) {
                        System.out.println("Please enter 'Yes' or 'No'.");
                        isUsable = toRead.nextLine();
                    }
                    System.out.println("Enter amount of items : ");
                    int amountAddItem = Integer.parseInt(toRead.nextLine());
                    if (!s.insertNewItem(ProductIDAddItem, Location, expireDate, isUsable.equals("Yes"), amountAddItem).ErrorOccurred())
                        System.out.print("Added item successfully.");
                    break;

                }
                case "2": {
                    System.out.println("Enter product ID : ");
                    String ProductIDRemoveItem = toRead.nextLine();
                    System.out.println("Enter item ID : ");
                    int ItemID = Integer.parseInt(toRead.nextLine());
                    System.out.println(s.deleteItem(ProductIDRemoveItem, ItemID).ErrorMessage);
                    break;
                }
                case "3": {
                    System.out.println(s.getStockReport().Value);
                    break;
                }
                case "4": {
                    System.out.println("Enter product ID to filter : ");
                    System.out.println(s.getStockReportByCategory(Integer.parseInt(toRead.nextLine())).Value);
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
                    String productID = toRead.nextLine();
                    System.out.println("Enter item's ID :");
                    int itemID = Integer.parseInt(toRead.nextLine());
                    System.out.println("Choose attribute to change :");
                    System.out.print("0 - ProductID");
                    System.out.print("1 - Location");
                    System.out.print("2 - Expire date");
                    System.out.print("3 - is defected? (Enter true/false"); // @TODO - VALIDATE BOOLEAN INPUT
                    System.out.print("4 - is expired? (Enter true/false"); // @TODO - VALIDATE BOOLEAN INPUT
                    System.out.print("5 - Amount");
                    int attribute = Integer.parseInt(toRead.nextLine());
                    System.out.println("Enter the new value :");
                    Object value = toRead.nextLine();

                    System.out.println(s.updateItemAttribute(productID, itemID, attribute, value).ErrorMessage);
                    break;
                }
                case "8": {
                    System.out.println("Enter ProductID of the item : ");
                    String ProductIDReduceItemAmount = toRead.nextLine();
                    System.out.println("Enter ID of the item : ");
                    int itemID = Integer.parseInt(toRead.nextLine());
                    System.out.println("Enter how much do you want to reduce : ");
                    int amountToReduce = Integer.parseInt(toRead.nextLine());
                    System.out.println(s.reduceItemAmount(ProductIDReduceItemAmount, itemID, amountToReduce).ErrorMessage);
                    break;
                }
                case "9": {
                    ExitStockModule();
                    terminateCurrentMenu = true;
                    break;
                }
                default: {
                    System.out.println("Invalid input, try again");
                    break;
                }
            }

        }

    }

    public static void runDiscountsMenu(Service s, Scanner toRead) {
        System.out.println(ANSI_WHITE_BACKGROUND + "Stock Module - Discounts Menu :" + ANSI_RESET);
        boolean terminateCurrentMenu = false;
        while (!terminateCurrentMenu) {
            System.out.println("Choose your action : ");
            System.out.println("0 - Back to previous menu");
            System.out.println("1 - Show current discounts report");
            System.out.println("2 - Insert new discount");
            System.out.println("3 - Delete discount");
            System.out.println("9 - Exit stock module");
            String msg = toRead.nextLine();

            switch (msg) {
                case "0": {
                    terminateCurrentMenu = true;
                    break;
                }
                case "1": {
                    System.out.println(s.getCurrentDiscounts().Value);
                    break;
                }
                case "2": {
                    System.out.println("Enter ID of product to add discount to : ");
                    String ProductID = toRead.nextLine();
                    System.out.println("Enter start date of discount : ");
                    System.out.println("Year :");
                    int yearAddDiscount = Integer.parseInt(toRead.nextLine());
                    System.out.println("Month :");
                    int monthAddDiscount = Integer.parseInt(toRead.nextLine());
                    System.out.println("Day :");
                    int dayAddDiscount = Integer.parseInt(toRead.nextLine());
                    Date startDate = new Date(yearAddDiscount, monthAddDiscount - 1, dayAddDiscount);

                    System.out.println("Enter end date of discount : ");
                    System.out.println("Year :");
                    int year2 = Integer.parseInt(toRead.nextLine());
                    System.out.println("Month :");
                    int month2 = Integer.parseInt(toRead.nextLine());
                    System.out.println("Day :");
                    int day2 = Integer.parseInt(toRead.nextLine());
                    Date endDate = new Date(year2, month2 - 1, day2);
                    System.out.println("Enter amount of discount : ");
                    int amount = Integer.parseInt(toRead.nextLine());
                    System.out.print("Enter type of discount : 'PERCENT' or 'FIXED'");
                    Type t;
                    while (true) {
                        String type = toRead.nextLine();
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
                    if (!s.deleteDiscount(Integer.parseInt(toRead.nextLine())).ErrorOccurred())
                        System.out.print("Removed discount successfully.");
                    break;
                }
                case "9": {
                    ExitStockModule();
                    terminateCurrentMenu = true;
                    break;
                }
                default: {
                    System.out.println("Invalid input, try again");
                    break;
                }
            }
        }
    }

    public static void runCategoriesMenu(Service s, Scanner toRead) {
        System.out.println(ANSI_WHITE_BACKGROUND + "Stock Module - Categories Menu :" + ANSI_RESET);
        boolean terminateCurrentMenu = false;
        while (!terminateCurrentMenu) {
            System.out.println("Choose your action : ");
            System.out.println("0 - Back to previous menu");
            System.out.println("1 - Show categories");
            System.out.println("2 - Insert new category");
            System.out.println("3 - Remove category");
            System.out.println("4 - Set subcategory");
            System.out.println("9 - Exit stock module");
            String msg = toRead.nextLine();

            switch (msg) {
                case "0": {
                    terminateCurrentMenu = true;
                    break;
                }
                case "1": {
                    System.out.println(s.getCategories().Value);
                    break;
                }
                case "2": {
                    System.out.println("Enter new category name : ");
                    if (!s.insertNewCategory(toRead.nextLine()).ErrorOccurred())
                        System.out.println("Added category successfully.");
                    break;
                }
                case "3": {
                    System.out.println("Enter category ID to delete :");
                    if (!s.deleteCategory(Integer.parseInt(toRead.nextLine())).ErrorOccurred())
                        System.out.println("Removed category successfully.");
                    break;
                }
                case "4": {
                    System.out.println("Enter categoryID you want to set as subcategory : ");
                    int subCategoryID = Integer.parseInt(toRead.nextLine());
                    System.out.println("Enter categoryID you want to set as parent : ");
                    int parentID = Integer.parseInt(toRead.nextLine());
                    if (!s.setSubCategory(subCategoryID, parentID).ErrorOccurred())
                        System.out.println("Subcategory set successfully.");
                    break;
                }
                case "9": {
                    ExitStockModule();
                    terminateCurrentMenu = true;
                    break;
                }
                default: {
                    System.out.println("Invalid input, try again");
                    break;
                }
            }

        }
    }


}