package Tests;

import DAL.DALController;
import DAL.DTO.DriverDTO;
import DeliveryModule.PresentationLayer.PresentationController;
import PersonelModule.BusinessLayer.ServiceLayer.Service;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ServiceTest {

    Service service;
    DeliveryModule.Facade.Service DMService;
    @Before
    public void setUp() throws Exception {
        service = new Service();
        DMService = new DeliveryModule.Facade.Service(service);
    }

    @Test
    public void addWorker() {
        assertEquals("Added worker successfully",service.AddWorker("212589691","Nikita Kovalchuk","Cashier","yes","Bank 003 111111",30.00,"22/9/2020","..."));
        assertEquals("Deleted worker successfully",service.DeleteWorker("212589691"));//To remove from DB
    }
    @Test
    public void addWorkerIncorrectDetailsTest() {
        assertEquals("Job isn't valid",service.AddWorker("212589691","Nikita Kovalchuk","Cashie1r","yes","Bank 003 111111",30.00,"22/9/2020","..."));
        service.DeleteWorker("212589691");//To remove from DB
    }

    @Test
    public void deleteWorker() {
        service.AddWorker("212589691","Nikita Kovalchuk","Cashier","yes","Bank 003 111111",30.00,"22/9/2020","...");
        assertEquals("Deleted worker successfully",service.DeleteWorker("212589691"));
    }

    @Test
    public void deleteWorkerIncorrectId() {
        service.AddWorker("212589691","Nikita Kovalchuk","Cashier","yes","Bank 003 111111",30.00,"22/9/2020","...");
        assertEquals("Wasnt able to delete worker check input",service.DeleteWorker("212589692"));
        service.DeleteWorker("212589691");//To remove from DB
    }

    @Test
    public void changeName() {
        service.AddWorker("212589691","Nikita Kovalchuk","Cashier","yes","Bank 003 111111",30.00,"22/9/2020","...");
        assertEquals("Changed name successfully",service.ChangeName("212589691","Nikita"));
        assertEquals("Id:212589691\n" +
                "Name:Nikita\n" +
                "Job:Cashier\n" +
                "SMQualification:yes\n" +
                "Bank Details:Bank 003 111111\n" +
                "Pay:30.0\n" +
                "StartDate22/09/2020\n" +
                "Social Conditions:...",service.GetWorkerString("212589691"));
        service.DeleteWorker("212589691");//To remove from DB
    }

    @Test
    public void changeSMQual() {
        service.AddWorker("212589691","Nikita Kovalchuk","Cashier","yes","Bank 003 111111",30.00,"22/9/2020","...");
        assertEquals("Changed SMQualification successfully",service.ChangeQual("212589691","no"));
        assertEquals("Id:212589691\n" +
                "Name:Nikita Kovalchuk\n" +
                "Job:Cashier\n" +
                "SMQualification:no\n" +
                "Bank Details:Bank 003 111111\n" +
                "Pay:30.0\n" +
                "StartDate22/09/2020\n" +
                "Social Conditions:...",service.GetWorkerString("212589691"));
        service.DeleteWorker("212589691");//To remove from DB
    }

    @Test
    public void changeBankDetials() {
        service.AddWorker("212589691","Nikita Kovalchuk","Cashier","yes","Bank 003 111111",30.00,"22/9/2020","...");
        assertEquals("Changed bank details successfully",service.ChangeBank("212589691","Try"));
        assertEquals("Id:212589691\n" +
                "Name:Nikita Kovalchuk\n" +
                "Job:Cashier\n" +
                "SMQualification:yes\n" +
                "Bank Details:Try\n" +
                "Pay:30.0\n" +
                "StartDate22/09/2020\n" +
                "Social Conditions:...",service.GetWorkerString("212589691"));
        service.DeleteWorker("212589691");//To remove from DB
    }


    @Test
    public void GetWorkersByJob()
    {
        service.AddWorker("212589691","Nikita Kovalchuk","Cashier","yes","Bank 003 111111",30.00,"22/9/2020","...");
        service.AddWorker("212589692","Nikita Kovalchuk1","Cashier","yes","Bank 003 111111",30.00,"22/9/2020","...");
        service.AddWorker("212589693","Nikita Kovalchuk2","Usher","yes","Bank 003 111111",30.00,"22/9/2020","...");
        service.AddWorker("212589694","Nikita Kovalchuk3","PersonnelManager","yes","Bank 003 111111",30.00,"22/9/2020","...");
        assertEquals("212589692 Nikita Kovalchuk1\n" +
                "212589691 Nikita Kovalchuk",service.GetWorkersByJob("Cashier"));
        service.DeleteWorker("212589691");
        service.DeleteWorker("212589692");
        service.DeleteWorker("212589693");
        service.DeleteWorker("212589694");

    }


    @Test
    public void getAllWorkersString() {
        service.AddWorker("212589691","Nikita Kovalchuk","Cashier","no","Bank 003 111111",30.00,"22/9/2020","...");
        service.AddWorker("212589692","Nikita Kovalchuk1","Usher","yes","Bank 003 111111",30.00,"22/9/2020","...");
        service.AddWorker("212589693","Nikita Kovalchuk2","StoreKeeper","no","Bank 003 111111",30.00,"22/9/2020","...");
        assertEquals("Id:212589693\n" +
                "Name:Nikita Kovalchuk2\n" +
                "Job:StoreKeeper\n" +
                "SMQualification:no\n" +
                "Bank Details:Bank 003 111111\n" +
                "Pay:30.0\n" +
                "StartDate22/09/2020\n" +
                "Social Conditions:...\n" +
                "Id:212589692\n" +
                "Name:Nikita Kovalchuk1\n" +
                "Job:Usher\n" +
                "SMQualification:yes\n" +
                "Bank Details:Bank 003 111111\n" +
                "Pay:30.0\n" +
                "StartDate22/09/2020\n" +
                "Social Conditions:...\n" +
                "Id:212589691\n" +
                "Name:Nikita Kovalchuk\n" +
                "Job:Cashier\n" +
                "SMQualification:no\n" +
                "Bank Details:Bank 003 111111\n" +
                "Pay:30.0\n" +
                "StartDate22/09/2020\n" +
                "Social Conditions:...",service.GetAllWorkersString());
        service.DeleteWorker("212589691");
        service.DeleteWorker("212589692");
        service.DeleteWorker("212589693");

    }

    //--------------------------------------------------------------------------------
    //-----------------------------Shared Tests---------------------------------------
    @Test
    public void deleteWorkerDriver() {
      //  assertEquals("Added Driver successfully",service.AddDriver("212589691","Nikita Kovalchuk","Driver","yes","Bank 003 111111",30.00,"22/9/2020","...","E","Negev","0525670092"));
        assertEquals("Deleted worker successfully",service.DeleteWorker("212589691"));//To remove from DB
    }
    @Test
    public void AddDriver() {
        assertEquals("Added Driver successfully",service.AddDriver("212589691","Nikita Kovalchuk","Driver","yes","Bank 003 111111",30.00,"22/9/2020","...","E","Negev","0525670092"));
        assertEquals("Deleted worker successfully",service.DeleteWorker("212589691"));//To remove from DB
    }
    @Test
    public void AddDrivers() {
        assertEquals("Added Driver successfully",service.AddDriver("212589691","Nikita Kovalchuk","Driver","yes","Bank 003 111111",30.00,"22/9/2020","...","E","Negev","0525670092"));
        assertEquals("Added Driver successfully",service.AddDriver("212589692","Nikita Kovalchuk","Driver","yes","Bank 003 111111",30.00,"22/9/2020","...","E","Negev","0525670092"));
        assertEquals("Added Driver successfully",service.AddDriver("212589693","Nikita Kovalchuk","Driver","yes","Bank 003 111111",30.00,"22/9/2020","...","E","Negev","0525670092"));

        assertEquals("Deleted worker successfully",service.DeleteWorker("212589691"));//To remove from DB
        assertEquals("Deleted worker successfully",service.DeleteWorker("212589692"));//To remove from DB
        assertEquals("Deleted worker successfully",service.DeleteWorker("212589693"));//To remove from DB

    }
    @Test
    public void AddDriverWithIlligalWorkerData() {
        assertEquals("Pay cannot be below 29.12",service.AddDriver("212589691","Nikita Kovalchuk","Driver","yes","Bank 003 111111",26.00,"22/9/2020","...","AC","Negev","0525670092"));
    }

    @Test
    public void AddDriverWithIlligalWorkerData1() {
        assertEquals("Id has to be 9 numbers",service.AddDriver("21258969","Nikita Kovalchuk","Driver","yes","Bank 003 111111",30.00,"22/9/2020","...","E","Negev","0525670092"));
    }
    @Test
    public void DelDriver() {
        assertEquals("Added Driver successfully",service.AddDriver("212589691","Nikita Kovalchuk","Driver","yes","Bank 003 111111",30.00,"22/9/2020","...","E","Negev","0525670092"));
        assertEquals("Deleted worker successfully",service.DeleteWorker("212589691"));//To remove from DB
    }
    @Test
    public void DelMultipleDriver() {
        assertEquals("Added Driver successfully",service.AddDriver("212589691","Nikita Kovalchuk","Driver","yes","Bank 003 111111",30.00,"22/9/2020","...","E","Negev","0525670092"));
        assertEquals("Added Driver successfully",service.AddDriver("212589692","Nikita Kovalchuk","Driver","yes","Bank 003 111111",30.00,"22/9/2020","...","E","Negev","0525670092"));
        assertEquals("Deleted worker successfully",service.DeleteWorker("212589692"));//To remove from DB
        assertEquals("Deleted worker successfully",service.DeleteWorker("212589691"));//To remove from DB
    }
    @Test
    public void FailedDel() {
        assertEquals("Added Driver successfully",service.AddDriver("212589691","Nikita Kovalchuk","Driver","yes","Bank 003 111111",30.00,"22/9/2020","...","E","Negev","0525670092"));
        assertEquals("Wasnt able to delete worker check input",service.DeleteWorker("212589692"));//To remove from DB
        assertEquals("Deleted worker successfully",service.DeleteWorker("212589691"));//To remove from DB
    }
    @Test
    public void getAvail() {
        assertEquals("Added Driver successfully",service.AddDriver("212589691","Nikita Kovalchuk","Driver","yes","Bank 003 111111",30.00,"22/9/2020","...","E","Negev","0525670092"));
        assertEquals("Driver Nikita Kovalchuk id:212589691 future delivers are:\n" +
                "Availability:\n" +
                "\t212589691: ",service.showAvailability());
        assertEquals("Deleted worker successfully",service.DeleteWorker("212589691"));//To remove from DB
    }
    @Test
    public void AddShiftAndCheckConstraints() {
        assertEquals("Added worker successfully",service.AddWorker("212589691","Nikita Kovalchuk","PersonnelManager","yes","Bank 003 111111",30.00,"22/9/2020","..."));
        assertEquals("Added worker successfully",service.AddWorker("212589692","Nikita Kovalchuk","Cashier","yes","Bank 003 111111",30.00,"22/9/2020","..."));
        assertEquals("Added worker successfully",service.AddWorker("212589693","Nikita Kovalchuk","StoreKeeper","yes","Bank 003 111111",30.00,"22/9/2020","..."));
        assertEquals("Added worker successfully",service.AddWorker("212589694","Nikita Kovalchuk","Usher","yes","Bank 003 111111",30.00,"22/9/2020","..."));
        assertEquals("Added worker successfully",service.AddWorker("212589695","Nikita Kovalchuk","LogisticsManager","yes","Bank 003 111111",30.00,"22/9/2020","..."));
        assertEquals("Added Driver successfully",service.AddDriver("212589696","Nikita Kovalchuk","Driver","yes","Bank 003 111111",30.00,"22/9/2020","...","E","Negev","0525670092"));
        assertEquals("Added Driver successfully",service.AddDriver("212589697","Nikita Kovalchuk","Driver","yes","Bank 003 111111",30.00,"22/9/2020","...","E","Negev","0525670092"));
        assertEquals("Added shift successfully",service.addShift("12/04/2022",0,"212589691","PersonnelManager 212589691|Cashier 212589692|StoreKeeper 212589693|Usher 212589694|LogisticsManager 212589695|Driver 212589696 "));
        List<DriverDTO> drivers = DALController.getInstance().getAllDrivers();
        assertEquals("31@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~28@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~31@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~30@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#1100#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~31@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~30@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~31@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~30@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~31@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~30@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~31@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~30@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000",drivers.get(0).Diary);
        assertEquals("Deleted worker successfully",service.DeleteWorker("212589691"));//To remove from DB
        assertEquals("Deleted worker successfully",service.DeleteWorker("212589692"));//To remove from DB
        assertEquals("Deleted worker successfully",service.DeleteWorker("212589693"));//To remove from DB
        assertEquals("Deleted worker successfully",service.DeleteWorker("212589694"));//To remove from DB
        assertEquals("Deleted worker successfully",service.DeleteWorker("212589695"));//To remove from DB
        assertEquals("Deleted worker successfully",service.DeleteWorker("212589696"));//To remove from DB
        assertEquals("Deleted worker successfully",service.DeleteWorker("212589697"));//To remove from DB
    }

    @Test
    public void AddShiftAndCheckConstraintsMutipleDrivers() {
        assertEquals("Added worker successfully",service.AddWorker("212589691","Nikita Kovalchuk","PersonnelManager","yes","Bank 003 111111",30.00,"22/9/2020","..."));
        assertEquals("Added worker successfully",service.AddWorker("212589692","Nikita Kovalchuk","Cashier","yes","Bank 003 111111",30.00,"22/9/2020","..."));
        assertEquals("Added worker successfully",service.AddWorker("212589693","Nikita Kovalchuk","StoreKeeper","yes","Bank 003 111111",30.00,"22/9/2020","..."));
        assertEquals("Added worker successfully",service.AddWorker("212589694","Nikita Kovalchuk","Usher","yes","Bank 003 111111",30.00,"22/9/2020","..."));
        assertEquals("Added worker successfully",service.AddWorker("212589695","Nikita Kovalchuk","LogisticsManager","yes","Bank 003 111111",30.00,"22/9/2020","..."));
        assertEquals("Added Driver successfully",service.AddDriver("212589696","Nikita Kovalchuk","Driver","yes","Bank 003 111111",30.00,"22/9/2020","...","E","Negev","0525670092"));
        assertEquals("Added Driver successfully",service.AddDriver("212589697","Nikita Kovalchuk","Driver","yes","Bank 003 111111",30.00,"22/9/2020","...","E","Negev","0525670092"));
        assertEquals("Added Driver successfully",service.AddDriver("212589698","Nikita Kovalchuk","Driver","yes","Bank 003 111111",30.00,"22/9/2020","...","E","Negev","0525670092"));
        assertEquals("Added Driver successfully",service.AddDriver("212589699","Nikita Kovalchuk","Driver","yes","Bank 003 111111",30.00,"22/9/2020","...","E","Negev","0525670092"));
        assertEquals("Added shift successfully",service.addShift("12/04/2022",0,"212589691","PersonnelManager 212589691|Cashier 212589692|StoreKeeper 212589693|Usher 212589694|LogisticsManager 212589695|Driver 212589696 "));
        List<DriverDTO> drivers = DALController.getInstance().getAllDrivers();
        assertEquals("31@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~28@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~31@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~30@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#1100#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~31@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~30@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~31@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~30@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~31@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~30@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~31@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~30@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000",drivers.get(0).Diary);
        assertEquals("31@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~28@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~31@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~30@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#1100#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~31@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~30@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~31@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~30@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~31@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~30@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~31@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~30@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000",drivers.get(1).Diary);
        assertEquals("31@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~28@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~31@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~30@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#1100#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~31@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~30@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~31@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~30@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~31@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~30@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~31@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000~30@0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000#0000",drivers.get(2).Diary);
        assertEquals("Deleted worker successfully",service.DeleteWorker("212589691"));//To remove from DB
        assertEquals("Deleted worker successfully",service.DeleteWorker("212589692"));//To remove from DB
        assertEquals("Deleted worker successfully",service.DeleteWorker("212589693"));//To remove from DB
        assertEquals("Deleted worker successfully",service.DeleteWorker("212589694"));//To remove from DB
        assertEquals("Deleted worker successfully",service.DeleteWorker("212589695"));//To remove from DB
        assertEquals("Deleted worker successfully",service.DeleteWorker("212589696"));//To remove from DB
        assertEquals("Deleted worker successfully",service.DeleteWorker("212589697"));//To remove from DB
    }


}