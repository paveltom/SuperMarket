package com.company;

import DAL.SupplierDataMapper;
import SuppliersModule.Presentation.SupplierCLI;
//import com.company.PresentationLayer.StockCLI;
//import StockModule.PresentationLayer.StockCLI;


import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        SupplierDataMapper sdm = new SupplierDataMapper();
        boolean[] sDays = {true, false, true, false, true, true, true};
        //sdm.addSupplier("00", "yossi", "beer7", "bank", true, true, sDays, 2, 8, true);
        sdm.loadSuppliers();

//        SupplierCLI suppCLI = new SupplierCLI();
//        StockCLI stockCLI = new StockCLI();
//        Scanner scanner = new Scanner(System.in);
//
//        String input = "";
//        label:
//        while (true) {
//            System.out.println("Hello\n" +
//                    "press 1 for stock system\n" +
//                    "press 2 for suppliers system\n" +
//                    "press exit to exit the system\n");
//            input = scanner.nextLine();
//            switch (input) {
//                case "1":
//                    stockCLI.run();
//                    break;
//                case "2":
//                    suppCLI.run();
//                    break;
//                case "exit":
//                    break label;
//                default:
//                    System.out.println("Invalid input");
//                    break;
//            }
//        }
    }

}
