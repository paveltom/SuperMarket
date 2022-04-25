package PresentationLayer;

import BusinessLayer.ServiceLayer.Service;

import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    public static Service service;

    public static void Init()
    {
        service.AddWorker("111111111","Worker1","PersonnelManager","yes","Bank 003 111111",40.00,"22/9/2020","...");
        service.AddWorker("222222222","Worker2","Cashier","yes","Bank 003 111111",35.00,"22/9/2020","...");
        service.AddWorker("333333333","Worker3","StoreKeeper","no","Bank 003 111111",33.00,"22/9/2020","...");
        service.AddWorker("444444444","Worker4","Usher","no","Bank 003 111111",32.00,"22/9/2020","...");
        service.AddWorker("555555555","Worker5","LogisticsManager","no","Bank 003 111111",38.00,"22/9/2020","...");
        service.AddWorker("666666666","Worker6","Driver","no","Bank 003 111111",33.50,"22/9/2020","...");
        service.changeAvailability("111111111","sun - Morning, tue - Morning");
        service.changeAvailability("222222222","sun - Morning Evening, mon - Evening, tue - Morning");
        service.changeAvailability("333333333","tue - Morning");
        service.changeAvailability("444444444","tue - Morning, wed - Morning Evening");
        service.changeAvailability("555555555","tue - Morning, wed - Evening");
        service.changeAvailability("666666666","tue - Morning, wed - Evening");
        service.addShift("12/04/2022",0,"111111111","PersonnelManager 111111111|Cashier 222222222|StoreKeeper 333333333|Usher 444444444|LogisticsManager 555555555|Driver 666666666");
    }
    public static void main(String[] args) {
        //Init();
        service = new Service();
        Scanner scan = new Scanner(System.in);
        System.out.println("Program Initiated");
        System.out.println("Please Enter Next Command");
        String command = scan.nextLine();
        while (!command.equals("x"))
        {
            String[] data = command.split("#");
            switch (data[0]){
                case "Init":
                    Init();
                    System.out.println("Initialised successfully");
                    break;
                case "AddWorker":
                    System.out.println(service.AddWorker(data[1],data[2],data[3],data[4],data[5],Double.parseDouble(data[6]),data[7],data[8]));
                    break;
                case "DeleteWorker":
                    System.out.println(service.DeleteWorker(data[1]));
                    break;
                case "ChangeId":
                    System.out.println(service.ChangeId(data[1],data[2]));
                    break;
                case "ChangeName":
                    System.out.println(service.ChangeName(data[1],data[2]));
                    break;
                case "ChangeJob":
                    System.out.println(service.ChangeJob(data[1],data[2]));
                    break;
                case "ChangeQual":
                    System.out.println(service.ChangeQual(data[1],data[2]));
                    break;
                case "ChangeBank":
                    System.out.println(service.ChangeBank(data[1],data[2]));
                    break;
                case "ChangePay":
                    System.out.println(service.ChangePay(data[1],Double.parseDouble(data[2])));
                    break;
                    case "ChangeStart":
                    System.out.println(service.ChangeStart(data[1],data[2]));
                    break;
                case "ChangeSocial":
                    System.out.println(service.ChangeSocial(data[1],data[2]));
                    break;
                case "GetWorker":
                    System.out.println(service.GetWorkerString(data[1]));
                    break;
                case "GetWorkers":
                    System.out.println(service.GetAllWorkersString());
                    break;
                case "GetWorkersByJob":
                    System.out.println(service.GetWorkersByJob(data[1]));
                    break;
                case "changeAvailability":
                    System.out.println(service.changeAvailability(data[1],data[2]));
                    break;
                case "addShift":
                    System.out.println(service.showAvailability());
                    String command2 = scan.nextLine();
                    String[] d = command2.split("#");
                    while(!d[0].equals("x")){
                        String ans = service.addShift(d[0],Integer.parseInt(d[1]),d[2],d[3]);
                        System.out.println(ans);
                        if(ans.equals("Added shift successfully")) break;
                        else{
                            System.out.println("Try again");
                            command2 = scan.nextLine();
                            d = command2.split("#");
                        }
                    }
                    break;
                case "shiftHistory":
                    System.out.println(service.shiftHistory());
                    break;
                case "showAvailability":
                    System.out.println(service.showAvailability());
                    break;
            }
            System.out.println("Please Enter Next Command");
            command = scan.nextLine();
        }
        System.out.println("Program Ended");
    }
}