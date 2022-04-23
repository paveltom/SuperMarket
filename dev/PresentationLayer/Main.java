package com.company.PresentationLayer;

import com.company.BusinessLayer.ServiceLayer.Service;

public class Main {

    public static Service service;

    public static void Init()
    {
        service = new Service();
        service.AddWorker("111111111","Worker1","PersonnelManager","yes","Bank 003 111111",40.00,"22/9/2020","...");
        service.AddWorker("222222222","Worker2","Cashier","yes","Bank 003 111111",35.00,"22/9/2020","...");
        service.AddWorker("333333333","Worker3","StoreKeeper","no","Bank 003 111111",33.00,"22/9/2020","...");
        service.AddWorker("444444444","Worker4","Usher","no","Bank 003 111111",32.00,"22/9/2020","...");
        service.AddWorker("555555555","Worker5","LogisticsManager","no","Bank 003 111111",38.00,"22/9/2020","...");
        service.AddWorker("666666666","Worker6","Driver","no","Bank 003 111111",33.50,"22/9/2020","...");

    }
    public static void main(String[] args) {
        Init();
    }
}