package Tests;

import PersonelModule.BusinessLayer.ServiceLayer.Service;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ServiceTest {

    Service service;
    @Before
    public void setUp() throws Exception {
        service = new Service();
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
    public void AddDriver() {
        assertEquals("Added Driver successfully",service.AddDriver("212589691","Nikita Kovalchuk","Driver","yes","Bank 003 111111",30.00,"22/9/2020","...","E","Negev","0525670092"));
        assertEquals("Deleted worker successfully",service.DeleteWorker("212589691"));//To remove from DB
    }

    @Test
    public void AddDriverWithIdThatExists() {
        assertEquals("Added Driver successfully",service.AddDriver("212589691","Nikita Kovalchuk","Driver","yes","Bank 003 111111",30.00,"22/9/2020","...","E","Negev","0525670092"));
        assertEquals("A worker with this Id Already Exists",service.AddDriver("212589691","Nikita Kovalchuk1","Driver","yes","Bank 003 111111",30.00,"22/9/2020","...","E","Negev","0525670092"));
        assertEquals("Deleted worker successfully",service.DeleteWorker("212589691"));//To remove from DB
    }

    @Test
    public void AddDriverWithIlligalDriverData() {
        assertEquals("Added Driver successfully",service.AddDriver("212589691","Nikita Kovalchuk","Driver","yes","Bank 003 111111",30.00,"22/9/2020","...","AC","Negev","0525670092"));
        assertEquals("Deleted worker successfully",service.DeleteWorker("212589691"));//To remove from DB
    }

    @Test
    public void AddDriverWithIlligalWorkerData() {
        assertEquals("Added Driver successfully",service.AddDriver("21258969","Nikita Kovalchuk","Driver","yes","Bank 003 111111",30.00,"22/9/2020","...","E","Negev","0525670092"));
        assertEquals("Deleted worker successfully",service.DeleteWorker("212589691"));//To remove from DB
    }
    @Test
    public void AddShiftAndCheckConstraints() {
        assertEquals("Added Driver successfully",service.AddWorker("212589691","Nikita Kovalchuk","Driver","yes","Bank 003 111111",30.00,"22/9/2020","..."));
        assertEquals("Added Driver successfully",service.AddWorker("212589692","Nikita Kovalchuk","Driver","yes","Bank 003 111111",30.00,"22/9/2020","..."));
        assertEquals("Added Driver successfully",service.AddWorker("212589693","Nikita Kovalchuk","Driver","yes","Bank 003 111111",30.00,"22/9/2020","..."));
        assertEquals("Added Driver successfully",service.AddWorker("212589694","Nikita Kovalchuk","Driver","yes","Bank 003 111111",30.00,"22/9/2020","..."));
        assertEquals("Added Driver successfully",service.AddWorker("212589695","Nikita Kovalchuk","Driver","yes","Bank 003 111111",30.00,"22/9/2020","..."));
        assertEquals("Added Driver successfully",service.AddDriver("212589696","Nikita Kovalchuk","Driver","yes","Bank 003 111111",30.00,"22/9/2020","...","E","Negev","0525670092"));
        assertEquals("Added Driver successfully",service.addShift("12/04/2022",0,"212589691","PersonnelManager 212589691|Cashier 212589692|StoreKeeper 212589693|Usher 212589694|LogisticsManager 212589695|Driver 212589696 "));
        assertEquals("Deleted worker successfully",service.DeleteWorker("212589691"));//To remove from DB
    }

}