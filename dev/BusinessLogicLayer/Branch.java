package com.company.BusinessLogicLayer;

public class Branch {
    private int branchID;
    private String name;
    private StockController stockController;

    Branch(int _branchID,String _name){
        branchID = _branchID;
        name = _name;
        stockController = new StockController();
    }

}
