package com.company.Tests;

import com.company.BusinessLayer.ServiceLayer.Service;
import com.company.BusinessLayer.ServiceLayer.WorkerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {

    Service service ;
    @BeforeEach
    void setUp() {
        service = new Service();
    }

    @org.junit.jupiter.api.Test
    void addWorker() {
        assertEquals("Added worker successfully",service.AddWorker("212589691","Nikita Kovalchuk","Cashier","yes","Bank 003 111111",30.00,"22/9/2020","..."));
    }

    @org.junit.jupiter.api.Test
    void addWorkerDuplicateWorkerTest() {
        assertEquals("Added worker successfully",service.AddWorker("212589691","Nikita Kovalchuk","Cashier","yes","Bank 003 111111",30.00,"22/9/2020","..."));
        assertEquals("A worker with this Id Already Exists",service.AddWorker("212589691","Nikita Kovalchuk1","Cashier","yes","Bank 003 111111",30.00,"22/9/2020","..."));
    }

    @org.junit.jupiter.api.Test
    void addWorkerIncorrectDetailsTest() {
        assertEquals("Job isn't valid",service.AddWorker("212589691","Nikita Kovalchuk","Cashie1r","yes","Bank 003 111111",30.00,"22/9/2020","..."));
    }

    @org.junit.jupiter.api.Test
    void deleteWorker() {
        service.AddWorker("212589691","Nikita Kovalchuk","Cashier","yes","Bank 003 111111",30.00,"22/9/2020","...");
        assertEquals("Deleted worker successfully",service.DeleteWorker("212589691"));
    }

    @org.junit.jupiter.api.Test
    void deleteWorkerIncorrectId() {
        service.AddWorker("212589691","Nikita Kovalchuk","Cashier","yes","Bank 003 111111",30.00,"22/9/2020","...");
        assertEquals("There is no worker with id 212589692",service.DeleteWorker("212589692"));
    }

    @org.junit.jupiter.api.Test
    void changeId() {
        service.AddWorker("212589691","Nikita Kovalchuk","Cashier","yes","Bank 003 111111",30.00,"22/9/2020","...");
        assertEquals("Changed Id successfully",service.ChangeId("212589691","212589692"));
    }
    @org.junit.jupiter.api.Test
    void changeIdIncorrectId() {
        service.AddWorker("212589691","Nikita Kovalchuk","Cashier","yes","Bank 003 111111",30.00,"22/9/2020","...");
        assertEquals("There is no worker with id 212589692",service.ChangeId("212589692","212589693"));
    }
    @org.junit.jupiter.api.Test
    void changeIdExistingId() {
        service.AddWorker("212589691","Nikita Kovalchuk","Cashier","yes","Bank 003 111111",30.00,"22/9/2020","...");
        service.AddWorker("212589692","Nikita Kovalchuk1","Cashier","yes","Bank 003 111111",30.00,"22/9/2020","...");
        assertEquals("A worker with id :212589692 Already Exist, Change failed",service.ChangeId("212589691","212589692"));
    }
    @org.junit.jupiter.api.Test
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
        assertEquals("\nId:212589693\n" +
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
}