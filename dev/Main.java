import DeliveryModule.PresentationLayer.PresentationController;
import PersonelModule.BusinessLayer.ServiceLayer.Service;
import DAL.Delivery_Personnel.DTO.WorkerDTO;
import StockModule.PresentationLayer.StockCLI;
import SuppliersModule.Presentation.SupplierCLI;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){

        // Stock and Suppliers main need to be added!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        PersonelModule.PresentationLayer.Main pmMain = new PersonelModule.PresentationLayer.Main();
        Service pmService = new Service();
        SupplierCLI suppCLI = new SupplierCLI();
        StockCLI stockCLI = new StockCLI();

        PresentationController delPC = new PresentationController(pmService);
        Scanner scanner = new Scanner(System.in);
        String input = "-1";
        WorkerDTO currentUser = null;

        while(input != "0") {
            while(currentUser == null)
            {
                System.out.println("Please LogIn Using an Id");
                input = scanner.nextLine();
                currentUser = pmService.Login(input);
                if(currentUser == null)
                    System.out.println("Id was incorrect or invalid please try again");
            }
            System.out.println("0. Exit \n1. Delivery module \n2. Personnel module\n3. Stock module\n4. Supplier module");
            System.out.println("Choose option:");
            input = scanner.nextLine();
            switch (input){
                case "0":
                    System.out.println("exiting...");
                    System.exit(0);
                    break;

                case "1":
                    if(currentUser.getParamVal("Job").equals("PersonnelManager") || currentUser.getParamVal("Job").equals("StoreKeeper") || currentUser.getParamVal("Job").equals("LogisticsManager")) {
                        delPC.run();
                    }
                    else {
                        System.out.println("You are not permitted to go into this menu, you need to be a StoreKeeper inorder to access this menu");
                    }
                    break;
                case "2":
                    pmMain.main(pmService);
                    break;
                case "3":
                    if(currentUser.getParamVal("Job").equals("PersonnelManager") || currentUser.getParamVal("Job").equals("StoreKeeper")) {
                        stockCLI.run();
                    }else {
                        System.out.println("You are not permitted to go into this menu, you need to be a PersonnelManager inorder to access this menu");
                    }
                    break;
                case "4":
                    if(currentUser.getParamVal("Job").equals("PersonnelManager") || currentUser.getParamVal("Job").equals("LogisticsManager")) {
                        suppCLI.run();
                    }else {
                        System.out.println("You are not permitted to go into this menu, you need to be a PersonnelManager inorder to access this menu");
                    }
                    break;
                default:
                    System.out.println("Not a valid option...");
            }
        }

    }

}




// ===================================================================================================


//import DeliveryModule.PresentationLayer.PresentationController;
//import PersonelModule.BusinessLayer.ServiceLayer.Service;
//
//import java.util.Scanner;
//
//public class Main {
//    public static void main(String[] args){
//
//        // Stock and Suppliers main need to be added!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//
//        PersonelModule.PresentationLayer.Main pmMain = new PersonelModule.PresentationLayer.Main();
//        Service pmService = new Service();
//        PresentationController delPC = new PresentationController(pmService);
//        Scanner scanner = new Scanner(System.in);
//        String input = "-1";
//
//        while(input != "0") {
//            System.out.println("0. Exit \n1. Delivery module \n2. Personnel module");
//            System.out.println("Choose option:");
//            input = scanner.nextLine();
//            switch (input){
//                case "0":
//                    System.out.println("exiting...");
//                    System.exit(0);
//                    break;
//
//                case "1":
//                    delPC.run();
//                    break;
//
//                case "2":
//                    pmMain.main(pmService);
//                    break;
//
//                default:
//                    System.out.println("Not a valid option...");
//            }
//        }
//
//    }
//
//}
