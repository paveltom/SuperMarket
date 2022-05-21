import DeliveryModule.PresentationLayer.PresentationController;
import PersonelModule.BusinessLayer.ServiceLayer.Service;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        PersonelModule.PresentationLayer.Main pmMain = new PersonelModule.PresentationLayer.Main();
        Service pmService = new Service();
        PresentationController delPC = new PresentationController(pmService);
        Scanner scanner = new Scanner(System.in);
        String input = "-1";

        while(input != "0") {
            System.out.println("0. Exit \n1. Delivery module \n2. Personnel module");
            System.out.println("Choose option:");
            input = scanner.nextLine();
            switch (input){
                case "0":
                    System.out.println("exiting...");
                    System.exit(0);
                    break;

                case "1":
                    delPC.run();
                    break;

                case "2":
                    pmMain.main(pmService);
                    break;

                default:
                    System.out.println("Not a valid option...");
            }
        }

    }

}
