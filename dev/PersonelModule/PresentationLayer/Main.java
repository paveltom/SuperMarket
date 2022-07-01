package PersonelModule.PresentationLayer;

import DAL.Delivery_Personnel.DTO.WorkerDTO;
import PersonelModule.BusinessLayer.ServiceLayer.Service;

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
    public void main(Service serv, WorkerDTO currentUser) {
        //Init();
        service = serv;
        Scanner scan = new Scanner(System.in);
        System.out.println("Program Initiated");
        System.out.println("Please Enter Next Command");
        System.out.println("0. Exit\n1. Add Worker\n2. Delete Worker \n3. Change Personal Data\n4. Change Workers Data\n5. Gets \n6. Shift");
        String command = scan.nextLine();
     /*   while (!command.equals("x"))
        {
            String[] data = command.split("#");
            switch (data[0]){
                case "Init":
                    Init();
                    System.out.println("Initialised successfully");
                    break;
                case "AddWorker":
                    System.out.println("Choose the type of the worker:\n1. PersonnelManager\n2. Cashier\n3. StoreKeeper\n4. Usher\n5. LogisticsManager\n6. Driver");
                    String c1 = scan.nextLine();
                    switch (c1){
                        case "1":
                            System.out.println("Type the worker details in the following order:\n<Id>#<Name>#<SMQualification>#<BankDetails>#<Pay>#<StartDate>#<SocialDetails>");
                            String c12 = scan.nextLine();
                            String[] d12 = c12.split("#");
                            while(!d12[0].equals("x")){
                                String ans = service.AddWorker(d12[0],d12[1],"PersonnelManager",d12[2],d12[3],Double.parseDouble(d12[4]),d12[5],d12[6]);
                                System.out.println(ans);
                                if(ans.equals("Added worker successfully")) break;
                                else{
                                    System.out.println("Try again");
                                    c12 = scan.nextLine();
                                    d12 = c12.split("#");
                                }
                            }
                            break;
                        case "2":
                            System.out.println("Type the worker details in the following order:\n<Id>#<Name>#<SMQualification>#<BankDetails>#<Pay>#<StartDate>#<SocialDetails>");
                            String c22 = scan.nextLine();
                            String[] d22 = c22.split("#");
                            while(!d22[0].equals("x")){
                                String ans = service.AddWorker(d22[0],d22[1],"Cashier",d22[2],d22[3],Double.parseDouble(d22[4]),d22[5],d22[6]);
                                System.out.println(ans);
                                if(ans.equals("Added worker successfully")) break;
                                else{
                                    System.out.println("Try again");
                                    c22 = scan.nextLine();
                                    d22 = c22.split("#");
                                }
                            }
                            break;
                        case "3":
                            System.out.println("Type the worker details in the following order:\n<Id>#<Name>#<SMQualification>#<BankDetails>#<Pay>#<StartDate>#<SocialDetails>");
                            String c32 = scan.nextLine();
                            String[] d32 = c32.split("#");
                            while(!d32[0].equals("x")){
                                String ans = service.AddWorker(d32[0],d32[1],"StoreKeeper",d32[2],d32[3],Double.parseDouble(d32[4]),d32[5],d32[6]);
                                System.out.println(ans);
                                if(ans.equals("Added worker successfully")) break;
                                else{
                                    System.out.println("Try again");
                                    c32 = scan.nextLine();
                                    d32 = c32.split("#");
                                }
                            }
                            break;
                        case "4":
                            System.out.println("Type the worker details in the following order:\n<Id>#<Name>#<SMQualification>#<BankDetails>#<Pay>#<StartDate>#<SocialDetails>");
                            String c42 = scan.nextLine();
                            String[] d42 = c42.split("#");
                            while(!d42[0].equals("x")){
                                String ans = service.AddWorker(d42[0],d42[1],"Usher",d42[2],d42[3],Double.parseDouble(d42[4]),d42[5],d42[6]);
                                System.out.println(ans);
                                if(ans.equals("Added worker successfully")) break;
                                else{
                                    System.out.println("Try again");
                                    c42 = scan.nextLine();
                                    d42 = c42.split("#");
                                }
                            }
                            break;
                        case "5":
                            System.out.println("Type the worker details in the following order:\n<Id>#<Name>#<SMQualification>#<BankDetails>#<Pay>#<StartDate>#<SocialDetails>");
                            String c52 = scan.nextLine();
                            String[] d52 = c52.split("#");
                            while(!d52[0].equals("x")){
                                String ans = service.AddWorker(d52[0],d52[1],"LogisticsManager",d52[2],d52[3],Double.parseDouble(d52[4]),d52[5],d52[6]);
                                System.out.println(ans);
                                if(ans.equals("Added worker successfully")) break;
                                else{
                                    System.out.println("Try again");
                                    c52 = scan.nextLine();
                                    d52 = c52.split("#");
                                }
                            }
                            break;
                        case "6":
                            System.out.println("Type the worker details in the following order:\n<Id>#<Name>#<SMQualification>#<BankDetails>#<Pay>#<StartDate>#<SocialDetails>#<VehicleCategory>#<LivingArea>#<Cellphone>");
                            String c62 = scan.nextLine();
                            String[] d62 = c62.split("#");
                            while(!d62[0].equals("x")){
                                String ans = service.AddDriver(d62[0],d62[1],"Driver",d62[2],d62[3],Double.parseDouble(d62[4]),d62[5],d62[6],d62[7],d62[8],d62[9]);
                                System.out.println(ans);
                                if(ans.equals("Added Driver successfully")) break;
                                else{
                                    System.out.println("Try again");
                                    c62 = scan.nextLine();
                                    d62 = c62.split("#");
                                }
                            }
                            break;
                    }

                    break;
                case "DeleteWorker":
                    System.out.println(service.DeleteWorker(data[1]));
                    break;
                case "ChangeName":
                    System.out.println(service.ChangeName(data[1],data[2]));
                    break;
                case "ChangeJob":
                    System.out.println("Enter the Job you want to change into :\n PersonnelManager\n Cashier\n StoreKeeper\n Usher\n LogisticsManager\n Driver");
                    String c2 = scan.nextLine();
                    switch (c2)
                    {
                        case "Driver":
                            System.out.println("Type the additional driver details in this format:\n<Id>#<VehicleCategory>#<LivingArea>#<Cellphone>");
                            String driverInfo = scan.nextLine();
                            String[] driverInfoSplit = driverInfo.split("#");
                            System.out.println(service.ChangeToDriver(driverInfoSplit[0],driverInfoSplit[1],driverInfoSplit[2],driverInfoSplit[3]));
                            break;
                        default:
                            System.out.println("Type the worker Id");
                            String wId = scan.nextLine();
                            System.out.println(service.ChangeJob(wId,c2));
                    }
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
        }*/

        while(!command.equals("0"))
        {
            switch (command)
            {
                case "1":
                    if(!currentUser.getParamVal("Job").equals("PersonnelManager"))
                    {
                        System.out.println("You are not permitted to go into this menu, you need to be a PersonnelManager inorder to access these functions");
                        break;
                    }
                    else {
                        String Id, Name, BankDetails, Pay, StartDate, SocialDetails, JobNumber, SMQulNum, SMqual, ans, Vcategory, LivingArea, CellPhone;
                        double _pay;
                        System.out.println("Enter Worker Id");
                        Id = scan.nextLine();
                        System.out.println("Enter Worker Name");
                        Name = scan.nextLine();
                        System.out.println("Shift Manager Qualifications?(default no)\n1.Yes\n2.No");
                        SMQulNum = scan.nextLine();
                        switch (SMQulNum) {
                            case "1":
                                SMqual = "yes";
                                break;
                            default:
                                SMqual = "no";
                                break;
                        }
                        System.out.println("Enter Worker BankDetails");
                        BankDetails = scan.nextLine();
                        System.out.println("Enter Worker Pay");
                        Pay = scan.nextLine();
                        try {
                            _pay = Double.parseDouble(Pay);
                        } catch (Exception e) {
                            System.out.println("Illegal Number");
                            break;
                        }
                        System.out.println("Enter Worker StartDate In Format DD/MM/YYYY");
                        StartDate = scan.nextLine();
                        System.out.println("Enter Worker Social Details");
                        SocialDetails = scan.nextLine();
                        System.out.println("Select Job:\n1. PersonnelManager\n2. Cashier\n3. StoreKeeper\n4. Usher\n5. LogisticsManager\n6. Driver");
                        JobNumber = scan.nextLine();
                        switch (JobNumber) {
                            case "1":
                                ans = service.AddWorker(Id, Name, "PersonnelManager", SMqual, BankDetails, _pay, StartDate, SocialDetails);
                                System.out.println(ans);
                                break;
                            case "2":
                                ans = service.AddWorker(Id, Name, "Cashier", SMqual, BankDetails, _pay, StartDate, SocialDetails);
                                System.out.println(ans);
                                break;
                            case "3":
                                ans = service.AddWorker(Id, Name, "StoreKeeper", SMqual, BankDetails, _pay, StartDate, SocialDetails);
                                System.out.println(ans);
                                break;
                            case "4":
                                ans = service.AddWorker(Id, Name, "Usher", SMqual, BankDetails, _pay, StartDate, SocialDetails);
                                System.out.println(ans);
                                break;
                            case "5":
                                ans = service.AddWorker(Id, Name, "LogisticsManager", SMqual, BankDetails, _pay, StartDate, SocialDetails);
                                System.out.println(ans);
                                break;
                            case "6":
                                System.out.println("Enter Driver Vehicle Category");
                                Vcategory = scan.nextLine();
                                System.out.println("Enter Driver Living Area");
                                LivingArea = scan.nextLine();
                                System.out.println("Enter Driver Cellphone");
                                CellPhone = scan.nextLine();
                                ans = service.AddDriver(Id, Name, "Driver", SMqual, BankDetails, _pay, StartDate, SocialDetails, Vcategory, LivingArea, CellPhone);
                                System.out.println(ans);
                                break;
                        }
                        break;
                    }
                case "2":
                    if(!currentUser.getParamVal("Job").equals("PersonnelManager"))
                    {
                        System.out.println("You are not permitted to go into this menu, you need to be a PersonnelManager inorder to access these functions");
                        break;
                    }
                    else {
                        String DId;
                        System.out.println("Enter Worker Id");
                        DId = scan.nextLine();
                        String ans = service.DeleteWorker(DId);
                        System.out.println(ans);
                        break;
                    }
                case "3":
                    String PChangeCommand;
                    System.out.println("Your Information:");
                    System.out.println(service.GetWorkerString(currentUser.wId));
                    System.out.println("Select What personal information you want to change:\n1.Name\n2.Bank Details\n3.Availability");
                    PChangeCommand = scan.nextLine();
                    switch (PChangeCommand){
                        case "1":
                            System.out.println("Enter New Name");
                            System.out.println(service.ChangeName(currentUser.wId,scan.nextLine()));
                            break;
                        case "2":
                            System.out.println("Enter New Bank Details");
                            System.out.println(service.ChangeBank(currentUser.wId,scan.nextLine()));
                            break;
                        case "3":
                            System.out.println("Enter New Availability");
                            System.out.println(service.changeAvailability(currentUser.wId,scan.nextLine()));
                            break;
                        default:
                            System.out.println("Illegal selection, try again");
                            break;
                    }
                    break;
                case "4":
                    if(!currentUser.getParamVal("Job").equals("PersonnelManager"))
                    {
                        System.out.println("You are not permitted to go into this menu, you need to be a PersonnelManager inorder to access these functions");
                    }
                    else {
                        String Ccommand,Cid,Cnewname,Cnewjob,CnewBank,CnewPay,CnewStart,CnewSocial,CnewAvail,CVehicleCat,CArea,CCellphone;
                        System.out.println("Select what you want to change:\n1.Name\n2.Job\n3.SMQual\n4.Bank Details\n5.Pay\n6.Start Date\n7.Social Conditions\n8.Availability");
                        Ccommand = scan.nextLine();
                        switch (Ccommand)
                        {
                            case "1":
                                System.out.println("Enter Worker Id");
                                Cid = scan.nextLine();
                                System.out.println("Enter New Name");
                                System.out.println(service.ChangeName(Cid,scan.nextLine()));
                                break;
                            case "2":
                                System.out.println("Enter Worker Id");
                                Cid = scan.nextLine();
                                System.out.println("Select Job to change to:\n1. PersonnelManager\n2. Cashier\n3. StoreKeeper\n4. Usher\n5. LogisticsManager\n6. Driver");
                                switch (scan.nextLine())
                                {
                                    case "1":
                                        System.out.println(service.ChangeJob(Cid,"PersonnelManager"));
                                        break;
                                    case "2":
                                        System.out.println(service.ChangeJob(Cid,"Cashier"));
                                        break;
                                    case "3":
                                        System.out.println(service.ChangeJob(Cid,"StoreKeeper"));
                                        break;
                                    case "4":
                                        System.out.println(service.ChangeJob(Cid,"Usher"));
                                        break;
                                    case "5":
                                        System.out.println(service.ChangeJob(Cid,"LogisticsManager"));
                                        break;
                                    case "6":
                                        System.out.println("Enter Driver Vehicle Licence");
                                        CVehicleCat = scan.nextLine();
                                        System.out.println("Enter Driver Living Area");
                                        CArea = scan.nextLine();
                                        System.out.println("Enter Driver Cellphone");
                                        CCellphone = scan.nextLine();
                                        System.out.println(service.ChangeToDriver(Cid,CVehicleCat,CArea,CCellphone));
                                        break;
                                    default:
                                        System.out.println("Illegal selection, try again");
                                        break;

                                }
                                break;
                            case "3":
                                System.out.println("Select Shift Manager Qualification(default is No):\n1.Yes\n2.No");
                                switch (scan.nextLine())
                                {
                                    case "1":
                                        System.out.println("Enter Worker id");
                                        System.out.println(service.ChangeQual(scan.nextLine(),"yes"));
                                        break;
                                    default:
                                        System.out.println("Enter Worker id");
                                        System.out.println(service.ChangeQual(scan.nextLine(),"no"));
                                        break;
                                }
                                break;
                            case "4":
                                System.out.println("Enter Worker Id");
                                Cid = scan.nextLine();
                                System.out.println("Enter New Bank Details");
                                System.out.println(service.ChangeBank(Cid,scan.nextLine()));
                                break;
                            case "5":
                                System.out.println("Enter Worker Id");
                                Cid = scan.nextLine();
                                System.out.println("Enter New Pay");
                                String newpay = scan.nextLine();
                                double _newPay;
                                try{
                                    _newPay = Double.parseDouble(newpay);
                                }
                                catch (Exception e)
                                {
                                    System.out.println("Pay was invalid, try again");
                                    break;
                                }
                                System.out.println(service.ChangePay(Cid,_newPay));
                                break;
                            case "6":
                                System.out.println("Enter Worker Id");
                                Cid = scan.nextLine();
                                System.out.println("Enter New Start Date (DD/MM/YYY format)");
                                System.out.println(service.ChangeStart(Cid,scan.nextLine()));
                                break;
                            case "7":
                                System.out.println("Enter Worker Id");
                                Cid = scan.nextLine();
                                System.out.println("Enter New Social Conditions");
                                System.out.println(service.ChangeSocial(Cid,scan.nextLine()));
                                break;
                            case "8":
                                System.out.println("Enter Worker Id");
                                Cid = scan.nextLine();
                                System.out.println("Enter New Availability");
                                System.out.println(service.changeAvailability(Cid,scan.nextLine()));
                                break;
                        }
                    }
                    break;
                case "5":
                    if(!currentUser.getParamVal("Job").equals("PersonnelManager"))
                    {
                        System.out.println("You are not permitted to go into this menu, you need to be a PersonnelManager inorder to access these functions");
                    }
                    else{
                        System.out.println("Select Functin:\n1.Get Worker string by Id\n2.Get all workers\n3.Get workers by job");
                        switch (scan.nextLine())
                        {
                            case "1":
                                System.out.println("Enter worker id");
                                System.out.println(service.GetWorkerString(scan.nextLine()));
                                break;
                            case "2":
                                System.out.println(service.GetAllWorkersString());
                                break;
                            case "3":
                                System.out.println("Enter job");
                                System.out.println(service.GetWorkersByJob(scan.nextLine()));
                                break;
                            default:
                                System.out.println("Illegal selection, try again");
                                break;
                        }
                    }
                    break;
                case "6":
                    if(!currentUser.getParamVal("Job").equals("PersonnelManager"))
                    {
                        System.out.println("You are not permitted to go into this menu, you need to be a PersonnelManager inorder to access these functions");
                    }
                    else {
                        System.out.println("Select Function:\n1.Add Shift\n.Show shift history\n3.Show availability");
                        switch (scan.nextLine()) {
                            case "1":
                                String Sdate,Smanager,SworkerList;
                                Integer Stype;
                                System.out.println(service.showAvailability());
                                System.out.println("Enter Date (format DD/MM/YYYY)");
                                Sdate = scan.nextLine();
                                System.out.println("Is the shift a day shift or a night shift?(default day)\n1.Day\n2.Night");
                                switch (scan.nextLine())
                                {
                                    case "2":
                                        Stype = 1;
                                        break;
                                    default:
                                        Stype = 0;
                                        break;
                                }
                                System.out.println("Enter shift manager Id");
                                Smanager = scan.nextLine();
                                System.out.println("Enter Worker list (format: <Job> <Id> <Id> .. | <Job> <Id> ..");
                                SworkerList = scan.nextLine();
                                String Sans = service.addShift(Sdate,Stype,Smanager,SworkerList);
                                System.out.println(Sans);
                                break;
                            case "2":
                                System.out.println(service.shiftHistory());
                                break;
                            case "3":
                                System.out.println(service.showAvailability());
                                break;
                            default:
                                System.out.println("Illegal selection, try again");
                                break;
                        }

                    }
                    break;
                default:
                    System.out.println("Illegal selection, try again");
                    break;

            }
            System.out.println("0. Exit\n1. Add Worker\n2. Delete Worker \n3. Change Personal Data\n4. Change Workers Data\n5. Gets \n6. Shift");
            command = scan.nextLine();
        }
        System.out.println("Program Ended");
    }
}