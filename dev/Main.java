package com.company;

import com.company.StockModule.PresentationLayer.StockCLI;
import com.company.SuppliersModule.Presentation.SupplierCLI;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        SupplierCLI suppCLI = new SupplierCLI();
        StockCLI stockCLI = new StockCLI();
        Scanner scanner = new Scanner(System.in);

        String input = "";
        label:
        while (true) {
            System.out.println("Hello\n" +
                    "press 1 for stock system\n" +
                    "press 2 for suppliers system\n" +
                    "press exit to exit the system\n");
            input = scanner.nextLine();
            switch (input) {
                case "1":
                    stockCLI.run();
                    break;
                case "2":
                    suppCLI.run();
                    break;
                case "exit":
                    break label;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        }
    }

}
