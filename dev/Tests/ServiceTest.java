package Tests;

import BusinessLayer.ServiceLayer.Service;
import BusinessLayer.ServiceLayer.WorkerService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


class ServiceTest {

    Service service ;
    @Before
    void setUp() {
        service = new Service();
    }

    @Test
    void addWorker() {
        assertEquals("Added worker successfully",service.AddWorker("212589691","Nikita Kovalchuk","Cashier","yes","Bank 003 111111",30.00,"22/9/2020","..."));
    }

    @Test
    void addWorkerDuplicateWorkerTest() {
        assertEquals("Added worker successfully",service.AddWorker("212589691","Nikita Kovalchuk","Cashier","yes","Bank 003 111111",30.00,"22/9/2020","..."));
        assertEquals("A worker with this Id Already Exists",service.AddWorker("212589691","Nikita Kovalchuk1","Cashier","yes","Bank 003 111111",30.00,"22/9/2020","..."));
    }

    @Test
    void addWorkerIncorrectDetailsTest() {
        assertEquals("Job isn't valid",service.AddWorker("212589691","Nikita Kovalchuk","Cashie1r","yes","Bank 003 111111",30.00,"22/9/2020","..."));
    }

    @Test
    void deleteWorker() {
        service.AddWorker("212589691","Nikita Kovalchuk","Cashier","yes","Bank 003 111111",30.00,"22/9/2020","...");
        assertEquals("Deleted worker successfully",service.DeleteWorker("212589691"));
    }

    @Test
    void deleteWorkerIncorrectId() {
        service.AddWorker("212589691","Nikita Kovalchuk","Cashier","yes","Bank 003 111111",30.00,"22/9/2020","...");
        assertEquals("There is no worker with id 212589692",service.DeleteWorker("212589692"));
    }

    @Test
    void changeId() {
        service.AddWorker("212589691","Nikita Kovalchuk","Cashier","yes","Bank 003 111111",30.00,"22/9/2020","...");
        assertEquals("Changed Id successfully",service.ChangeId("212589691","212589692"));
    }
    @Test
    void changeIdIncorrectId() {
        service.AddWorker("212589691","Nikita Kovalchuk","Cashier","yes","Bank 003 111111",30.00,"22/9/2020","...");
        assertEquals("There is no worker with id 212589692",service.ChangeId("212589692","212589693"));
    }
    @Test
    void changeIdExistingId() {
        service.AddWorker("212589691","Nikita Kovalchuk","Cashier","yes","Bank 003 111111",30.00,"22/9/2020","...");
        service.AddWorker("212589692","Nikita Kovalchuk1","Cashier","yes","Bank 003 111111",30.00,"22/9/2020","...");
        assertEquals("A worker with id :212589692 Already Exist, Change failed",service.ChangeId("212589691","212589692"));
    }
    @Test
    void TwoChangeId() {
        service.AddWorker("212589691","Nikita Kovalchuk","Cashier","yes","Bank 003 111111",30.00,"22/9/2020","...");
        assertEquals("Changed Id successfully",service.ChangeId("212589691","212589692"));
        assertEquals("Changed Id successfully",service.ChangeId("212589692","212589691"));

    }
    @Test
    void getAllWorkersString() {
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
    }
    @Test
    void GetWorkersByJob()
    {
       service.AddWorker("212589691","Nikita Kovalchuk","Cashier","yes","Bank 003 111111",30.00,"22/9/2020","...");
        service.AddWorker("212589692","Nikita Kovalchuk1","Cashier","yes","Bank 003 111111",30.00,"22/9/2020","...");
        service.AddWorker("212589691","Nikita Kovalchuk2","Usher","yes","Bank 003 111111",30.00,"22/9/2020","...");
        service.AddWorker("212589691","Nikita Kovalchuk3","PersonnelManager","yes","Bank 003 111111",30.00,"22/9/2020","...");
        assertEquals("212589692 Nikita Kovalchuk1\n" +
                "212589691 Nikita Kovalchuk",service.GetWorkersByJob("Cashier"));
    }
}